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
      * 解包
      * @param json
      * @param cla
      * @param jsonType 需要解包的json串是一个object对象则jsonType为 object ;是array则 jsonType为array
      * @return
      */
     public static List<ResponseBean> UnPack(String json, Class cla,String jsonType){
    	 List<ResponseBean> list=new ArrayList<ResponseBean>();
    	 log.debug(" JsonPackUnpack unpack");
    	 if(jsonType.equals(Constants.JSONTYPE_OBJECT)){//如果json是object类型
    		 log.debug(" JsonType object");
    		 Object obj = (Object) JSONObject.parseObject(json, cla);
    		 list.add((ResponseBean) obj);
    	 }else if(jsonType.equals(Constants.JSONTYPE_ARRAY)){//如果json是array类型
    		 log.debug(" JsonType Array");
    		 list=JSONObject.parseArray(json, cla);
    	 }
    	 log.debug("unpack finish");
    	 return list;
     }
}
