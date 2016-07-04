package com.mt.app.payment.responsebean.yacol;

import com.mt.android.sys.bean.base.ResponseBean;

public class CampaignDetailRespBean extends ResponseBean {
  private String campaignDiscount;
  private String campaignPrice;
  private int consumeType;
  private String description;
  private String disDescription;

  public String getCampaignDiscount()
  {
    return this.campaignDiscount;
  }

  public String getCampaignPrice()
  {
    return this.campaignPrice;
  }

  public int getConsumeType()
  {
    return this.consumeType;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getDisDescription()
  {
    return this.disDescription;
  }

  public void setCampaignDiscount(String paramString)
  {
    this.campaignDiscount = paramString;
  }

  public void setCampaignPrice(String paramString)
  {
    this.campaignPrice = paramString;
  }

  public void setConsumeType(int paramInt)
  {
    this.consumeType = paramInt;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setDisDescription(String paramString)
  {
    this.disDescription = paramString;
  }
}