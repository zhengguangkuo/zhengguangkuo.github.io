package com.mt.android.message.mdo;

public class MessageDataObject {
	public MessageHeader header;
	public MessageBody body;
	public MessageHeader getHeader() {
		return header;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public MessageBody getBody() {
		return body;
	}

	public void setBody(MessageBody body) {
		this.body = body;
	}
}
