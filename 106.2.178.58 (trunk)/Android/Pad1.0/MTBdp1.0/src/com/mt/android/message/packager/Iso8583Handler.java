package com.mt.android.message.packager;

import com.mt.android.message.iso.IsoHandle;
import com.mt.android.message.mdo.MessageDataObject;


public class Iso8583Handler {
	IsoHandle isoHandle = new IsoHandle();

	public  byte[] Pack(MessageDataObject mdo){
		//MessageField.init();
		byte[] isomsg = null;//isoHandle.Pack(mdo.getBody());
		return isomsg;
	}
	public  MessageDataObject Unpack(byte[] isomsg){
		MessageDataObject mdo = new MessageDataObject();
		//MessageField.init();
		//isoHandle.Unpack(isomsg, mdo.getBody());
		return mdo;
	}
}
