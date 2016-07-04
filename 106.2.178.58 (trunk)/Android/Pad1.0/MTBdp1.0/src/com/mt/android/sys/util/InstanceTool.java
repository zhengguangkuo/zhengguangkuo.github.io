package com.mt.android.sys.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mt.android.sys.mvc.controller.Controller;

import android.os.Bundle;

public class InstanceTool {
	/**
	 * ���û�״̬��������
	 * @param savedInstanceState
	 */
     public void set(Bundle savedInstanceState){
	        if(savedInstanceState != null){
	        	ArrayList lKey = new ArrayList();
		        ArrayList lVal = new ArrayList();
		        
		        //����map
		        Set set = Controller.session.entrySet();
			    Iterator it=set.iterator();
			    while(it.hasNext()){
			    	Map.Entry  entry=(Map.Entry) it.next();
			    	
			    	if(entry.getValue() instanceof Serializable){//���������л�����Ϣ��������
				    	lKey.add(entry.getKey());
				    	lVal.add(entry.getValue());
			    	}

			    }
		        
		        Bundle bundle = new Bundle();
		        bundle.putSerializable("lKey", lKey);
		        bundle.putSerializable("lVal", lVal);
		        savedInstanceState.putAll(bundle);
	        }
     }
     /**
      * ���û�״̬���лָ�
      * @param savedInstanceState
      */
     public void get(Bundle savedInstanceState){
 
    	 if(savedInstanceState != null){
    		 ArrayList lKey = (ArrayList)savedInstanceState.getSerializable("lKey");
             ArrayList lVal = (ArrayList)savedInstanceState.getSerializable("lVal");
             
             for(int i = 0; i < lKey.size(); i ++){
             	try{
             		Controller.session.put(lKey.get(i).toString(), lVal.get(i));
             	}catch(Exception ex){
             		ex.printStackTrace();
             	}
             }
    	 }
    	
     }
}
