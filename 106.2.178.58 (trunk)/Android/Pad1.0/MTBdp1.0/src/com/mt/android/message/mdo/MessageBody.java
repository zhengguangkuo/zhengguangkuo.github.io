package com.mt.android.message.mdo;

import com.mt.android.message.annotation.MsgDirection;

public class MessageBody {
	@MsgDirection(direction = "double")
	private String msgId = "";// ��Ϣ����

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
