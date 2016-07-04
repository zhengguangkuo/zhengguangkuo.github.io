package com.mt.app.padpayment.requestbean;

import com.mt.android.sys.mvc.common.Request;

public class LockInfoReqBean extends Request{
	//πÒ‘±∫≈
	private String lockNum;
	//√‹¬Î
	private String lockPass;
	public String getLockNum() {
		return lockNum;
	}
	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}
	public String getLockPass() {
		return lockPass;
	}
	public void setLockPass(String lockPass) {
		this.lockPass = lockPass;
	}
	
}
