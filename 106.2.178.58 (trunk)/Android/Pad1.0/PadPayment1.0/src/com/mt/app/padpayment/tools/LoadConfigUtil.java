package com.mt.app.padpayment.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

public class LoadConfigUtil {

	/**
	 * 获取驱动的配置文件
	 */
	public static Map<String, String> LoadDriverPath(Context ctx) {
		Map<String, String> map = new HashMap<String, String>() ;
		try {
			InputStream is = ctx.getAssets().open("file/LoadDriver.xml") ;
			XmlPullParser parser = Xml.newPullParser() ;
			parser.setInput(is, "utf-8") ;
			int type = parser.getEventType() ;
			while(type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG :
					if("PinPadDriver".equals(parser.getName())) {
						map.put("PinPadDriver", parser.nextText()) ;
					} else if("CardReaderDriver".equals(parser.getName())) {
						map.put("CardReaderDriver", parser.nextText()) ;
					} else if("PrinterDriver".equals(parser.getName())) {
						map.put("PrinterDriver", parser.nextText()) ;						
					} else if("Layout".equals(parser.getName())) {
						map.put("Layout", parser.nextText()) ;
					}
					break;
				}
				type = parser.next() ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return map ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
