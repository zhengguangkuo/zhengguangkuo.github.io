package com.mt.android.message.mdo;

import com.mt.android.message.annotation.MsgDirection;

public class MessageBody {
	@MsgDirection(direction = "double")
	private String msgId = "";// 消息类型

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
