package com.mt.android.message.mdo;

import com.mt.android.message.iso.IsoHandle;


public class MsgTestBean extends MessageBody {
	private String msgId;// 消息类型
	private String cardNo;// 卡号
	private String password;// 密码
	private String mainKey;// 主密钥
	private String mac;// mac

	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public static void main(String[] args){
		MsgTestBean bean = new MsgTestBean();
		bean.setMsgId("0100");
		bean.setCardNo("62269607000028923");
		bean.setPassword("000000");
		bean.setMainKey("3A4B5D6F7C8B9A0B");
		bean.setMac("D123456789");
		//MessageField.init();
		
		IsoHandle isoHandle = new IsoHandle();
		/*byte[] isores = isoHandle.Pack(bean);
		
		MsgTestBean res = new MsgTestBean();
		isoHandle.Unpack(isores, res);
		System.out.println(res.getCardNo());*/
	}
}
