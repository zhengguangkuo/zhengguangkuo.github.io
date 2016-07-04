package com.mt.app.padpayment.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.frame.smart.config.DrawListViewAdapter;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;

/**
 * 多选删除柜员command
 * @author lzw
 *
 */
public class AdminDeleteListCommand extends AbstractCommand{
	

	//private ArrayList<Map<String, String>> listDelete=(ArrayList<Map<String, String>>)Controller.session.get("AdminDeleteNum");
    private static DbHandle dbhandle = new DbHandle();
	@Override
	protected void go() {
		// TODO Auto-generated method stub
	
		//获得多选list的id，循环操作数据库删除相应的柜员，赋值结果
	    String listDelete = (String) getRequest().getData();
		if(listDelete!= null)
		{
			dbhandle.delete("TBL_ADMIN", listDelete, null);
			
			Controller.session.put("AdminPassWordRu", "柜员删除成功");
		}
		else
		{
			Controller.session.put("AdminPassWordRu", "柜员删除失败");
		}
		DrawListViewAdapter.list.clear();
		//
		Response response = new Response();
		int[] flags=new int[1];
		flags[0]=Intent.FLAG_ACTIVITY_NO_HISTORY;
		int id=getRequest().getActivityID();
		response.setFlags(flags);
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_AdminPassWordRuAllActivity"));
		setResponse(response);
		
	}
}
