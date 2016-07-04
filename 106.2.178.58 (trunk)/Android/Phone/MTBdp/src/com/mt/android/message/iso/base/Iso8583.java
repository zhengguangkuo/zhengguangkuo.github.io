package com.mt.android.message.iso.base;
import android.util.Log;

import com.mt.android.message.iso.exception.IsoException;
import com.mt.android.message.iso.exception.MsgConvException;
import com.mt.android.message.iso.util.StrUtil;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Iso8583 implements MsgConvFace {
	public String code = "GBK";
    byte ascLenInfo;        /* �򳤶���Ϣ��ʾ��ʽ */
    IsoInfo[] isoInfo;      /* 8583����������� */
    byte[][] isoData ;      /* ���8583�������ݵĻ����� */
    short[] isoLen;         /* 8583��������ݳ��� */


    public Iso8583( IsoInfo[] isoInfo ) throws MsgConvException
    {
        if ( isoInfo.length > 128 )
            throw new MsgConvException( "8583��������128��" );
        this.isoInfo = isoInfo;
        isoData = new byte[this.isoInfo.length][];
        isoLen = new short[this.isoInfo.length];
        this.ascLenInfo = (byte)(this.isoInfo[0].type & 0x08) ;
    }

    public void Clear( ) {
        for ( int i = 0; i < isoInfo.length; i++ )
        {
            isoData[i] = null;
            isoLen[i] = 0;
        }
    }

    public byte[] IsoToMsg( ) throws MsgConvException {
        int i, j, offSet, lenType, len, length, mapNum = 1;
        String tmp;
        byte ch = 0x01;
        byte[] lenInfo, isoStr;
        byte[] bitMap = new byte[16];
        byte[] tmpArray = new byte[4096];

        /* ����bitmap,����ȡ���������������8583���ĵĸ����������Ԫ��Ϣ */
        try {
            for ( i = 1, offSet = 0; i < isoData.length; i++ ) {
                if ( isoData[i] == null )
                    continue;

                /* ����Ǳ䳤������,����򳤶���Ϣ */
                lenType = isoInfo[i].type & 0xC0;
                if (  lenType != IsoInfo.FIXED ) {
                    /* ���ɱ䳤��ĳ�����Ϣ */
                    len = ( lenType == IsoInfo.LLVAR ) ? 2 : 3;
                    tmp = Integer.toString( isoLen[i] );
                    length = tmp.length();
                    for ( j = 0; j < len - length; j++ )
                        tmp = '0' + tmp;

                    /* �����򳤶���Ϣ��ʾ��ʽ���򳤶���Ϣ��ת�� */
                    if ( ascLenInfo == IsoInfo.BCD )
                        lenInfo = StrUtil.AscToBcd( tmp, StrUtil.RIGHT_ALIGN );
                    else
                        lenInfo = tmp.getBytes();
                    /* ���򳤶���Ϣ��ӵ����������Ԫ��Ϣ */
                    System.arraycopy( lenInfo, 0, tmpArray, offSet, lenInfo.length );
                    offSet += lenInfo.length;
                }

                /* ������������Ϣ��������������Ԫ��Ϣ,������λͼ��Ϣ */
                System.arraycopy( isoData[i], 0, tmpArray, offSet, isoData[i].length );
                offSet += isoData[i].length;
                ch = 0x01;
                bitMap[i / 8] |= ch << (7 - i % 8);
            }
        }
        catch ( Exception ex ) {
            throw new MsgConvException( "8583���ĳ��ȳ���4096." );
        }

        /* �����������64��,����λͼ��Ϣ */
        if ( i > 64 ) {
            bitMap[0] |= 0x80;
            mapNum = 2;
        }
        /* ��msgId,λͼ��Ϣ�Լ������������Ԫ��Ϣ���8583���� */
        isoStr = new byte[isoData[0].length + mapNum * 8 + offSet];
        System.arraycopy( isoData[0], 0, isoStr, 0, isoData[0].length );
        
        System.out.println("bitMap:" +  StrUtil.ByteToHexString( bitMap, " " ) );
        
        System.arraycopy( bitMap, 0, isoStr, isoData[0].length, mapNum * 8 );
        System.arraycopy( tmpArray, 0, isoStr, isoData[0].length + mapNum * 8, offSet );
        return( isoStr );
    }

    public void MsgToIso( Object msgData ) throws MsgConvException {
        int i, j, offSet, lenType, len, length, mapNum = 1;
        String tmp;
        byte ch = 0x01;
        byte[] lenInfo, isoStr = (byte[])msgData;
        byte[] bitMap = new byte[16];
        byte[] tmpArray;


        /* ��ȡMsgId��Ϣ */
        if ( (isoInfo[0].type & 0x08) == IsoInfo.BCD )
        {
            isoData[0] = new byte[2];
            isoLen[0] = 2;
            System.arraycopy( isoStr, 0, isoData[0], 0, 2 );
            offSet = 2;
        }
        else
        {
            isoData[0] = new byte[4];
            isoLen[0] = 4;
            System.arraycopy( isoStr, 0, isoData[0], 0, 4 );
            offSet = 4;
        }

        /* ��ȡλͼ��Ϣ */
        if ( (isoStr[offSet] & 0x80) != 0 )
            mapNum = 2;
        else
            mapNum = 1;
        System.arraycopy( isoStr, offSet, bitMap, 0, 8 * mapNum);
        offSet += mapNum * 8;

        try {
			/* ����8583�����е�λͼ,��ȡ�������������Ϣ */
            for ( i = 1; i < isoInfo.length; i++ )
			{
			    j = i % 8;
			    
			    if (i == 63){
			    	System.out.println("��ǰ������:" + i);	
			    }
			    
			    
				if ( (bitMap[i / 8] & (0x01 << (7 - j))) == 0 )
				    continue;

				lenType = isoInfo[i].type & 0xC0;
                /* ��ȡ�򳤶���Ϣ */
                if ( lenType != IsoInfo.FIXED )
                {
                    if ( lenType == IsoInfo.LLVAR )
                        length = 2;
                    else
                        length = 3;

					if ( ascLenInfo == IsoInfo.BCD )
					{
					    length = (length + 1) / 2;
                        tmpArray = new byte[length];
						System.arraycopy( isoStr, offSet, tmpArray, 0, length );
						tmp = StrUtil.BcdToAsc( tmpArray, length * 2, StrUtil.RIGHT_ALIGN );
					}
					else
					    tmp = new String( isoStr, offSet, length );
					len = StrUtil.Str2Int( tmp );
					isoLen[i] = (short)len;
                    offSet += length;
				}
				else
				{
				    len = isoInfo[i].len;
                    isoLen[i] = (short)len;
				}

                /* ��ȡ��������Ϣ */
                if ( (isoInfo[i].type & 0x08) == IsoInfo.BCD )
                    len = ( len + 1 ) / 2;
                if ( (isoInfo[i].type & 0x04) == IsoInfo.SYMBOL )
                    len++;
                isoData[i] = new byte[len];
                System.arraycopy( isoStr, offSet, isoData[i], 0, len );
                offSet += len;
                
                if(i == 1){
                	Log.i("UnPackInfo", "MSGID:" + GetFld(1));
                }
                Log.i("UnPackInfo",  "NO:" + (i+1) + ", Value:[" + GetFld(i+1) + "]," + "LEN:" + len + ", offSet:" + offSet);
		    }
        }
		catch ( Exception ex )
		{
		    throw new MsgConvException( "���8583����ʱ,����Խ��" );
		}
    }

    public void SetFld( int fldNo, String inData ) throws MsgConvException {
        int len, inLen, tmpLen, offSet = 0;
        byte type, lenType;
        byte[] bcdArray;
        char fillCh;


        if ( fldNo < 1 || fldNo > isoInfo.length )
            throw new MsgConvException( "��Ч��8583���[" + fldNo + "]" );

        type = isoInfo[--fldNo].type;
        lenType = (byte)(type & 0xC0);
        inLen = inData.getBytes().length;

        /* ����ǽ���Ƿ�����,�򳤶Ȳ�������Ƿ���,������ɴ������������ʱ��Ҫ
         ��inData�ĵڶ���λ��ȡ��Ϣ */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
        {
            offSet = 1;
            inLen--;
        }

        /* ����isoInfo��������������󳤶� */
        if ( (type & 0x10) == IsoInfo.BINARY )
            /* ����Ƕ���������,�ڵ���SetIsoFld����ʱ,��Ҫ��byte����ת����String���� */
            len = isoInfo[fldNo].len * 2;
        else
            len = isoInfo[fldNo].len;

        /* ȷ���������ݴ���ʱ,������ݳ��� */
        if ( lenType != IsoInfo.FIXED && inLen < len )
            len = inLen;

        /* ��д�������ݳ��� */
        if ( (type & 0x10) == IsoInfo.BINARY )
            isoLen[fldNo] = (short)( ( len + 1 ) / 2 );
        else
            isoLen[fldNo] = (short)len;


        StringBuffer inBuf = new StringBuffer( inData.substring( offSet, inData.length() ) );
        /* ȷ������ʹ�õ�����ַ� */
        if ( (type & 0x01) == IsoInfo.BLANK )
            fillCh = ' ';
        else
            fillCh = '0';

        /* ������Ĳ������������봦�� */
        tmpLen = len - inLen > 0 ? len - inLen : 0;
        for ( int i = 0; i < tmpLen; i++ ) {
            if ( (type & 0x02) == IsoInfo.LEFT )
                inBuf.append( fillCh );
            else
                inBuf.insert( 0, fillCh );
        }

        /* ������ѹ����BCD���ת����byte������ */
        if ( (type & 0x08) == IsoInfo.BCD || (type & 0x10) == IsoInfo.BINARY )
            bcdArray = StrUtil.AscToBcd( inBuf.toString(), (type & 0x02) >> 1 );
        else
            bcdArray = inBuf.toString().getBytes();
       // System.out.println( "len is: " + len + ", bcdAray len is: " + bcdArray.length );

        /* ���ɸ��������������Ϣ */
        isoData[fldNo] = new byte[bcdArray.length + offSet];
        /* ����ǽ���Ƿ�����,��������ӵ���������Ϣ�� */
        if ( (type &0x04) == IsoInfo.SYMBOL )
            isoData[fldNo][0] = (byte)inData.charAt( 0 );
        System.arraycopy( bcdArray, 0, isoData[fldNo], offSet, bcdArray.length );
        
        Log.i("PACKINFO============HEXFILED==================", "NO:" + (fldNo+1) + ", HexVal:" + StrUtil.ByteToHexString(bcdArray, " "));
    }

    public String GetFld( int fldNo ) throws MsgConvException {
        int len, offSet = 0;
        byte type;
        byte[] bcdArray;
        String tmpBuf = "";


        if ( fldNo < 1 || fldNo > isoInfo.length )
            throw new MsgConvException( "��Ч��8583���[" + fldNo + "]" );

        if ( isoData[--fldNo] == null )
            return( null );

        type = isoInfo[fldNo].type;
        len = isoData[fldNo].length;

        /* ����ǽ���Ƿ�����,�򳤶Ȳ�������Ƿ���,������ɴ������������ʱ��Ҫ
         ��inData�ĵڶ���λ��ȡ��Ϣ */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
        {
            offSet = 1;
            len--;
        }

        bcdArray = new byte[len];
        System.arraycopy( isoData[fldNo], offSet, bcdArray, 0, len );
        /* �����ݽ�ѹ��ת����String������ */
        if ( (type & 0x08) == IsoInfo.BCD || (type & 0x10) == IsoInfo.BINARY )
            tmpBuf = StrUtil.BcdToAsc( bcdArray, len * 2, (type & 0x02) >> 1 );
        else{
        	 try{
             	tmpBuf = new String( bcdArray,  code);
             }catch(Exception ex){
             	ex.printStackTrace();
             }
        }
           

        
        
        /* ����ǽ���Ƿ�����,��������ӵ�����ȡ��������Ϣ�� */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
            tmpBuf = new String( isoData[fldNo], 0, 1 ) + tmpBuf;
        
        int lenType = isoInfo[fldNo].type & 0xC0;
        /* ��ȡ�򳤶���Ϣ */
        if(lenType != IsoInfo.FIXED && ((isoInfo[fldNo].type & 0x08) == IsoInfo.BCD)){
        	
        	byte type2 = isoInfo[fldNo].type;
        	
        	if((type2 & 0x02) == IsoInfo.LEFT){//����� 
        		tmpBuf = tmpBuf.substring(0, isoLen[fldNo]);
        	}else{//�Ҷ���
        		tmpBuf = tmpBuf.substring(tmpBuf.length()-isoLen[fldNo], tmpBuf.length());
        	}
        	
        }

        //return( tmpBuf.trim() );
        return(tmpBuf);
    }

    public byte[] Get8583Fld( int fldNo ) throws IsoException {
        byte type, lenType;
        byte[] tmpArray, fld;
        String tmp;
        int i, len, length;


        if ( fldNo < 1 || fldNo > isoInfo.length )
            throw new IsoException( "��Ч��8583���[" + fldNo + "]" );

        if ( isoData[--fldNo] == null )
            return( null );

        type = isoInfo[fldNo].type;
        lenType = (byte)(type & 0xC0);

        if ( lenType != IsoInfo.FIXED )
        {
            if ( lenType == IsoInfo.LLVAR )
                length = 2;
            else
                length = 3;

            tmp = Integer.toString( isoLen[fldNo] );
            len = tmp.length();
            for ( i = 0; i < length - len; i++ )
                tmp = '0' + tmp;
            len = isoData[fldNo].length;
            if ( ascLenInfo == IsoInfo.BCD )
            {
                length = (length + 1) / 2;
                tmpArray = StrUtil.AscToBcd( tmp, StrUtil.RIGHT_ALIGN );
            }
            else
                tmpArray = tmp.getBytes( );
            fld = new byte[length + len];
            System.arraycopy( tmpArray, 0, fld, 0, length );
            System.arraycopy( isoData[fldNo], 0, fld, length, isoData[fldNo].length );
        }
        else
        {
            fld = new byte[isoData[fldNo].length];
            System.arraycopy( isoData[fldNo], 0, fld, 0, isoData[fldNo].length );
        }

        return( fld );
    }

    public Object GetOrigiMsgFld( int fldNo ) {
        return( isoData[fldNo] );
    }
}
