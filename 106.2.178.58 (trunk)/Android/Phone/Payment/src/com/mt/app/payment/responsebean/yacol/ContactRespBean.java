package com.mt.app.payment.responsebean.yacol;

import com.mt.android.sys.bean.base.ResponseBean;

public class ContactRespBean extends ResponseBean {
  private String areaCode;
  private String ext;
  private String phone;

  public String getAreaCode()
  {
    return this.areaCode;
  }

  public String getExt()
  {
    return this.ext;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setAreaCode(String paramString)
  {
    this.areaCode = paramString;
  }

  public void setExt(String paramString)
  {
    this.ext = paramString;
  }

  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
}