package com.mt.app.padpayment.command;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;

import com.mt.android.db.DbHandle;
import com.mt.android.sys.mvc.command.AbstractCommand;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.ActivityID;
import com.mt.app.padpayment.requestbean.AdminReqBean;

public class AdminResetPassWordRuCommand extends AbstractCommand{
	

		
		private static DbHandle dbhandle = new DbHandle();
		private String defaultPassWord="000";//�������ó�Ĭ��ֵ
		@Override
		protected void go() {
			// TODO Auto-generated method stub
			AdminReqBean arb = (AdminReqBean) getRequest().getData();
			Response response = new Response();
			//�˴���д�ж� �߼� ���� ����ȷ����֤����ȥ���ݿ���ĵ��߼���Ȼ�󴫸�ȥ��activity�Ľ��ֵ
			List<Map<String,String>> list=dbhandle.rawQuery("select * from TBL_ADMIN where USER_ID =? and LIMITS=1", new String[] {arb.getUserId()});
			if(list!=null&&list.size()>0)
			{
				if(list.get(0).get("USER_ID").equals(arb.getUserId()))
				{
					dbhandle.update("TBL_ADMIN", new String[]{"PASSWORD"}, new String[]{defaultPassWord}, "USER_ID = ?", new String[]{arb.getUserId()});
					Controller.session.remove("AdminPassWordRu");
					Controller.session.put("AdminPassWordRu", "�������óɹ�");
				}
				else
				{
					Controller.session.remove("AdminPassWordRu");
					Controller.session.put("AdminPassWordRu", "��Ա�Ų�ƥ�䣬����ʧ��");
				}
			}
			else
			{
				Controller.session.remove("AdminPassWordRu");
				Controller.session.put("AdminPassWordRu", "�޴˹�Ա�ţ�����ʧ��");
			}
			response.setTargetActivityID(ActivityID.map.get("ACTIVITY_ID_AdminPassWordRuAllActivity"));
			
			Bundle bundle =new Bundle();
//			 bundle.putString("oldpassword", arb.getPassword());
//			 bundle.putString("newpassword",arb.getNewpassword());
			 response.setBundle(bundle);
			 int[] flags = new int[1];
			 flags[0] = Intent.FLAG_ACTIVITY_NO_HISTORY;
			 response.setFlags(flags);
			setResponse(response);
		
	}

}
