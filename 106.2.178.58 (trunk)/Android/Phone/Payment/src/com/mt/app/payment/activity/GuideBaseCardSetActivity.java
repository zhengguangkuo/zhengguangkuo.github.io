package com.mt.app.payment.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.responsebean.Card_DataBean;

/**
 * 
 * @Description:基卡信息展示界面
 * 
 * @author:dw
 * 
 * @time:2013-9-13 下午3:43:40
 */
public class GuideBaseCardSetActivity extends BaseActivity {
	private TextView cardtype;
	private TextView Cardnotext;
	private TableRow InputCardno, Selecttype;
	private String cardno;
	private TextView title;
	private ImageView image, imaF;
	private Button bindbtn, cancelbindbtn, back;
	private RegCodeInput regcode;
	ArrayList<Card_DataBean> list;
	private TextView tvInputDetails;

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.guidebasecard_set_layout);
		title = (TextView) findViewById(R.id.titlewelcome);
		back = (Button) findViewById(R.id.onoff);
		image = (ImageView) findViewById(R.id.onoffB);
		imaF = (ImageView) findViewById(R.id.basecard_frame_imageview);
		imaF.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imaF.setVisibility(View.GONE);
			}
		});
		InputCardno = (TableRow) findViewById(R.id.guideInputCardno);
		cancelbindbtn = (Button) findViewById(R.id.cancelbindbtn);
		Selecttype = (TableRow) findViewById(R.id.guideSelecttype);
		bindbtn = (Button) findViewById(R.id.guidebindbtn);
		cardtype = (TextView) findViewById(R.id.guidecardtypeshow); // 类型
		bindbtn.setText("确定");
		title.setText("基卡设置");
		Cardnotext = (TextView) findViewById(R.id.guidecard_num); // 卡号
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.inputcard_layout,
				(ViewGroup) findViewById(R.id.dialog));
		final EditText editcardno = (EditText) layout
				.findViewById(R.id.editcardno);
		tvInputDetails = (TextView) layout.findViewById(R.id.bindInputDetails);
		back.setVisibility(View.GONE);
		image.setVisibility(View.GONE);

		// 数据显示
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			list = (ArrayList<Card_DataBean>) bundle.getSerializable("list");
			if (list == null) {
				showToast(this, "请检查网络连接！");
			} else {
				if (list.size() > 0) {
					if (list.get(0).getRespcode().equals("-1")) {
						showToast(this, list.get(0).getMessage());
					}
				}
				for (int i = 0; i < list.size(); i++) { // 设置基卡类型和卡号
					if (list.get(i).getBind_flag().equals("1")) {
						cardtype.setText(list.get(i).getCard_name());// 基卡类型
						Cardnotext.setText(list.get(i).getCard_no());// 设置卡号
						break;
					}
				}
			}
		}
		cancelbindbtn
				.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(GuideBaseCardSetActivity.this,
								"未完成基卡的设置，将不能享受到电子卡包及优惠折扣的服务", Toast.LENGTH_LONG).show();
						finish();
					}
				});
		// 确定按钮
		bindbtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// 解绑
				if (!cardtype.getText().toString().equals("")
						&& Cardnotext.getText().toString().equals("")) {
					Request request = new Request();
					Card_DataBean card = new Card_DataBean();
					card.setCard_no(" ");
					card.setCard_type(" ");
					card.setAction("unBind");
					request.setData(card);
					go(CommandID.map.get("BINDCARD"), request, true);
				}

				if (cardtype.getText().toString().equals("")) { // 校验类型
					showToast(GuideBaseCardSetActivity.this, "请选择基卡类型");
					return;
				}
				if (Cardnotext.getText().toString().equals("")) { // 校验卡号
					showToast(GuideBaseCardSetActivity.this, "请输入卡号");
					return;
				}
				// 基卡绑定
				if (Controller.session.get("isBind") == null) {
					Request request = new Request();
					Card_DataBean card = new Card_DataBean();
					card.setCard_no(Cardnotext.getText().toString());
					for (Card_DataBean bean : list) { // 卡类型
						if (bean.getCard_name().equals(
								cardtype.getText().toString())) {
							card.setCard_type(bean.getCard_type());
						}
					}

					card.setAction("bind");
					request.setData(card);
					go(CommandID.map.get("BINDCARD"), request, true);
				} else { // 基卡修改
					Request request = new Request();
					Card_DataBean card = new Card_DataBean();

					for (Card_DataBean bean : list) { // 卡类型
						if (bean.getCard_name().equals(
								cardtype.getText().toString())) {
							card.setCard_type(bean.getCard_type());
						}
					}
					card.setCard_no(Cardnotext.getText().toString());
					card.setAction("update");
					request.setData(card);
					go(CommandID.map.get("UPDATECARD"), request, true);
				}
			}
		});

		Selecttype.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 验证码
				if (Controller.session.get("regcode") == null) {// 如果没有输入验证码,则返回
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				// 验证码
				Request request = new Request();
				request.setData(list);
				go(CommandID.map.get("GuideBaseCardSelect"), request, true);
			}
		});
		// 类型
		cardtype.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 验证码
				if (Controller.session.get("regcode") == null) {// 如果没有输入验证码,则返回
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				// 验证码
				Request request = new Request();
				request.setData(list);
				go(CommandID.map.get("GuideBaseCardSelect"), request, true);
			}
		});
		// 卡号
		final AlertDialog dialog = new AlertDialog.Builder(
				GuideBaseCardSetActivity.this).setTitle("输入卡号").setView(layout)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cardno = editcardno.getText().toString();
						Cardnotext.setText(cardno);
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getCard_name()
									.equals(cardtype.getText())) {
								list.get(i).setCard_no(cardno);
								break;
							}
						}

					}
				}).setNegativeButton("取消", null).create();
		Cardnotext.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 验证码
				if (Controller.session.get("regcode") == null) {// 如果没有输入验证码,则返回
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				if (cardtype.getText().toString().equals("")) { // 没有选择卡片类型
					Toast.makeText(GuideBaseCardSetActivity.this, "请先选择基卡类型",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (Cardnotext != null && Cardnotext.getText() != null) {
					String i = Cardnotext.getText().toString();
					editcardno.setText(i);
				}
				if(Controller.session.get("bindInputDetails") != null){
					tvInputDetails.setVisibility(View.VISIBLE);
					tvInputDetails.setText(String.valueOf(Controller.session.get("bindInputDetails")));
				}
				dialog.show();
			}
		});

		InputCardno.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 验证码
				if (Controller.session.get("regcode") == null) {// 如果没有输入验证码,则返回
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				if (cardtype.getText().toString().equals("")) { // 没有选择卡片类型
					Toast.makeText(GuideBaseCardSetActivity.this, "请先选择卡片类型",
							Toast.LENGTH_LONG).show();
					return;
				}
				if (Cardnotext != null && Cardnotext.getText() != null) {
					String i = Cardnotext.getText().toString();
					editcardno.setText(i);
				}
				if(Controller.session.get("bindInputDetails") != null){
					tvInputDetails.setVisibility(View.VISIBLE);
					tvInputDetails.setText(String.valueOf(Controller.session.get("bindInputDetails")));
				}
				dialog.show();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Controller.session.get("guideClose") != null 
				&& Controller.session.get("guideClose").equals("ok")){
			imaF.setVisibility(View.GONE);
			Controller.session.remove("guideClose");
		}
	}

	/*
	 * //验证码 if(Controller.session.get("regcode") == null){//如果没有输入验证码,则返回
	 * regcode = new RegCodeInput(BaseCardSetActivity.this);
	 * regcode.showDialog(); return ; }
	 */

	/* 验证码 */
	public void checkRegInfo(String vregCode) {// 核对验证码
		Request request = new Request();
		CheckRegCodeBean regcode = new CheckRegCodeBean();
		regcode.setValidateCode(vregCode);
		request.setData(regcode);

		go(CommandID.map.get("RegCodeVerfi"), request, true);
	}

	public void getRegInfo() {
		Request request = new Request();
		go(CommandID.map.get("RegCodeGet"), request, true);
	}

	/*
	 * 验证码
	 * 
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); try { if (j == 1) { if
	 * (Controller.session.get("cardtypeflag").toString() == "2") {
	 * cardtype.setText(Controller.session.get("cardtype") .toString()); } } j =
	 * 1; if(Controller.session.get("cardtype") == null){ cardtype.setText("");
	 * Cardnotext.setText(""); }
	 * 
	 * } catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
	 * }
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return true;
	}

	@Override
	public void onError(Response response) {
		/* 验证码 */
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		if (response != null && response.getData() != null) {
			showToast(this, ((ResponseBean) response.getData()).getMessage());
		}
	}

	@Override
	public void onSuccess(Response response) {
		// 验证码
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		if (response != null && response.getData() != null) {
			showToast(this, ((ResponseBean) response.getData()).getMessage());
		}
		finish();
		/*
		 * 
		 * // 验证码 if (regcode != null && regcode.isRegResponse(response)) {
		 * return; } // 验证码 // list = (List<Card_DataBean>) response.getData();
		 * // 查找已绑定的卡 for (ResponseBean bean : list) { Card_DataBean cbean =
		 * (Card_DataBean) bean;
		 * 
		 * if (cbean.getBind_flag() != null &&
		 * cbean.getBind_flag().trim().equalsIgnoreCase("0")) { k++; }
		 * 
		 * if (cbean.getBind_flag() != null &&
		 * cbean.getBind_flag().trim().equalsIgnoreCase("1")) { localCardType =
		 * cbean.getCard_type(); flag = true; Controller.session.put("cardtype",
		 * cbean.getCard_name()); if
		 * (!Controller.session.get("cardtype").equals("123")) {
		 * cardtype.setText(Controller.session.get("cardtype") .toString()); }
		 * else { cardtype.setText(cbean.getCard_name()); }
		 * Cardnotext.setText(cbean.getCard_no()); } }
		 * bindbtn.setOnClickListener(new android.view.View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (flag == false) { if (Controller.session.get("type") == null
		 * && k != 2) { Request request = new Request(); Card_DataBean card =
		 * new Card_DataBean(); card.setAction("unBind"); request.setData(card);
		 * go(CommandID.map.get("BINDCARD"), request, true); return; } if
		 * (Controller.session.get("type") == null && k == 2) {
		 * Toast.makeText(BaseCardSetActivity.this, "请选择基卡类型！",
		 * Toast.LENGTH_LONG).show(); k = 0; return; } if
		 * (Controller.session.get("type") != null && Cardnotext.getText() !=
		 * null && !Cardnotext.getText().toString().trim() .equals("")) {
		 * Request request1 = new Request(); Card_DataBean card1 = new
		 * Card_DataBean(); card1.setCard_no(Cardnotext.getText().toString());
		 * card1.setCard_type(Controller.session.get("type") .toString());
		 * card1.setAction("bind"); request1.setData(card1);
		 * go(CommandID.map.get("BINDCARD"), request1, true); return; } if
		 * (Controller.session.get("type") != null && Cardnotext.getText() ==
		 * null) { Toast.makeText(BaseCardSetActivity.this, "请输入卡号", 3000)
		 * .show(); return; } } else { if (Controller.session.get("cardtype") ==
		 * null) { Request request = new Request(); Card_DataBean card = new
		 * Card_DataBean(); card.setAction("unBind"); request.setData(card);
		 * go(CommandID.map.get("BINDCARD"), request, true);
		 * Controller.session.put("unbindSuss", "0"); return; } else { Request
		 * request = new Request(); Card_DataBean card = new Card_DataBean(); if
		 * (Cardnotext.getText() != null &&
		 * !Cardnotext.getText().toString().trim() .equals("")) {
		 * card.setCard_no(Cardnotext.getText().toString()); } else {
		 * Toast.makeText(BaseCardSetActivity.this, "请输入卡号", 3000).show();
		 * return; } if (Controller.session.get("type") != null) {
		 * card.setCard_type(Controller.session.get("type") .toString()); } else
		 * { card.setCard_type(localCardType); }
		 * 
		 * card.setAction("update"); request.setData(card);
		 * go(CommandID.map.get("UPDATECARD"), request, true); } } } });
		 */}
}
