package com.mt.app.payment.responsebean.yacol;

import com.mt.android.sys.bean.base.ResponseBean;

public class KeyWordRespBean extends ResponseBean {
  private String category;
  private String kw;
  private String kwId;

  public String getCategory()
  {
    return this.category;
  }

  public String getKw()
  {
    return this.kw;
  }

  public String getKwId()
  {
    return this.kwId;
  }

  public void setCategory(String paramString)
  {
    this.category = paramString;
  }

  public void setKw(String paramString)
  {
    this.kw = paramString;
  }

  public void setKwId(String paramString)
  {
    this.kwId = paramString;
  }
}