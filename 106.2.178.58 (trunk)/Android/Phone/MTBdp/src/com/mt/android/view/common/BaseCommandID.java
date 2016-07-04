package com.mt.android.view.common;

import java.util.HashMap;
import java.util.Map;

import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;

public class BaseCommandID {
	public BaseCommandID(){
		registerBaseCommand();
	}
	public static Map<String, Integer> map = Controller.appmap;

	public void registerBaseCommand() {
		map.put("LISTVIEW_SHOW",CommandExecutor.IDistributer());
		map.put("LISTVIEW_INIT",CommandExecutor.IDistributer());
		map.put("HOMEPAGE_FETCH",CommandExecutor.IDistributer());
		map.put("BEFORE_CARD_READ",CommandExecutor.IDistributer());
		map.put("CARD_READING",CommandExecutor.IDistributer());
		map.put("TAB_TOP_SHOW",CommandExecutor.IDistributer());
		map.put("TAB_BOTTOM_SHOW",CommandExecutor.IDistributer());
		map.put("VIEW_SLIDE",CommandExecutor.IDistributer());
		map.put("LAYOUT_REUSE",CommandExecutor.IDistributer());
	}

	public void registerCommand() {
		registerBaseCommand();
	}
}
