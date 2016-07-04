package com.mt.app.payment.responsebean;

import java.io.Serializable;
import java.util.Map;

import com.mt.android.sys.bean.base.ResponseBean;
public class ImageResultBean implements Serializable{
	private ResponseBean respBean;
	private Map<String , byte[]> imageMap;
	private String Message;
	public void setMessage(String message) {
		Message = message;
	}
	public String getMessage() {
		return Message;
	}
	public ResponseBean getRespBean() {
		return respBean;
	}
	public void setRespBean(ResponseBean respBean) {
		this.respBean = respBean;
	}
	public Map<String, byte[]> getImageMap() {
		return imageMap;
	}
	public void setImageMap(Map<String, byte[]> imageMap) {
		this.imageMap = imageMap;
	}
	
}
