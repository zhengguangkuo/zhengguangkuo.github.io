package com.mt.android.frame.entity;

import android.view.View;
import com.mt.android.sys.mvc.common.Response;

public interface IPagerSlide {
	//ָ����ǰ��page��ʹ�õ�layout
	public int getViewPagerLayOut();
	//ָ����ǰpageҳ������
	public String getPageName();
	//�Զ����ʼ��
	public void initialize();
	//��ʼ����ʹ��layout��һЩ�����¼�
	public void layOutListenerInit(View layout);
	public void onSuccess(Response response);
	public void onError(Response response);
}
