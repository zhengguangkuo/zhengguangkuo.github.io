package com.wizarpos.apidemo.printer;

public class PrinterCommand 
{
    /*--------------------------��ӡ����-----------------------------*/

    /**
     * ��ӡ�л�����������ݲ���ǰ��ֽһ�С����л�����Ϊ��ʱֻ��ǰ��ֽһ�С�
     * 
     * @return
     */
    static public byte[] getCmdLf() 
    {
    	return new byte[] { (byte) 0x0A };
    }

    /**
     * ��ӡλ��������һ���Ʊ�λ,�Ʊ�λΪ 8 ���ַ�����ʼλ��
     * 
     * @return
     */
    static public byte[] getCmdHt() 
    {
    	return new byte[] { (byte) 0x09 };
    }

    /**
     * ��ӡ�������������,����кڱ깦��,��ӡ���ֽ����һ���ڱ�λ��
     * 
     * @return
     */
    static public byte[] getCmdFf() 
    {
    	return new byte[] { (byte) 0x0c };
    }

    /**
     * 
     * ��ӡ�л������������,����ǰ��ֽ n ���С� ������ֻ�Ա�����Ч,���ı� ESC 2,ESC 3 �������õ��м��ֵ��
     * 
     * @param n
     *            0-255
     * @return
     */
    static public byte[] getCmdEscJN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x4A, (byte) n };
    }

    /**
     * ��ӡ�������������,����кڱ깦��,��ӡ���ֽ����һ���ڱ�λ��
     * 
     * @return
     */
    static public byte[] getCmdEscFf() 
    {
    	return new byte[] { (byte) 0x1b, (byte) 0x0c };
    }

    /**
     * ��ӡ�л������������,����ǰ��ֽ n �С� �и�Ϊ ESC 2,ESC 3 �趨��ֵ
     * 
     * @param n
     *            0-255
     * @return
     */
    static public byte[] getCmdEscDN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x64, (byte) n };
    }

    /**
     * 1:��ӡ����������ģʽ,���ܴ�ӡ���ݲ���ӡ 0:��ӡ����������ģʽ,�����ܴ�ӡ����
     * 
     * @param n
     *            :0,1���λ��Ч
     * @return
     */
    static public byte[] getCmdEscN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x3d, (byte) n };
    }

    /*--------------------------�м����������-----------------------------*/

    /**
     * �����м��Ϊ 4 ����,32 ��
     * 
     * @return
     */
    static public byte[] getCmdEsc2() 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x32 };
    }

    /**
     * �����м��Ϊ n ���С� Ĭ��ֵ�м���� 32 �㡣
     * 
     * @param n
     *            :0-255
     * @return
     */
    static public byte[] getCmdEsc3N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x33, (byte) n };
    }

    /**
     * ���ô�ӡ�еĶ��뷽ʽ,ȱʡ:����� 0 �� n �� 2 �� 48 �� n �� 50 �����: n=0,48 ���ж���: n=1,49 �Ҷ���:
     * n=2,50
     * 
     * @param n
     *            :0 �� n �� 2 �� 48 �� n �� 50
     * @return
     */
    static public byte[] getCmdEscAN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x61, (byte) n };
    }

    /**
     * ���ô�ӡ����߾�,ȱʡΪ 0����߾�Ϊ nL+nH*256,��λ 0.125mm
     * 
     * @param nL
     * @param nH
     * @return
     */
    static public byte[] getCmdGsLNlNh(int nL, int nH) 
    {
    	return new byte[] { (byte) 0x1D, (byte) 0x4c, (byte) nL, (byte) nH };
    }

    /**
     * ���ô�ӡ����߾�,ȱʡΪ 0 ��߾�Ϊ nL+nH*256,��λ 0.125mm
     * 
     * @param nL
     * @param nH
     * @return
     */
    static public byte[] getCmdEsc$NlNh(int nL, int nH) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x24, (byte) nL, (byte) nH };
    }

    /*--------------------------�ַ���������-----------------------------*/

    /**
     * �������ô�ӡ�ַ��ķ�ʽ��Ĭ��ֵ�� 0
     * 
     * @param n
     *            λ 0:���� λ 1:1:���巴�� λ 2:1:�������µ��� λ 3:1:����Ӵ� λ 4:1:˫���߶� λ
     *            5:1:˫����� λ 6:1:ɾ����
     * @return
     */
    static public byte[] getCmdEsc_N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x21, (byte) n };
    }

    /**
     * n �ĵ� 4 λ��ʾ�߶��Ƿ�Ŵ�,���� 0 ��ʾ���Ŵ� n �ĸ� 4 λ��ʾ����Ƿ�Ŵ�,���� 0 ��ʾ���Ŵ�
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdGs_N(int n) 
    {
    	return new byte[] { (byte) 0x1D, (byte) 0x21, (byte) n };
    }

    /**
     * ���� 0 ʱȡ������Ӵ� �� 0 ʱ��������Ӵ�
     * 
     * @param n
     *            ���λ��Ч
     * @return
     */
    static public byte[] getCmdEscEN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x45, (byte) n };
    }

    /**
     * Ĭ��ֵ:0
     * 
     * @param n
     *            :��ʾ�����ַ�֮��ļ��
     * @return
     */
    static public byte[] getCmdEscSpN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x20, (byte) n };
    }

    /**
     * ������֮�������ַ�����������ȵ� 2 ����ӡ; ����������ûس����� DC4 ����ɾ����
     * 
     * @return
     */
    static public byte[] getCmdEscSo() 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x0E };
    }

    /**
     * ����ִ�к�,�ַ��ָ�������ȴ�ӡ
     * 
     * @return
     */
    static public byte[] getCmdEscDc4() 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x14 };
    }

    /**
     * Ĭ��:0
     * 
     * @param n
     *            n=1:�����ַ����µ��� n=0:ȡ���ַ����µ���
     * @return
     */
    static public byte[] getCmdEsc__N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x7B, (byte) n };
    }

    /**
     * Ĭ��:0
     * 
     * @param n
     *            n=1:�����ַ����״�ӡ n=0:ȡ���ַ����״�ӡ
     * @return
     */
    static public byte[] getCmdGsBN(int n) 
    {
    	return new byte[] { (byte) 0x1D, (byte) 0x42, (byte) n };
    }

    /**
     * Ĭ��:0
     * 
     * @param n
     *            n=0-2,�»��ߵĸ߶�
     * @return
     */
    static public byte[] getCmdEsc___N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x2D, (byte) n };
    }

    /**
     * 
     * @param n
     *            n=1:ѡ���û��Զ����ַ���; n=0:ѡ���ڲ��ַ���(Ĭ��)
     * @return
     */
    static public byte[] getCmdEsc____N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x25, (byte) n };
    }

    /**
     * ���������û��Զ����ַ�,�������� 32 ���û��Զ����ַ���
     * 
     * @return
     */
    static public byte[] getCmdEsc_SNMW() 
    {
		return null;
    }

    /**
     * ��������ȡ���û��Զ�����ַ�,�ַ�ȡ����,ʹ��ϵͳ���ַ���
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEsc_____N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x25, (byte) n };
    }

    /**
     * ѡ������ַ��������İ汾��֧�ָ����
     * 
     * @param n
     *            �����ַ�����������:0:USA 1:France 2:Germany 3:U.K. 4:Denmark 1 5:Sweden
     *            6:Italy 7:Spain1 8:Japan 9:Norway 10:Denmark II 11:Spain II
     *            12:Latin America 13:Korea
     * @return
     */
    static public byte[] getCmdEscRN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x52, (byte) n };
    }

    /**
     * ѡ���ַ�����ҳ,�ַ�����ҳ����ѡ�� 0x80~0xfe �Ĵ�ӡ�ַ������İ汾��֧�ָ�����
     * 
     * @param n
     *            �ַ�����ҳ������ ��:0:437 1:850
     * @return
     */
    static public byte[] getCmdEscTN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x74, (byte) n };
    }

    /*--------------------------ͼ�δ�ӡ���� �� -----------------------------*/

    /*--------------------------������������-----------------------------*/

    /**
     * ����/��ֹ������������,��ʱ��֧�ָ����
     * 
     * @param n
     *            n=1,��ֹ���� n=0,������(Ĭ��)
     * @return
     */
    static public byte[] getCmdEscC5N(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x63, (byte) 0x35, (byte) n };
    }

    /*--------------------------��ʼ������-----------------------------*/

    /**
     * ��ʼ����ӡ���������ӡ������ �ָ�Ĭ��ֵ ѡ���ַ���ӡ��ʽ ɾ���û��Զ����ַ�
     * 
     * @return
     */
    static public byte[] getCmdEsc_() 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x40 };
    }

    /*--------------------------״̬��������-----------------------------*/

    /**
     * ���������Ϳ��ư�״̬
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEscVN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x76, (byte) n };
    }

    /**
     * ����Чʱ,��ӡ������״̬�ı�,���Զ�����״̬����������ϸ����ESC/POSָ���
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdGsAN(int n) 
    {
    	return new byte[] { (byte) 1D, (byte) 61, (byte) n };
    }

    /**
     * �����������ܱ��豸״̬,���Դ����ʹ�ӡ����Ч�������֧�֡���ϸ����ESC/POSָ���
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEscUN(int n) 
    {
    	return new byte[] { (byte) 0x1B, (byte) 0x75, (byte) n };
    }

    /*--------------------------�����ӡ���� �� -----------------------------*/

    /*--------------------------���ư�������� �� -----------------------------*/

    /**
     * �Զ����Ʊ�λ(2���ո�)
     * 
     * @return
     */
    static public byte[] getCustomTabs() 
    {
    	return "  ".getBytes();
    }

}
