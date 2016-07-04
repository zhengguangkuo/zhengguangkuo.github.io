package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.android.R;
import com.mt.android.db.DbHandle;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.UserRegisterReqBean;
import com.mt.app.payment.requestbean.ValidTextReqBean;
import com.mt.app.payment.tools.EncoderMD5Tool;

public class UserRegisterActivity extends BaseActivity {
	private TextView textCheck;
	private Button regBut,codeBut ,reCancelBut;
	private ImageButton registerbackbtn;
	private Button regBack;
	EditText editText, rePass, /*Re_phone,*/ Re_Name ,Re_reve;
	private ArrayAdapter<SpinnerData> adapter;
	private Spinner spCityVal;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {

		setContentView(R.layout.user_register);
//		Re_phone = (EditText) findViewById(R.id.phone);
//		Re_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
		Re_reve  = (EditText) findViewById(R.id.editRegCode);
		Re_reve.setInputType(InputType.TYPE_CLASS_NUMBER);
		regBut = (Button) findViewById(R.id.button1);
		reCancelBut = (Button) findViewById(R.id.button1_cancel);
		regBack = (Button) findViewById(R.id.reg_back);
		codeBut = (Button) findViewById(R.id.get_code);
		rePass = ((EditText)findViewById(R.id.editPass));
		setListener();
	}
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if(Controller.session.get("IsLogin") != null && Controller.session.get("IsLogin").toString().equalsIgnoreCase("true")){
        finish();  
    	 }
    	}
	@Override
	public void onSuccess(Response response) {
		
	}

	@Override
	public void onError(Response response) {
		try {

			if ((ResponseBean) response.getData() != null) {
				// Toast.makeText(this, ((ResponseBean)
				// response.getData()).getMessage(), 9000).show();
				showToast(UserRegisterActivity.this,
						((ResponseBean) response.getData()).getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String verify(UserRegisterReqBean registerBean) {
		try {
			editText = (EditText) findViewById(R.id.editPass2);
			String pass = registerBean.getPassword();
			String sureP = editText.getText().toString();
			String name = registerBean.getUser_id();
			String phone = registerBean.getUser_id();
			String check = registerBean.getValid_code();
//			String emails = registerBean.getEmail();
//			if (emails == null ||pass == null || sureP == null || check == null || name == null
//					|| phone == null || ("").equals(emails) || ("").equals(pass) || ("").equals(sureP)
//					|| ("").equals(check) || ("").equals(name)
//					|| ("").equals(phone)) {
//				String m1 = "您的信息填写不完整，请先完善注册信息！";
//				return m1;
//			}
			if (pass.length() < 6 || pass.length() > 20) {
				String m3 = "密码长度在6~20位";
				return m3;
			}
			if (!pass.equals(sureP)) {
				String m2 = "两次密码输入不一致";
				editText.setText("");
				rePass.setText("");
				return m2;
			}
			if(phone.length() != 11){
				String m4 = "请输入11位真实有效的手机号码";
				return m4;
			}
//			if(!(Pattern.compile(
//					"^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"
//					).matcher(emails).matches())){
//				String m5 = "请输入正确的邮箱";
//				return m5;
//			}
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void setListener() {
		regBut.setOnClickListener(new OnClickListener() { // 注册按钮

			@Override
			public void onClick(View v) {
				UserRegisterReqBean reqBean = new UserRegisterReqBean();
				/*new BaseFormImpl().form2Bean(reqBean,
						findViewById(R.id.linearLayout2)); // 将数据装入bean
*/				
				String city = ((SpinnerData)spCityVal.getSelectedItem()).getValue();
				String j_username = ((EditText)findViewById(R.id.editUserName)).getText().toString();
				String j_password = ((EditText)findViewById(R.id.editPass)).getText().toString();
//				String email = ((EditText)findViewById(R.id.editEmail)).getText().toString();
				String mobile = j_username;	// 因用户名和手机号一致而做出的修改 //((EditText)findViewById(R.id.phone)).getText().toString();
				String valid_code = ((EditText)findViewById(R.id.editRegCode)).getText().toString();
				reqBean.setCity(city);
				reqBean.setUser_id(j_username);
				reqBean.setPassword(j_password);
//				reqBean.setEmail(email);
				reqBean.setMobile(mobile);
				reqBean.setValid_code(valid_code);
				String msg = verify(reqBean);
				if (msg != null && msg.equals("")) { // 验证通过
					// 对密码进行加密
					reqBean.setPassword(EncoderMD5Tool.MD5(j_password+"{"+j_username+"}")) ;
					Request request = new Request(reqBean);
					go(true, CommandID.map.get("UserDoRegister"), request, true); // 发送请求
				} else {
					showToast(UserRegisterActivity.this, msg); // 显示失败原因

				}
			}
		});
		reCancelBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {  //取消按钮
				finish();
			}
		});
		regBack.setOnClickListener(new OnClickListener() { //返回

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		codeBut.setOnClickListener(new OnClickListener() { //获取验证码
			
			@Override
			public void onClick(View v) {
				
				
				EditText et = (EditText) findViewById(R.id.editUserName);		// 获取手机号码
				String str = et.getText().toString();
				if (str != null && !str.equals("")) {
					Toast.makeText(UserRegisterActivity.this, "请求已发送...", Toast.LENGTH_LONG).show();
					ValidTextReqBean reqBean = new ValidTextReqBean();
					Request request = new Request(reqBean);
					reqBean.setMobile(str);
					go(CommandID.map.get("UserVerification"), request, false); // 发送请求
				} else {
					showToast(UserRegisterActivity.this, "请输入电话号码"); // 显示失败原因
				}
			}
		});
		
		DbHandle dbhandle = new DbHandle();
		List<Map<String, String>> list = dbhandle.rawQuery(
					"select * from AREA_CODE where AREA_LEVEL =2",null);
	    List<SpinnerData> lst = new ArrayList<SpinnerData>();
		for (int i = 0; i < list.size(); i++) {
				SpinnerData c = new SpinnerData(list.get(i).get("AREA_CODE"), list.get(i).get("AREA_NAME"));
				lst.add(c);
		}
	        //将可选内容与ArrayAdapter连接起来  
		adapter = new ArrayAdapter<SpinnerData>(this,android.R.layout.simple_spinner_item,lst);  
	          
	        //设置下拉列表的风格  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	          
	        //将adapter 添加到spinner中  
		spCityVal = (Spinner) findViewById(R.id.spCityVal);
	    spCityVal.setAdapter(adapter);  
	          
	        //添加事件Spinner事件监听    
	    spCityVal.setOnItemSelectedListener(new SpinnerSelectedListener());  
	          
	        //设置默认值  
	    spCityVal.setVisibility(View.VISIBLE);
		spCityVal=(Spinner)findViewById(R.id.spCityVal);
		spCityVal.setAdapter(adapter);
	}
	
	class SpinnerData {

		private String value = "";
		private String text = "";

		public SpinnerData() {
			value = "";
			text = "";
		}

		public SpinnerData(String _value, String _text) {
			value = _value;
			text = _text;
		}

		@Override
		public String toString() { 

			return text;
		}

		public String getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}
	
	//使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {
        	
        	Log.i("选择的城市为：",((SpinnerData)spCityVal.getSelectedItem()).getText());
            Log.i("选择的城市值为：",((SpinnerData)spCityVal.getSelectedItem()).getValue());
			Controller.session.put("AREA_CODE_LEVEL_1", ((SpinnerData)spCityVal.getSelectedItem()).getValue());
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }

}
