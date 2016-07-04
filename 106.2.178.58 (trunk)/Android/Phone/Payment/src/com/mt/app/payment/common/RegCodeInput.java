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
		final AlertDialog dialog=new AlertDialog.Builder(context).setTitle("请输入验证码").setView(layout).setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String regCode = ((EditText)layout.findViewById(R.id.editRegCode)).getText().toString();
						
						if(regCode == null || regCode.trim().equalsIgnoreCase("")){
							Toast.makeText(context, "验证码不能为空", Toast.LENGTH_SHORT).show();
							return;
						}
						
						((BaseActivity)context).checkRegInfo(regCode);
					}
				}).setNegativeButton("取消", null).create();
		dialog.show();
		Button regBtn = (Button)layout.findViewById(R.id.getRegCode);//请求验证码按钮
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
			//获取验证码信息的返回
			UserRegRespBean regRespBean = (UserRegRespBean)response.getData();
			
			if(regRespBean.getBusiInfo().equalsIgnoreCase("0")){//请求验证码
				if(regRespBean.getRespcode().equalsIgnoreCase("0")){
					Toast.makeText(context, "验证码请求成功", Toast.LENGTH_SHORT).show();
				}else{
					if(regRespBean.getMessage() == null || regRespBean.getMessage().trim().equalsIgnoreCase("")){
						Toast.makeText(context, "验证码获取失败", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, regRespBean.getMessage(), Toast.LENGTH_SHORT).show();
					}			
				}
			}
			
			if(regRespBean.getBusiInfo().equalsIgnoreCase("1")){//验证码验证
				if(regRespBean.getRespcode().equalsIgnoreCase("0")){
					Toast.makeText(context, "验证码验证成功", Toast.LENGTH_SHORT).show();
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
