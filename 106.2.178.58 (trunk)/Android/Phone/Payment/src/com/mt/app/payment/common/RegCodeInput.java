package com.mt.app.payment.common;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.app.payment.responsebean.UserRegRespBean;
public class RegCodeInput {
	private Context context;
	public  RegCodeInput(Context context){
		this.context = context;
	}
	
	public void showDialog(){
		LayoutInflater inflater = ((BaseActivity)context).getLayoutInflater();
		final View layout = inflater.inflate(R.layout.input_regcode_layout,
			     ((ViewGroup) ((BaseActivity)context).findViewById(R.id.dialog)));
		final AlertDialog dialog=new AlertDialog.Builder(context).setTitle("��������֤��").setView(layout).setPositiveButton("ȷ��", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String regCode = ((EditText)layout.findViewById(R.id.editRegCode)).getText().toString();
						
						if(regCode == null || regCode.trim().equalsIgnoreCase("")){
							Toast.makeText(context, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
							return;
						}
						
						((BaseActivity)context).checkRegInfo(regCode);
					}
				}).setNegativeButton("ȡ��", null).create();
		dialog.show();
		Button regBtn = (Button)layout.findViewById(R.id.getRegCode);//������֤�밴ť
		regBtn.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((BaseActivity)context).getRegInfo();
			}
		});
	}
	
	public boolean isRegResponse(Response response){
		if(response.getData() != null && response.getData() instanceof UserRegRespBean){
			//��ȡ��֤����Ϣ�ķ���
			UserRegRespBean regRespBean = (UserRegRespBean)response.getData();
			
			if(regRespBean.getBusiInfo().equalsIgnoreCase("0")){//������֤��
				if(regRespBean.getRespcode().equalsIgnoreCase("0")){
					Toast.makeText(context, "��֤������ɹ�", Toast.LENGTH_SHORT).show();
				}else{
					if(regRespBean.getMessage() == null || regRespBean.getMessage().trim().equalsIgnoreCase("")){
						Toast.makeText(context, "��֤���ȡʧ��", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, regRespBean.getMessage(), Toast.LENGTH_SHORT).show();
					}			
				}
			}
			
			if(regRespBean.getBusiInfo().equalsIgnoreCase("1")){//��֤����֤
				if(regRespBean.getRespcode().equalsIgnoreCase("0")){
					Toast.makeText(context, "��֤����֤�ɹ�", Toast.LENGTH_SHORT).show();
					Controller.session.put("regcode", "isregcode");
				}else{
					Toast.makeText(context, regRespBean.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			
			return true;
		}
		
		return false;
	}
}
