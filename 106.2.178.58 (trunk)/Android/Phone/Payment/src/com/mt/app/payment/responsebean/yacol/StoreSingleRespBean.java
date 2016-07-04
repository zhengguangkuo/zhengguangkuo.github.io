package com.mt.app.payment.responsebean.yacol;

import java.util.ArrayList;

import com.mt.android.sys.bean.base.ResponseBean;

public class StoreSingleRespBean extends ResponseBean {
	private String addr;
	private String appTimes;
	private String busline;
	private SingleCampaignInfoRespBean campaignInfo;
	private int collectTimes;
	private ArrayList<String> consumeClass;
	private ContactRespBean contact;
	private String disDescription;
	private String goldDiscount;
	private boolean hasPos;
	private String id;
	private ArrayList<String> images;
	private String intro;
	private ArrayList<KeyWordRespBean> keywordList;
	private int moderateAppraiseCount;
	private String name;
	private int negativeAppraiseCount;
	private int positiveAppraiseCount;
	private int score;
	private String silverDiscount;
	private String status;
	private String statusName;
	private String viewTimes;
	private String vipDiscount;
	private ArrayList<WarmHintRespBean> warmHint;
	private String xpos;
	private String ykylDiscount;
	private String ypos;
	private String consumptionPerPerson;

	public ArrayList<String> getImages() {
		return images;
	}

	public void setImages(ArrayList<String> images) {
		this.images = images;
	}

	public int getModerateAppraiseCount() {
		return moderateAppraiseCount;
	}

	public void setModerateAppraiseCount(int moderateAppraiseCount) {
		this.moderateAppraiseCount = moderateAppraiseCount;
	}

	public int getNegativeAppraiseCount() {
		return negativeAppraiseCount;
	}

	public void setNegativeAppraiseCount(int negativeAppraiseCount) {
		this.negativeAppraiseCount = negativeAppraiseCount;
	}

	public int getPositiveAppraiseCount() {
		return positiveAppraiseCount;
	}

	public void setPositiveAppraiseCount(int positiveAppraiseCount) {
		this.positiveAppraiseCount = positiveAppraiseCount;
	}

	public ArrayList<WarmHintRespBean> getWarmHint() {
		return warmHint;
	}

	public void setWarmHint(ArrayList<WarmHintRespBean> warmHint) {
		this.warmHint = warmHint;
	}

	public String getConsumptionPerPerson() {
		return consumptionPerPerson;
	}

	public void setConsumptionPerPerson(String consumptionPerPerson) {
		this.consumptionPerPerson = consumptionPerPerson;
	}

	public String getAddr() {
		return this.addr;
	}

	public String getAppTimes() {
		return this.appTimes;
	}

	public String getBusline() {
		return this.busline;
	}

	public SingleCampaignInfoRespBean getCampaignInfo() {
		return this.campaignInfo;
	}

	public int getCollectTimes() {
		return this.collectTimes;
	}

	public ArrayList<String> getConsumeClass() {
		return this.consumeClass;
	}

	public ContactRespBean getContact() {
		return this.contact;
	}

	public String getDisDescription() {
		return this.disDescription;
	}

	public String getGoldDiscount() {
		return this.goldDiscount;
	}

	public String getId() {
		return this.id;
	}

	public String getIntro() {
		return this.intro;
	}

	public ArrayList<KeyWordRespBean> getKeywordList() {
		return this.keywordList;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

	public String getSilverDiscount() {
		return this.silverDiscount;
	}

	public String getStatus() {
		return this.status;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public String getViewTimes() {
		return this.viewTimes;
	}

	public String getVipDiscount() {
		return this.vipDiscount;
	}

	public String getXpos() {
		return this.xpos;
	}

	public String getYkylDiscount() {
		return this.ykylDiscount;
	}

	public String getYpos() {
		return this.ypos;
	}

	public boolean isHasPos() {
		return this.hasPos;
	}

	public void setAddr(String paramString) {
		this.addr = paramString;
	}

	public void setAppTimes(String paramString) {
		this.appTimes = paramString;
	}

	public void setBusline(String paramString) {
		this.busline = paramString;
	}

	public void setCampaignInfo(
			SingleCampaignInfoRespBean paramSingleCampaignInfo) {
		this.campaignInfo = paramSingleCampaignInfo;
	}

	public void setCollectTimes(int paramInt) {
		this.collectTimes = paramInt;
	}

	public void setConsumeClass(ArrayList<String> paramArrayList) {
		this.consumeClass = paramArrayList;
	}

	public void setContact(ContactRespBean paramContact) {
		this.contact = paramContact;
	}

	public void setDisDescription(String paramString) {
		this.disDescription = paramString;
	}

	public void setGoldDiscount(String paramString) {
		this.goldDiscount = paramString;
	}

	public void setHasPos(boolean paramBoolean) {
		this.hasPos = paramBoolean;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public void setIntro(String paramString) {
		this.intro = paramString;
	}

	public void setKeywordList(ArrayList<KeyWordRespBean> paramArrayList) {
		this.keywordList = paramArrayList;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setScore(int paramInt) {
		this.score = paramInt;
	}

	public void setSilverDiscount(String paramString) {
		this.silverDiscount = paramString;
	}

	public void setStatus(String paramString) {
		this.status = paramString;
	}

	public void setStatusName(String paramString) {
		this.statusName = paramString;
	}

	public void setViewTimes(String paramString) {
		this.viewTimes = paramString;
	}

	public void setVipDiscount(String paramString) {
		this.vipDiscount = paramString;
	}

	public void setXpos(String paramString) {
		this.xpos = paramString;
	}

	public void setYkylDiscount(String paramString) {
		this.ykylDiscount = paramString;
	}

	public void setYpos(String paramString) {
		this.ypos = paramString;
	}

}