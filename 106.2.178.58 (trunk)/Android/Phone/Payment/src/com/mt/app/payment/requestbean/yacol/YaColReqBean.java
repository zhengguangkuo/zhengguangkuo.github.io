package com.mt.app.payment.requestbean.yacol;

public class YaColReqBean {

	private String id ;
	private String returnType = "json" ;
	private String type = "1" ;
	private String v = "1.3" ;
	private String callType = "android" ;
	private String uuid ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getReturnType() {
		return returnType;
	}
	public String getType() {
		return type;
	}
	public String getV() {
		return v;
	}
	public String getCallType() {
		return callType;
	}
}
