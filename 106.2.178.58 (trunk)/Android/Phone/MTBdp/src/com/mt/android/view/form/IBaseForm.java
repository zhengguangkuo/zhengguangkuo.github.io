package com.mt.android.view.form;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;

/**
 * ������ ��Activity�Ŀؼ�ȡֵװ�䵽Bean���л��߷���
 * @author zyb 
 *
 */
public interface  IBaseForm{
	
	
	/**
	 * ��JavaBean���е�����ֵװ�䵽Form������,û��ListViewʱ,context��Ϊnull
	 * @return
	 */
    public boolean bean2Form(Object bean, View layout ,Context context);
    /**
     * ��Form�����е�����ֵװ�䵽Bean����
     * @return
     */
    public boolean form2Bean(Object bean, View layout);
    
    /**
     * ����һ��װ������ָ��ViewҶ�ӽڵ��List
     */
    public List<View> getAllLeafElements(View view);
    
    /**
     * ��JavaBean����������ֵ�Ķ�Ӧ��ϵ���ֵ�Map����
     */
    public Map<String, Object> setBeanValToMap(Object bean);
    
    /**
     * ����ͼ�ؼ���������<contentDescription>��ֵ�Ķ�Ӧ��ϵ���ֵ�Map����
     */
    public Map<String, Object> setViewValToMap(List<View> viewList);
    
}

