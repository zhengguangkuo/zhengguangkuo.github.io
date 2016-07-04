package com.mt.android.message.iso;
import java.util.List;

import com.mt.android.message.iso.util.StrUtil;




public class Test {

    public static String ReplaceMacro( String inStr, List<String> macroList ) {
        int startIndex, expBegIndex, expEndIndex, cpBegIndex, cpEndIndex;
        int macroBegIndex, macroEndIndex, macroNo, i, offSet1, offSet2;
        char ch;
        String tmp, macroVal, macro;


        /* 如果当前字符串中没有MACRO字符串,宏变量替换结束 */
        for ( startIndex = 0; (macroBegIndex = inStr.indexOf( "MACRO", startIndex)) > -1; ) {
            /* 先确定当前字符串中的宏变量起始,终止位置 */
            if ( (macroEndIndex = inStr.indexOf( "$", macroBegIndex + 5 )) == -1 )
                break;

            /* 如果不是宏变量 */
            ch = inStr.charAt( macroEndIndex - 1 );
            if ( ch != '>' && ( ch < '0' || ch > '9' ) )
            {
                startIndex = macroEndIndex + 1;
                continue;
            }

            /* 确定需要使用的宏变量编号,以及使用的宏变量中内容的起始,终止位置,如:MACRO2<2,4>$,表示使用宏变量数组
             的第2号元素中的第2至第4个字符做替换 */
            macro = inStr.substring( macroBegIndex, macroEndIndex );
            tmp = inStr.substring( macroBegIndex + 5, macroEndIndex );
            macroNo = StrUtil.Str2Int( tmp );
//System.out.println( "macroNo=" + macroNo + ",macro=" + macro + ",tmp=" + tmp );
            cpBegIndex = 0;
            cpEndIndex = macroList.get(macroNo).length() - 1;
            for ( i = 0; i < tmp.length(); i++ ) {
                ch = tmp.charAt(i);
                if ( ch == '<' )
                    cpBegIndex = StrUtil.Str2Int(tmp.substring(i + 1));
                else if( ch == ',' )
                {
                    cpEndIndex = StrUtil.Str2Int(tmp.substring(i + 1));
                    if ( cpBegIndex > cpEndIndex )
                        cpEndIndex = cpBegIndex;
                }
            }

//System.out.println( "cpBegIndex=" + cpBegIndex + ",cpEndIndex=" + cpEndIndex );


            /* 确定需要替换的宏表达式的起始,终止位置,如:@ settle_date='MACRO1$' ^,程序去掉@和^字符
             ,提取出settle_date='MACRO1$';如果宏数组1号元素为不为空值,将此表达式用1号元素替换,
             否则,将表达式置为空串;最后将表达式值替换到字符串中原宏表达式的位置上 */
            offSet1 = 1;
            offSet2 = 0;
            expBegIndex = inStr.indexOf( '@', startIndex );
            if ( expBegIndex == -1 || expBegIndex > macroBegIndex )
            {
                expBegIndex = macroBegIndex;
                expEndIndex = macroEndIndex;
                offSet1 = 0;
                offSet2 = 1;
            }
            else
            {
                expEndIndex = inStr.indexOf('^', startIndex);
                if (expEndIndex == -1 || expEndIndex < macroEndIndex) {
                    expBegIndex = macroBegIndex;
                    expEndIndex = macroEndIndex;
                    offSet1 = 0;
                    offSet2 = 1;
                }
            }
//System.out.println( "macroBegIndex=" + macroBegIndex + ",macroEndIndex=" + macroEndIndex );
//System.out.println( "expBegIndex=" + expBegIndex + ",expEndIndex=" + expEndIndex );

            tmp = inStr.substring( expBegIndex + offSet1, expEndIndex + offSet2 );
//System.out.println( "tmp_old=" + tmp );

            macroVal = macroList.get(macroNo);
//System.out.println( "macroVal=" + macroVal + ",macroVal.sub=" + macroVal.substring( cpBegIndex, cpEndIndex + 1 ) );
            if ( macroVal == null || macroVal.length() == 0 )
                tmp = "";
            else
            {
                tmp = tmp.replaceAll( "[$]", "" );
                tmp = tmp.replaceAll( macro, macroVal.substring(cpBegIndex, cpEndIndex + 1));
            }
//System.out.println( "tmp_new=" + tmp );
            inStr = inStr.substring( 0, expBegIndex ) + tmp + inStr.substring( expEndIndex + 1 );
            startIndex = expBegIndex + tmp.length();
        }

        return inStr;
    }

    public static void main( String[] args ) {
        int i = 255;
        String pwd = "123459A";
        String cardNo = "62269607000028923";
        byte a = (byte)( ( i % 256 ) & 0x000000FF );
        String tmp = String.format( "%02x", a );
        int b = a & 0x000000FF;
        System.out.println( b + "hello" );
//        StringBuffer a = new StringBuffer( 2 );
//        String oldStr = "Select * from tab where @date='MACRO1<2,4>$'^  and @time='MACRO0<1,4>$'^  ";
//        List<String> macroList = new ArrayList<String>( );
//        macroList.add( "20071110" );
//        macroList.add( "settle_date" );

     /*   IsoHandle isoHandle = new IsoHandle();
        byte[] isoStr = isoHandle.Pack();*/
     //   isoHandle.Unpack( isoStr );
/*
        byte[] tmpArray = StrUtil.AscToBcd( pwd, StrUtil.LEFT_ALIGN );
        String tmp = StrUtil.BcdToAsc( tmpArray, 0, StrUtil.LEFT_ALIGN );
        System.out.println( "tmp=" + tmp );
*/
//        String newStr = ReplaceMacro( oldStr, macroList );
//        String newStr = oldStr.replaceAll( "[$]", "" );
//        System.out.println( "newStr=" + newStr );
//        newStr = newStr.replaceAll( "MACRO1<2,4>", "0000" );
//        System.out.println( "newStr=" + newStr );


    }
}

