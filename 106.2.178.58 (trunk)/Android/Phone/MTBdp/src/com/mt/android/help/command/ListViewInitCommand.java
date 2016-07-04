package com.mt.android.help.command;

import org.apache.log4j.Logger;

import com.mt.android.help.listview.Person;
import com.mt.android.help.listview.HelpListViewBean;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;

public class ListViewInitCommand extends AbstractCommand{

	private static Logger log = Logger.getLogger(ListViewInitCommand.class);
	
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
		// TODO Auto-generated method stub
		log.debug("go");
		Response response = new Response();
		HelpListViewBean lsviewBean = new HelpListViewBean();
        response.setError(false);
        Person person1 = new Person();
		person1.setName("张三");
		person1.setAge("27");
		
		Person person2 = new Person();
		person2.setName("李四");
		person2.setAge("23");
		
		lsviewBean.getHelp_listview().add(person1);
		lsviewBean.getHelp_listview().add(person2);
        
		response.setData(lsviewBean);
        setResponse(response);
        //OpenCard();
	}
	
	@Override
	protected void onAfterExecute()
	{
		log.debug("onAfterExecute");
		
		Response response = getResponse();
		response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		/*CardReadBean cardRead = new CardReadBean();
		cardRead.setCardNo("123456");
		response.setData(cardRead);
		
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else
		{
			
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			
		}*/
	}


}
