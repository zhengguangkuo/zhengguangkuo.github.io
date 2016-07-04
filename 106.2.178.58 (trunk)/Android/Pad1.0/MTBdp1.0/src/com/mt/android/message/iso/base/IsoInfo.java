package com.mt.android.message.iso.base;

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
public class IsoInfo {
    public static final byte FIXED = 0x00;  /* 固定长度 */
    public static final byte LLVAR = 0x40;  /* 两位变长 */
    public static final byte LLLVAR = (byte)0x80; /* 三位变长 */
    public static final byte BINARY = 0x10; /* 二进制数据 */
    public static final byte OTHER = 0x00;  /* 非二进制数据 */
    public static final byte BCD = 0x08;    /* BCD数据 */
    public static final byte ASCII = 0x00;  /* ASCII数据 */
    public static final byte SYMBOL = 0x04; /* 有借贷记符号 */
    public static final byte UNSYMBOL = 0x00; /* 无借贷记符号 */
    public static final byte LEFT = 0x02;   /* 左对齐 */
    public static final byte RIGHT = 0x00;  /* 右对齐 */
    public static final byte BLANK = 0x01;  /* 补空格 */
    public static final byte ZERO = 0x00;   /* 补零   */

    int len;  /* 8583域长度 */
    byte type;  /* 8583域属性 */

    public IsoInfo( int len, byte type ) {
        this.len = len;
        this.type = type;
    }
}
