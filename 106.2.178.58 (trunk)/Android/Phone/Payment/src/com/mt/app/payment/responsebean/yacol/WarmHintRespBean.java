package com.mt.app.payment.responsebean.yacol;

import com.mt.android.sys.bean.base.ResponseBean;

public class WarmHintRespBean extends ResponseBean
{
  private String content;
  private String title;

  public String getContent()
  {
    return this.content;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}