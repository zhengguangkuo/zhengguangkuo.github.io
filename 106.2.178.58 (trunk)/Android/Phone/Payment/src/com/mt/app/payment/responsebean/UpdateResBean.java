package com.mt.app.payment.responsebean;

import com.mt.android.sys.bean.base.ResponseBean;

public class UpdateResBean extends ResponseBean{
	 public int  versionNo;
	 public String linkAddress;
	 

		public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

		public int getVersionNo() {
			return versionNo;
		}

		public void setVersionNo(int versionNo) {
			this.versionNo = versionNo;
		}
}
