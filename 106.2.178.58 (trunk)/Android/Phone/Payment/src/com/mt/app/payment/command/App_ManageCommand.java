package com.mt.app.payment.command;
import org.apache.log4j.Logger;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.tab.activity.TabAppManageCardActivity;
public class App_ManageCommand extends AbstractCommand {

	private static Logger log = Logger.getLogger(App_ManageCommand.class);
	
	@Override
	protected void prepare() {
		log.debug("prepare");
	}

	@Override
	protected final void onBeforeExecute() {
		log.debug("onBeforeExecute");
	}

	@Override
	protected void go() {
		try {
			Response response = new Response();
			setResponse(response);
		} catch (Exception e) {
			Response response = new Response();
			response.setError(true);
			setResponse(response);
		}
	}
	
	@Override
	protected void onAfterExecute()
	{	
		Response response = getResponse();
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else
		{
			FrameDataSource.tabDataClass.put("tabClass", TabAppManageCardActivity.class);
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_EleCard_DemoTab"));
		}
	}
}
