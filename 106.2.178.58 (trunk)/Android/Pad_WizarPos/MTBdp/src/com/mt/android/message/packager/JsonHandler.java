package com.mt.android.message.packager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.mt.android.sys.bean.base.RequestBean;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.util.Constants;

public class JsonHandler {
	 private static Logger log = Logger.getLogger(JsonHandler.class);
     public static String Pack(RequestBean messbean){
    	 return "";
     }
     /**
      * ���
      * @param json
      * @param cla
      * @param jsonType ��Ҫ�����json����һ��object������jsonTypeΪ object ;��array�� jsonTypeΪarray
      * @return
      */
     public static List<ResponseBean> UnPack(String json, Class cla,String jsonType){
    	 List<ResponseBean> list=new ArrayList<ResponseBean>();
    	 log.debug(" JsonPackUnpack unpack");
    	 if(jsonType.equals(Constants.JSONTYPE_OBJECT)){//���json��object����
    		 log.debug(" JsonType object");
    		 Object obj = (Object) JSONObject.parseObject(json, cla);
    		 list.add((ResponseBean) obj);
    	 }else if(jsonType.equals(Constants.JSONTYPE_ARRAY)){//���json��array����
    		 log.debug(" JsonType Array");
    		 list=JSONObject.parseArray(json, cla);
    	 }
    	 log.debug("unpack finish");
    	 return list;
     }
}
