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
    public static final byte FIXED = 0x00;  /* �̶����� */
    public static final byte LLVAR = 0x40;  /* ��λ�䳤 */
    public static final byte LLLVAR = (byte)0x80; /* ��λ�䳤 */
    public static final byte BINARY = 0x10; /* ���������� */
    public static final byte OTHER = 0x00;  /* �Ƕ��������� */
    public static final byte BCD = 0x08;    /* BCD���� */
    public static final byte ASCII = 0x00;  /* ASCII���� */
    public static final byte SYMBOL = 0x04; /* �н���Ƿ��� */
    public static final byte UNSYMBOL = 0x00; /* �޽���Ƿ��� */
    public static final byte LEFT = 0x02;   /* ����� */
    public static final byte RIGHT = 0x00;  /* �Ҷ��� */
    public static final byte BLANK = 0x01;  /* ���ո� */
    public static final byte ZERO = 0x00;   /* ����   */

    int len;  /* 8583�򳤶� */
    byte type;  /* 8583������ */

    public IsoInfo( int len, byte type ) {
        this.len = len;
        this.type = type;
    }
}
