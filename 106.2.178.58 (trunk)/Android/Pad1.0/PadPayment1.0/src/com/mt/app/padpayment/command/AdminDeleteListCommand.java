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
 * ��ѡɾ����Աcommand
 * @author lzw
 *
 */
public class AdminDeleteListCommand extends AbstractCommand{
	

	//private ArrayList<Map<String, String>> listDelete=(ArrayList<Map<String, String>>)Controller.session.get("AdminDeleteNum");
    private static DbHandle dbhandle = new DbHandle();
	@Override
	protected void go() {
		// TODO Auto-generated method stub
	
		//��ö�ѡlist��id��ѭ���������ݿ�ɾ����Ӧ�Ĺ�Ա����ֵ���
	    String listDelete = (String) getRequest().getData();
		if(listDelete!= null)
		{
			dbhandle.delete("TBL_ADMIN", listDelete, null);
			
			Controller.session.put("AdminPassWordRu", "��Աɾ���ɹ�");
		}
		else
		{
			Controller.session.put("AdminPassWordRu", "��Աɾ��ʧ��");
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
