package com.mt.android.message.iso.util;

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
public class StrUtil {
    /* ΪStrUtil��AscToBcd()��BcdToAsc()�����alignFlag���� */
    public static final int LEFT_ALIGN = 1;
    public static final int RIGHT_ALIGN = 0;

    /**
     * ��ASCII�ַ���ת����BCD������
     * @param ascStr String  ��Ҫת�����ַ���
     * @param alignFlag int  ���봦���־
     * @return String
     */
    public static final byte[] AscToBcd( String ascStr, int alignFlag ) {
        int i, off = 0, len, flag = 0;
        byte ch, bcdArray[];
        char tmpArray[];

        len = ascStr.length();

        if ( len % 2 != 0 && alignFlag == RIGHT_ALIGN )
            off = 1;
        tmpArray = ascStr.toCharArray();
        bcdArray = new byte[(len + 1) / 2];
        for ( i = 0; i < len; i++ ) {
            if ( tmpArray[i] >= 'a' )
                ch = (byte)( tmpArray[i] - 'a' + 10 );
            else if ( tmpArray[i] >= 'A' )
                ch = (byte)( tmpArray[i] - 'A' + 10 );
            else if ( tmpArray[i] >= '0' )
                ch = (byte)( tmpArray[i] - '0' );
            else
                ch = 0;

            if ( ( i + off )% 2 != 0 )
                bcdArray[(i + off) / 2] |= ( ch & 0x0F );
            else
                bcdArray[(i + off) / 2] |= ( ch << 4 );
        }

        return( bcdArray );
    }

    /**
     * ��BCD������ת�����ַ���
     * ���BCD������ֻ��n���ֽ�,��ָ��ת������ַ������ȴ���nx2,��ֻת����nx2���ַ������ַ���
     * @param bcdArray byte[]  BCD������
     * @param ascStrLen int  ָ��ת���ɵ��ַ�������
     * @param alignFlag int
     * @return String
     */
    public static final String BcdToAsc( byte[] bcdArray, int ascStrLen, int alignFlag ){
        int i, len, pos, maxLen;

        if ( ascStrLen <= 0 )
            return( null );

        maxLen = bcdArray.length * 2;
        if ( ascStrLen > maxLen )
            len = maxLen;
        else
            len = ascStrLen;

        byte[] tmpArray = new byte[len];
        if ( alignFlag == RIGHT_ALIGN )
        {
            for ( i = maxLen - 1, pos = len - 1; i > maxLen- len - 1; i--, pos-- )
            {
                if ( i % 2 == 0 )
                    tmpArray[pos] = (byte) (bcdArray[i / 2] >> 4 & 0x0F);
                else
                    tmpArray[pos] = (byte) (bcdArray[i / 2] & 0x0F);
                tmpArray[pos] += (byte)( tmpArray[pos] > 9 ? 'A' - 10 : '0' );
            }
        }
        else
        {
            for ( i = 0; i < len; i++ )
            {
                if ( i % 2 == 0 )
                    tmpArray[i] = (byte) (bcdArray[i / 2] >> 4 & 0x0F);
                else
                    tmpArray[i] = (byte) (bcdArray[i / 2] & 0x0F);
                tmpArray[i] += (byte)( tmpArray[i] > 9 ? 'A' - 10 : '0' );
            }
        }

        return( new String( tmpArray ) );
    }

    /**
     * ��byte����ת����16�����ַ���
     * @param array byte[]  �ֽ�����
     * @param splitFlag String  ��ת��ʱ,��ÿ��byteת������ַ������ķָ���
     * @return String  ת�����16�����ַ���
     */
    public static final String ByteToHexString( byte[] array, String splitFlag ) {
        StringBuffer buf = new StringBuffer();
        String tmpStr = null;

        for ( int i = 0; i < array.length; i++ ) {
            tmpStr = Integer.toHexString( (array[i] & 0xFF) ).toUpperCase();
            if ( tmpStr.length() == 1 )
                buf.append( '0' );
            buf.append( tmpStr + splitFlag );
        }

        return( buf.toString() );
    }

    /**
     * �����ض��ָ���ŵ�16�����ַ���ת��Ϊbyte����
     * ��:"31 EF C0",������" "Ϊ�ָ������ַ���
     * @param hexStr String  16�����ַ���
     * @param splitFlag String  �ָ���
     * @return byte[]  ת�����byte����
     */
    public static final byte[] HexStringToByte( String hexStr, String splitFlag ) {
        String tmpStr = hexStr.replaceAll( splitFlag, "" );
        if ( tmpStr.length() % 2 != 0 )
            return null;
        return( AscToBcd( tmpStr, LEFT_ALIGN ) );
    }

    /**
     * ��һ���ַ���ת��������.
     * ����ַ���Ϊ"12abc",���Ϊ12;����ַ���Ϊ"bef34",���Ϊ0
     * @param str String  Դ�ַ���
     * @return int  ת����Ľ��
     */
    public static int Str2Int( String str ) {
        int i;

        for ( i = 0; i < str.length(); i++ ) {
            if ( str.charAt(i) < '0' || str.charAt(i) > '9' )
                break;
        }
        if ( i == 0 )
            return( 0 );
        return( Integer.parseInt(str.substring( 0, i )) );
    }

    public static final byte[] Ansi98( String cardNo, String pwd ) {
        int i, len;
        String tmpStr;
        byte[] pinBlock = null, cardPan;

        if ( ( len = cardNo.length() ) < 13 )
            return( pinBlock );
        cardPan = StrUtil.AscToBcd( "0000" + cardNo.substring( len - 12 - 1, len -1 ), 0 );

        len = pwd.length();
        if ( len > 12 )
            return( pinBlock );

        tmpStr = Integer.toString( len );
        if ( tmpStr.length() == 1 )
            tmpStr = '0' + tmpStr;
        tmpStr = tmpStr + pwd;
        for ( i = 0; i < 16 - len -2; i++ )
            tmpStr += 'F';
        pinBlock = StrUtil.AscToBcd( tmpStr, 0 );

        for ( i = 0; i < 8; i++ )
            pinBlock[i] = (byte)(cardPan[i] ^ pinBlock[i]);

        return( pinBlock );
    }

    public static final String _Ansi98( String cardNo, byte[] pinBlock ) {
        byte[] cardPan;
        String pwd;
        int i, len;

        if ( ( len = cardNo.length() ) < 13 )
            return( null );
        cardPan = StrUtil.AscToBcd( "0000" + cardNo.substring( len - 12 - 1, len - 1 ), 0 );

        if ( pinBlock.length < 8 )
            return( null );
        for ( i = 0; i < 8; i++ )
            pinBlock[i] = (byte)(pinBlock[i] ^ cardPan[i]);

        pwd = StrUtil.BcdToAsc( pinBlock, 16, StrUtil.LEFT_ALIGN );
        len = Integer.parseInt( pwd.substring( 0, 2 ));

        return( pwd.substring( 2, 2 + len ) );
    }
}
