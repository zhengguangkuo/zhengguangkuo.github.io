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
    /* 为StrUtil的AscToBcd()、BcdToAsc()的入参alignFlag定义 */
    public static final int LEFT_ALIGN = 1;
    public static final int RIGHT_ALIGN = 0;

    /**
     * 将ASCII字符串转换成BCD码数组
     * @param ascStr String  需要转换的字符串
     * @param alignFlag int  对齐处理标志
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
     * 将BCD码数组转换成字符串
     * 如果BCD码数组只有n个字节,但指定转换后的字符串长度大于nx2,则只转换成nx2个字符长的字符串
     * @param bcdArray byte[]  BCD码数组
     * @param ascStrLen int  指定转换成的字符串长度
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
     * 将byte数组转换成16进制字符串
     * @param array byte[]  字节数组
     * @param splitFlag String  做转换时,在每个byte转换后的字符间加入的分隔符
     * @return String  转换后的16进制字符串
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
     * 将有特定分割符号的16机制字符串转换为byte数组
     * 如:"31 EF C0",就是以" "为分隔符的字符串
     * @param hexStr String  16进制字符串
     * @param splitFlag String  分隔符
     * @return byte[]  转换后的byte数组
     */
    public static final byte[] HexStringToByte( String hexStr, String splitFlag ) {
        String tmpStr = hexStr.replaceAll( splitFlag, "" );
        if ( tmpStr.length() % 2 != 0 )
            return null;
        return( AscToBcd( tmpStr, LEFT_ALIGN ) );
    }

    /**
     * 将一个字符串转换成整数.
     * 如果字符串为"12abc",结果为12;如果字符串为"bef34",结果为0
     * @param str String  源字符串
     * @return int  转换后的结果
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
