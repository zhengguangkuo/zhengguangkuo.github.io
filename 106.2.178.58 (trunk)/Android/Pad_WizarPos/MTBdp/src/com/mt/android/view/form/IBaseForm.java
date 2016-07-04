package com.mt.android.view.form;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;

/**
 * 公共类 将Activity的控件取值装配到Bean当中或者反向
 * @author zyb 
 *
 */
public interface  IBaseForm{
	
	
	/**
	 * 将JavaBean当中的属性值装配到Form表单当中,没有ListView时,context设为null
	 * @return
	 */
    public boolean bean2Form(Object bean, View layout ,Context context);
    /**
     * 将Form表单当中的属性值装配到Bean当中
     * @return
     */
    public boolean form2Bean(Object bean, View layout);
    
    /**
     * 返回一个装有所有指定View叶子节点的List
     */
    public List<View> getAllLeafElements(View view);
    
    /**
     * 将JavaBean中属性名与值的对应关系保持到Map当中
     */
    public Map<String, Object> setBeanValToMap(Object bean);
    
    /**
     * 将视图控件中属性名<contentDescription>与值的对应关系保持到Map当中
     */
    public Map<String, Object> setViewValToMap(List<View> viewList);
    
}

