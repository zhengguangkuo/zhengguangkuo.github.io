package com.mt.app.payment.responsebean.yacol;

import java.util.ArrayList;

import com.mt.android.sys.bean.base.ResponseBean;

public class SingleCampaignInfoRespBean extends ResponseBean {
	private String blockName ;
	private String catering ;
	private String actionAtToday ;
    private ArrayList<CampaignDetailRespBean> campaignDetailList ;
    
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getCatering() {
		return catering;
	}
	public void setCatering(String catering) {
		this.catering = catering;
	}
	public String getActionAtToday() {
		return actionAtToday;
	}
	public void setActionAtToday(String actionAtToday) {
		this.actionAtToday = actionAtToday;
	}
	public ArrayList<CampaignDetailRespBean> getCampaignDetailList() {
		return campaignDetailList;
	}
	public void setCampaignDetailList(
			ArrayList<CampaignDetailRespBean> campaignDetailList) {
		this.campaignDetailList = campaignDetailList;
	}
    
    
}