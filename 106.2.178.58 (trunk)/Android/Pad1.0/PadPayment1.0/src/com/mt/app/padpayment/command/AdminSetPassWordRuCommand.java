package com.mt.app.padpayment.command;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.AdminReqBean;

public class AdminSetPassWordRuCommand extends AbstractCommand{
	
	private String userID=Controller.session.get("userID").toString();
	private static DbHandle dbhandle = new DbHandle();
	@Override
	protected void go() {
		// TODO Auto-generated method stub
		AdminReqBean arb = (AdminReqBean) getRequest().getData();
		Response response = new Response();
		//�˴���д�ж� �߼� ���� ����ȷ����֤����ȥ���ݿ���ĵ��߼���Ȼ�󴫸�ȥ��activity�Ľ��ֵ
		
		if(arb.getNewpassword().toString().equals(arb.getEqnewPassword().toString()))
		{
			 List<Map<String,String>> list=dbhandle.rawQuery("select * from TBL_ADMIN where USER_ID =? ", new String[] {userID});
			if(list.size()>0)
			{
				if(list.get(0).get("USER_ID").equals(userID))
				{
					if(list.get(0).get("PASSWORD").equals(arb.getPassword()))
					{
						dbhandle.update("TBL_ADMIN", new String[]{"PASSWORD"}, new String[]{arb.getNewpassword()}, "USER_ID =?", new String[]{userID});
						Controller.session.remove("AdminPassWordRu");
						Controller.session.put("AdminPassWordRu", "�������޸ĳɹ�");
					}
					else
					{
						Controller.session.remove("AdminPassWordRu");
						Controller.session.put("AdminPassWordRu", "ԭ�������벻��ȷ");
					}
					
				}
				else
				{
					Controller.session.remove("AdminPassWordRu");
					Controller.session.put("AdminPassWordRu", "��Ա�Ų�ƥ�䣬�޸�ʧ��");
				}
					
			}
			else
			{
				Controller.session.remove("AdminPassWordRu");
				Controller.session.put("AdminPassWordRu", "��Ա�Ų�ƥ�䣬�޸�ʧ��");
			}
		}else
		{
			Controller.session.remove("AdminPassWordRu");
			Controller.session.put("AdminPassWordRu", "��������ȷ�����벻ͬ");
		}
		
		
		
		response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_AdminPassWordRuAllActivity"));
		
		Bundle bundle =new Bundle();
//		 bundle.putString("oldpassword", arb.getPassword());
//		 bundle.putString("newpassword",arb.getNewpassword());
		 response.setBundle(bundle);
		 int[] flags = new int[1];
		 flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
		 response.setFlags(flags);
		setResponse(response);
		
		
		
	}

}
