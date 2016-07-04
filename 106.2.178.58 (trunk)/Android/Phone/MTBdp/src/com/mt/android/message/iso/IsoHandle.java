package com.mt.android.message.iso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import android.util.Log;

import com.mt.android.message.annotation.MsgDirection;
import com.mt.android.message.iso.base.Iso8583;
import com.mt.android.message.iso.base.IsoInfo;
import com.mt.android.message.iso.base.IsoInfoFace;
import com.mt.android.message.iso.util.StrUtil;
import com.mt.android.message.mdo.MessageBody;
import com.mt.android.message.mdo.MessageDataObject;
import com.mt.android.message.mdo.MessageField;
import com.mt.android.message.mdo.MessageHeader;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IsoHandle implements IsoInfoFace {
	private static Logger log = Logger.getLogger(IsoHandle.class);
    public Iso8583 iso8583;


    public IsoHandle() {
        try {
            iso8583 = new Iso8583(GenIsoInfo());
        }
        catch ( Exception ex )
        {
            log.error( ex.toString());
        }
    }

    public IsoInfo[] GenIsoInfo()
    {
        IsoInfo[] tmpIsoInfo = {
        //BCD
        new IsoInfo( 4, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),        /* 1域MsgId */
        //BCD
        new IsoInfo( 99, (byte)(LLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),           
        //BCD
        new IsoInfo( 6, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),
        //BCD
        new IsoInfo( 12, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),      
        new IsoInfo( 36, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),      /* 5域    */
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),   
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),                                          /* 10域    */
        //BCD
        new IsoInfo( 6, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 6, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 4, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 4, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 4, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),      /* 15域    */
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),                                        /* 20域    */
        new IsoInfo( 0, (byte)0x00 ),
        //BCD
        new IsoInfo( 3, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 3, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        new IsoInfo( 0, (byte)0x00 ),
        //BCD
        new IsoInfo( 2, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),      /* 25域    */
        //BCD
        new IsoInfo( 2, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),      
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),                                       /* 30域    */
        new IsoInfo( 0, (byte)0x00),
        //BCD
        new IsoInfo( 99, (byte)(LLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),     
        new IsoInfo( 0, (byte)(0x00) ),
        new IsoInfo( 0, (byte)(0x00) ),
        new IsoInfo( 99, (byte)(LLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),     /* 35域    */
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),
        //ASCII
        new IsoInfo( 12, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 6, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 2, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        new IsoInfo( 0, (byte)0x00 ),                                       /* 40域    */
        //ASCII
        new IsoInfo( 8, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 15, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 40, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 99, (byte)(LLVAR|OTHER|ASCII|UNSYMBOL|ZERO|RIGHT) ),
        new IsoInfo( 0, (byte)0x00 ),                                          /* 45域    */
        new IsoInfo( 0, (byte)0x00 ),
        new IsoInfo( 0, (byte)0x00 ),
        //BIN
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|BINARY|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 3, (byte)(FIXED|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        new IsoInfo( 0, (byte)0x00 ),                                          /* 50域    */
        new IsoInfo( 0, (byte)0x00 ),
        //BIN
        new IsoInfo( 8, (byte)(FIXED|BINARY|BINARY|UNSYMBOL|ZERO|RIGHT) ),
        //BCD
        new IsoInfo( 16, (byte)(FIXED|OTHER|BCD|UNSYMBOL|ZERO|RIGHT) ),
        //ASCII
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|ZERO|RIGHT) ),
        new IsoInfo( 0, (byte)0x00 ),                                          /* 55域    */
        
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //BCD
        new IsoInfo( 6, (byte)(FIXED|OTHER|BCD|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ),
        //ASCII
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|BLANK|LEFT) ), 
        //BCD
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),      /* 60域    */
        //BCD
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|BCD|UNSYMBOL|ZERO|LEFT) ),
        //ASCII
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|ZERO|RIGHT) ),
        //ASCII
        new IsoInfo( 999, (byte)(LLLVAR|OTHER|ASCII|UNSYMBOL|ZERO|RIGHT) ),
        //BIN
        new IsoInfo( 8, (byte)(FIXED|OTHER|BINARY|UNSYMBOL|ZERO|RIGHT) )
       };

       return( tmpIsoInfo );
    }

    public byte[] Pack()
    {
        String pwd = "123459A";
        String cardNo = "62269607000028923";
        byte[] isoStr = null, fldVal;

        try {
            iso8583.Clear();
            iso8583.SetFld( 1, "0100" );
            iso8583.SetFld( 2, cardNo );
            iso8583.SetFld( 3, "000000" );
            iso8583.SetFld( 96, "3A4B5D6F7C8B9A0B");
            iso8583.SetFld( 120, "D123456789" );
            isoStr = iso8583.IsoToMsg( );
            log.debug( StrUtil.ByteToHexString( isoStr, " " )) ;
            fldVal = iso8583.Get8583Fld( 120 );
            log.debug(StrUtil.ByteToHexString( fldVal, " " ));
        }
        catch ( Exception ex ) {
        	StringWriter out = new StringWriter();
        	ex.printStackTrace(new PrintWriter(out));
            log.error(ex.toString());
        }

        return( isoStr );
    }

    public void Unpack( byte[] isoStr )
    {
        String tmp;
        MessageDataObject mdo = null;

        try {
            iso8583.Clear();
            iso8583.MsgToIso( isoStr );
        }
        catch ( Exception ex ) {
        	StringWriter out = new StringWriter();
        	ex.printStackTrace(new PrintWriter(out));
            log.error(ex.toString());
            ex.printStackTrace();
        }
        
    }
    
    public byte[] Pack(MessageHeader header, MessageBody body)
    {
    	byte[] msg = null;
    	byte[] tmpLen = null;
    	byte[] msgLen = new byte[2];
    	byte[] headmsg = null;
        byte[] bodymsg = null, fldVal;

        try {
            iso8583.Clear();
            setFlds(body);
            String headstr = header.getId()+header.getDestion()+header.getSource()+header.getApptype();
            headstr = headstr+header.getVersion()+header.getTerminalStat()+header.getRequirement()+header.getObligate();
            headmsg=StrUtil.AscToBcd(headstr, StrUtil.LEFT_ALIGN);
            bodymsg = iso8583.IsoToMsg();
            
            msg = new byte[headmsg.length + bodymsg.length + 2];
            tmpLen = IsoHandle.toByteArray(msg.length-2, 2);
            msgLen[0] = tmpLen[1];
            msgLen[1] = tmpLen[0];
            
            System.arraycopy(msgLen, 0, msg, 0, msgLen.length);
            System.arraycopy(headmsg, 0, msg, 2, headmsg.length);
            System.arraycopy(bodymsg, 0, msg, headmsg.length + 2, bodymsg.length);
            String hexstr = StrUtil.ByteToHexString(msg, " ");
            System.out.println("debuginfo["+hexstr+ "]");
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            StringWriter out = new StringWriter();
        	ex.printStackTrace(new PrintWriter(out));
            log.error(ex.toString());
            ex.printStackTrace();
        }

        return(msg);
    }
    
    
    public void Iso2Obj(MessageBody body)
    {
        String tmp;
        
        try {
            getFlds(body);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            StringWriter out = new StringWriter();
        	ex.printStackTrace(new PrintWriter(out));
            log.error(ex.toString());
        }
    }
    
    private void setFlds(MessageBody body){
    	Class cla = body.getClass();
    	Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
    	Method method = null;
    	Map map = new HashMap<String, String>();
    	map = this.getValidFields(body, "request");
    	String debugInfo = "";
    	try{

    	    //获取到域编号
    		int fieldNo = -1;
    		//域名称
    		String fieldName = "";
    		//获取到域值
    		String fieldVal = "";
    		for(int j = 0; j < ma.length; j ++){
    			method = ma[j];
    			
    			if (method.getName().indexOf("get") != 0){//排除set方法
    				continue;
    			}
    		    
    			fieldName = method.getName().substring(3).toUpperCase();
    			
                if (map.get(fieldName.toUpperCase()) == null){
    				continue;
    			}
    			
    			fieldNo = MessageField.getFiledNo(fieldName);
    			Object objval= method.invoke(body, null);
    			
    			if(objval == null){
    				continue;
    			}
    			
    			fieldVal = objval.toString();
    			debugInfo = debugInfo + "#" + fieldNo + ":" + fieldVal;
    			
    			if (fieldNo > 0 && fieldNo <= 128){
        			iso8583.SetFld(fieldNo, fieldVal);
        		}

    		}
    	
    		printFieldLog(debugInfo);//打印日志信息
    		//设置msgId
    		Class scla = body.getClass().getSuperclass();
    		Method[] mas = scla.getDeclaredMethods();// 获取所有声明的方法数组
    		
    		for(int z = 0; z < mas.length; z ++){
    			method = mas[z];
    			if(mas[z].getName().toUpperCase().equalsIgnoreCase("GETMSGID")){
    				fieldVal = method.invoke(body, null).toString();
    				iso8583.SetFld(1, fieldVal);
    				z = mas.length;
    			}
    		}
    	}catch(Exception ex){
    		 ex.printStackTrace();
             StringWriter out = new StringWriter();
         	 ex.printStackTrace(new PrintWriter(out));
             log.error(ex.toString());
    	}
    	
    }
    private void printFieldLog(String info){
    	String[] tmp = info.substring(1).split("#");
    	String[] arr = new String[65];
    	
    	for(int j = 0; j < 65; j ++){
    		arr[j] = null;
    	}
    	
    	for(int z = 0; z< tmp.length; z ++){
    		int no = Integer.valueOf(tmp[z].split(":")[0]);
    		String val = "";
    		
    		if(tmp[z].split(":").length == 2){
    		   val = tmp[z].split(":")[1];
    		}
    		
    		arr[no] = val;
    	}
    	
    	for(int i = 0; i< arr.length; i++){
    		
    		if(arr[i] != null)
    		{
    			try{
    				Log.i("PACKINFO*************STRINGVAL***********", "NO:" + i + ",VALUE:[" + arr[i] + "]" + ",iLEN:" + arr[i].getBytes().length);	
    			}catch(Exception ex){
    				ex.printStackTrace();
    			}
    			
    		}
    	}
    }
    private void getFlds(MessageBody body){
    	Class cla = body.getClass();
    	Method[] ma = cla.getDeclaredMethods();// 获取所有声明的方法数组
    	Method method = null;
    	Map map = new HashMap<String, String>();
    	map = this.getValidFields(body, "response");
    	try{

    	    //获取到域编号
    		int fieldNo = -1;
    		//域名称
    		String fieldName = "";
    		//获取到域值
    		String fieldVal = "";
    		for(int j = 0; j < ma.length; j ++){
    			method = ma[j];
    			
    			if (method.getName().indexOf("set") != 0){//排除set方法
    				continue;
    			}
    		   
    			fieldName = method.getName().substring(3).toUpperCase();
    			
    			if (map.get(fieldName.toUpperCase()) == null){
    				continue;
    			}
    			
    			fieldNo = MessageField.getFiledNo(fieldName);
    			fieldVal = iso8583.GetFld(fieldNo);
    			
    			if (fieldNo > 0 && fieldNo <= 128){
        			method.invoke(body, fieldVal);
        		}

    		}
    		//设置MsgId的值
    		Method[] sma = cla.getSuperclass().getDeclaredMethods();// 获取所有声明的方法数组
    		
    		for(int z = 0; z < sma.length; z ++){
    			if (sma[z].getName().equalsIgnoreCase("setMsgId")){
    				sma[z].invoke(body, iso8583.GetFld(1));
    				z = sma.length;
    			}
    		}
    	
    	}catch(Exception ex){
    		 ex.printStackTrace();
             StringWriter out = new StringWriter();
         	 ex.printStackTrace(new PrintWriter(out));
             log.error(ex.toString());
    	}
    }
    private Map getValidFields(Object obj, String tag){
    	Map map = new HashMap<String, String>();
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for (Field m : fields) {
			if (m.isAnnotationPresent(MsgDirection.class)) {
				MsgDirection msgdire = m.getAnnotation(MsgDirection.class);
				String srcfiled = msgdire.direction();
				
				if(tag.equalsIgnoreCase("request")){
					if(srcfiled.equalsIgnoreCase("request") || srcfiled.equalsIgnoreCase("double")){
					   map.put(m.getName().toUpperCase(), "1");
					}
				}else{
					if(srcfiled.equalsIgnoreCase("response") || srcfiled.equalsIgnoreCase("double")){
						   map.put(m.getName().toUpperCase(), "1");
					}
				}
				
			}
		}
    	return map;
    }
    public static byte[] toByteArray(int iSource, int iArrayLen) {
		byte[] bLocalArr = new byte[iArrayLen];
		for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
			bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);

		}
		return bLocalArr;
	}
}
