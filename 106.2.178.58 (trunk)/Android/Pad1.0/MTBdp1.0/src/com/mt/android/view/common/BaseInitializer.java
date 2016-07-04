package com.mt.android.view.common;


import com.mt.android.global.Globals;
import com.mt.android.help.activity.AgentActivity;
import com.mt.android.help.activity.ListViewShowActivity;
import com.mt.android.help.activity.MainMenuActivity;
import com.mt.android.help.activity.SimpleViewPagerSlideMenuActivity;
import com.mt.android.help.command.ListViewInitCommand;
import com.mt.android.help.command.ListViewSelectCommand;
import com.mt.android.help.command.TabTopShowCommand;
import com.mt.android.help.command.ViewSlideCommand;
import com.mt.android.sys.mvc.command.CommandExecutor;
import com.mt.android.sys.mvc.controller.Controller;

public class BaseInitializer
{
	public void superEnsureInitialized(){

		BaseActivityID ActivityID = (BaseActivityID) Globals.map.get("ActivityID");
		
			// ×¢²áActivity
			Controller ctrl = Controller.getInstance();
			if (ctrl != null) {
				ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_MAIN"),
						MainMenuActivity.class);
				ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_LISTVIEW_SHOW"),
						ListViewShowActivity.class);
				ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_TAB_TOP_SHOW"),
						AgentActivity.class);
				ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_TAB_BOTTOM_SHOW"),
						AgentActivity.class);
				ctrl.registerActivity(ActivityID.map.get("ACTIVITY_ID_VIEW_SLIDE"),
						SimpleViewPagerSlideMenuActivity.class);

			}
		

		// ×¢²áCommandÃüÁî
		CommandExecutor ce = CommandExecutor.getInstance();
		if (ce != null) {
			BaseCommandID CommandID = (BaseCommandID) Globals.map.get("CommandID");
			
				ce.registerCommand(CommandID.map.get("LISTVIEW_SHOW"),
						ListViewSelectCommand.class);
				ce.registerCommand(CommandID.map.get("LISTVIEW_INIT"),
						ListViewInitCommand.class);
				ce.registerCommand(CommandID.map.get("TAB_TOP_SHOW"),
						TabTopShowCommand.class);
				ce.registerCommand(CommandID.map.get("VIEW_SLIDE"), ViewSlideCommand.class);
			
		}
	
	}
	public  void ensureInitialized() {superEnsureInitialized();}
}
