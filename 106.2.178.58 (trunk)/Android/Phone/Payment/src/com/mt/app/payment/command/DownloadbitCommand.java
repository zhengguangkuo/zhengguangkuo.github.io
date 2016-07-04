package com.mt.app.payment.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.common.DownLoadImage;
import com.mt.app.payment.responsebean.ImageResultBean;

public class DownloadbitCommand extends AbstractCommand{
	private static Logger log = Logger.getLogger(DownloadbitCommand.class);
	ArrayList<String> path;
	@Override
	protected void go() {
		try {
			log.debug("go");
			Response response = new Response();
			path=(ArrayList<String>)getRequest().getData();
			 ImageResultBean be = new ImageResultBean();
				Map<String , byte[]> mmap = new HashMap<String , byte[]>();
				be.setImageMap(mmap);
				for(int i=0;i<path.size();i++){
					DownLoadImage.getImageMap(path.get(i), mmap);
				}
				
				response.setData(be);
				response.setError(false);
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
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		}
	}
}