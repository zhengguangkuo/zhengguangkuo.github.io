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
    byte ascLenInfo;        /* 域长度信息表示方式 */
    IsoInfo[] isoInfo;      /* 8583各个域的属性 */
    byte[][] isoData ;      /* 存放8583各域数据的缓冲区 */
    short[] isoLen;         /* 8583各域的数据长度 */


    public Iso8583( IsoInfo[] isoInfo ) throws MsgConvException
    {
        if ( isoInfo.length > 128 )
            throw new MsgConvException( "8583域数超过128个" );
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

        /* 生成bitmap,并提取各个域的数据生成8583报文的各个域的数据元信息 */
        try {
            for ( i = 1, offSet = 0; i < isoData.length; i++ ) {
                if ( isoData[i] == null )
                    continue;

                /* 如果是变长数据域,添加域长度信息 */
                lenType = isoInfo[i].type & 0xC0;
                if (  lenType != IsoInfo.FIXED ) {
                    /* 生成变长域的长度信息 */
                    len = ( lenType == IsoInfo.LLVAR ) ? 2 : 3;
                    tmp = Integer.toString( isoLen[i] );
                    length = tmp.length();
                    for ( j = 0; j < len - length; j++ )
                        tmp = '0' + tmp;

                    /* 根据域长度信息表示方式对域长度信息做转换 */
                    if ( ascLenInfo == IsoInfo.BCD )
                        lenInfo = StrUtil.AscToBcd( tmp, StrUtil.RIGHT_ALIGN );
                    else
                        lenInfo = tmp.getBytes();
                    /* 将域长度信息添加到该域的数据元信息 */
                    System.arraycopy( lenInfo, 0, tmpArray, offSet, lenInfo.length );
                    offSet += lenInfo.length;
                }

                /* 将该域数据信息添加至该域的数据元信息,并调整位图信息 */
                System.arraycopy( isoData[i], 0, tmpArray, offSet, isoData[i].length );
                offSet += isoData[i].length;
                ch = 0x01;
                bitMap[i / 8] |= ch << (7 - i % 8);
            }
        }
        catch ( Exception ex ) {
            throw new MsgConvException( "8583报文长度超过4096." );
        }

        /* 如果域数超过64个,调整位图信息 */
        if ( i > 64 ) {
            bitMap[0] |= 0x80;
            mapNum = 2;
        }
        /* 用msgId,位图信息以及各个域的数据元信息组成8583报文 */
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


        /* 获取MsgId信息 */
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

        /* 提取位图信息 */
        if ( (isoStr[offSet] & 0x80) != 0 )
            mapNum = 2;
        else
            mapNum = 1;
        System.arraycopy( isoStr, offSet, bitMap, 0, 8 * mapNum);
        offSet += mapNum * 8;

        try {
			/* 依据8583报文中的位图,提取各个数据域的信息 */
            for ( i = 1; i < isoInfo.length; i++ )
			{
			    j = i % 8;
			    
			    if (i == 63){
			    	System.out.println("当前的索引:" + i);	
			    }
			    
			    
				if ( (bitMap[i / 8] & (0x01 << (7 - j))) == 0 )
				    continue;

				lenType = isoInfo[i].type & 0xC0;
                /* 提取域长度信息 */
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

                /* 提取域数据信息 */
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
		    throw new MsgConvException( "拆解8583报文时,长度越界" );
		}
    }

    public void SetFld( int fldNo, String inData ) throws MsgConvException {
        int len, inLen, tmpLen, offSet = 0;
        byte type, lenType;
        byte[] bcdArray;
        char fillCh;


        if ( fldNo < 1 || fldNo > isoInfo.length )
            throw new MsgConvException( "无效的8583域号[" + fldNo + "]" );

        type = isoInfo[--fldNo].type;
        lenType = (byte)(type & 0xC0);
        inLen = inData.getBytes().length;

        /* 如果是借贷记符号域,域长度不含借贷记符号,因而生成待处理的域数据时需要
         从inData的第二个位置取信息 */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
        {
            offSet = 1;
            inLen--;
        }

        /* 根据isoInfo计算该域的数据最大长度 */
        if ( (type & 0x10) == IsoInfo.BINARY )
            /* 如果是二进制数据,在调用SetIsoFld函数时,需要将byte数据转换成String数据 */
            len = isoInfo[fldNo].len * 2;
        else
            len = isoInfo[fldNo].len;

        /* 确定做域数据处理时,域的数据长度 */
        if ( lenType != IsoInfo.FIXED && inLen < len )
            len = inLen;

        /* 填写该域数据长度 */
        if ( (type & 0x10) == IsoInfo.BINARY )
            isoLen[fldNo] = (short)( ( len + 1 ) / 2 );
        else
            isoLen[fldNo] = (short)len;


        StringBuffer inBuf = new StringBuffer( inData.substring( offSet, inData.length() ) );
        /* 确定补齐使用的填充字符 */
        if ( (type & 0x01) == IsoInfo.BLANK )
            fillCh = ' ';
        else
            fillCh = '0';

        /* 按照域的补齐属性做补齐处理 */
        tmpLen = len - inLen > 0 ? len - inLen : 0;
        for ( int i = 0; i < tmpLen; i++ ) {
            if ( (type & 0x02) == IsoInfo.LEFT )
                inBuf.append( fillCh );
            else
                inBuf.insert( 0, fillCh );
        }

        /* 将数据压缩成BCD码或转换成byte型数据 */
        if ( (type & 0x08) == IsoInfo.BCD || (type & 0x10) == IsoInfo.BINARY )
            bcdArray = StrUtil.AscToBcd( inBuf.toString(), (type & 0x02) >> 1 );
        else
            bcdArray = inBuf.toString().getBytes();
       // System.out.println( "len is: " + len + ", bcdAray len is: " + bcdArray.length );

        /* 生成该域的最终数据信息 */
        isoData[fldNo] = new byte[bcdArray.length + offSet];
        /* 如果是借贷记符号域,将符号添加到域数据信息中 */
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
            throw new MsgConvException( "无效的8583域号[" + fldNo + "]" );

        if ( isoData[--fldNo] == null )
            return( null );

        type = isoInfo[fldNo].type;
        len = isoData[fldNo].length;

        /* 如果是借贷记符号域,域长度不含借贷记符号,因而生成待处理的域数据时需要
         从inData的第二个位置取信息 */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
        {
            offSet = 1;
            len--;
        }

        bcdArray = new byte[len];
        System.arraycopy( isoData[fldNo], offSet, bcdArray, 0, len );
        /* 将数据解压或转换成String型数据 */
        if ( (type & 0x08) == IsoInfo.BCD || (type & 0x10) == IsoInfo.BINARY )
            tmpBuf = StrUtil.BcdToAsc( bcdArray, len * 2, (type & 0x02) >> 1 );
        else{
        	 try{
             	tmpBuf = new String( bcdArray,  code);
             }catch(Exception ex){
             	ex.printStackTrace();
             }
        }
           

        
        
        /* 如果是借贷记符号域,将符号添加到被提取的数据信息中 */
        if ( (type & 0x04) == IsoInfo.SYMBOL )
            tmpBuf = new String( isoData[fldNo], 0, 1 ) + tmpBuf;
        
        int lenType = isoInfo[fldNo].type & 0xC0;
        /* 提取域长度信息 */
        if(lenType != IsoInfo.FIXED && ((isoInfo[fldNo].type & 0x08) == IsoInfo.BCD)){
        	
        	byte type2 = isoInfo[fldNo].type;
        	
        	if((type2 & 0x02) == IsoInfo.LEFT){//左对齐 
        		tmpBuf = tmpBuf.substring(0, isoLen[fldNo]);
        	}else{//右对齐
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
            throw new IsoException( "无效的8583域号[" + fldNo + "]" );

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
