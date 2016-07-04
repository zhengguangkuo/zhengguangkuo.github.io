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
 * @Description:������Ϣչʾ����
 * 
 * @author:dw
 * 
 * @time:2013-9-13 ����3:43:40
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
		cardtype = (TextView) findViewById(R.id.guidecardtypeshow); // ����
		bindbtn.setText("ȷ��");
		title.setText("��������");
		Cardnotext = (TextView) findViewById(R.id.guidecard_num); // ����
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.inputcard_layout,
				(ViewGroup) findViewById(R.id.dialog));
		final EditText editcardno = (EditText) layout
				.findViewById(R.id.editcardno);
		tvInputDetails = (TextView) layout.findViewById(R.id.bindInputDetails);
		back.setVisibility(View.GONE);
		image.setVisibility(View.GONE);

		// ������ʾ
		Bundle bundle = getIntent().getBundleExtra("bundleInfo");
		if (bundle != null) {
			list = (ArrayList<Card_DataBean>) bundle.getSerializable("list");
			if (list == null) {
				showToast(this, "�����������ӣ�");
			} else {
				if (list.size() > 0) {
					if (list.get(0).getRespcode().equals("-1")) {
						showToast(this, list.get(0).getMessage());
					}
				}
				for (int i = 0; i < list.size(); i++) { // ���û������ͺͿ���
					if (list.get(i).getBind_flag().equals("1")) {
						cardtype.setText(list.get(i).getCard_name());// ��������
						Cardnotext.setText(list.get(i).getCard_no());// ���ÿ���
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
								"δ��ɻ��������ã����������ܵ����ӿ������Ż��ۿ۵ķ���", Toast.LENGTH_LONG).show();
						finish();
					}
				});
		// ȷ����ť
		bindbtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// ���
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

				if (cardtype.getText().toString().equals("")) { // У������
					showToast(GuideBaseCardSetActivity.this, "��ѡ���������");
					return;
				}
				if (Cardnotext.getText().toString().equals("")) { // У�鿨��
					showToast(GuideBaseCardSetActivity.this, "�����뿨��");
					return;
				}
				// ������
				if (Controller.session.get("isBind") == null) {
					Request request = new Request();
					Card_DataBean card = new Card_DataBean();
					card.setCard_no(Cardnotext.getText().toString());
					for (Card_DataBean bean : list) { // ������
						if (bean.getCard_name().equals(
								cardtype.getText().toString())) {
							card.setCard_type(bean.getCard_type());
						}
					}

					card.setAction("bind");
					request.setData(card);
					go(CommandID.map.get("BINDCARD"), request, true);
				} else { // �����޸�
					Request request = new Request();
					Card_DataBean card = new Card_DataBean();

					for (Card_DataBean bean : list) { // ������
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
				// ��֤��
				if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				// ��֤��
				Request request = new Request();
				request.setData(list);
				go(CommandID.map.get("GuideBaseCardSelect"), request, true);
			}
		});
		// ����
		cardtype.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��֤��
				if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				// ��֤��
				Request request = new Request();
				request.setData(list);
				go(CommandID.map.get("GuideBaseCardSelect"), request, true);
			}
		});
		// ����
		final AlertDialog dialog = new AlertDialog.Builder(
				GuideBaseCardSetActivity.this).setTitle("���뿨��").setView(layout)
				.setPositiveButton("ȷ��", new OnClickListener() {

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
				}).setNegativeButton("ȡ��", null).create();
		Cardnotext.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��֤��
				if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				if (cardtype.getText().toString().equals("")) { // û��ѡ��Ƭ����
					Toast.makeText(GuideBaseCardSetActivity.this, "����ѡ���������",
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
				// ��֤��
				if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
					regcode = new RegCodeInput(GuideBaseCardSetActivity.this);
					regcode.showDialog();
					return;
				}
				if (cardtype.getText().toString().equals("")) { // û��ѡ��Ƭ����
					Toast.makeText(GuideBaseCardSetActivity.this, "����ѡ��Ƭ����",
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
	 * //��֤�� if(Controller.session.get("regcode") == null){//���û��������֤��,�򷵻�
	 * regcode = new RegCodeInput(BaseCardSetActivity.this);
	 * regcode.showDialog(); return ; }
	 */

	/* ��֤�� */
	public void checkRegInfo(String vregCode) {// �˶���֤��
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
	 * ��֤��
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
		/* ��֤�� */
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		if (response != null && response.getData() != null) {
			showToast(this, ((ResponseBean) response.getData()).getMessage());
		}
	}

	@Override
	public void onSuccess(Response response) {
		// ��֤��
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		if (response != null && response.getData() != null) {
			showToast(this, ((ResponseBean) response.getData()).getMessage());
		}
		finish();
		/*
		 * 
		 * // ��֤�� if (regcode != null && regcode.isRegResponse(response)) {
		 * return; } // ��֤�� // list = (List<Card_DataBean>) response.getData();
		 * // �����Ѱ󶨵Ŀ� for (ResponseBean bean : list) { Card_DataBean cbean =
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
		 * Toast.makeText(BaseCardSetActivity.this, "��ѡ��������ͣ�",
		 * Toast.LENGTH_LONG).show(); k = 0; return; } if
		 * (Controller.session.get("type") != null && Cardnotext.getText() !=
		 * null && !Cardnotext.getText().toString().trim() .equals("")) {
		 * Request request1 = new Request(); Card_DataBean card1 = new
		 * Card_DataBean(); card1.setCard_no(Cardnotext.getText().toString());
		 * card1.setCard_type(Controller.session.get("type") .toString());
		 * card1.setAction("bind"); request1.setData(card1);
		 * go(CommandID.map.get("BINDCARD"), request1, true); return; } if
		 * (Controller.session.get("type") != null && Cardnotext.getText() ==
		 * null) { Toast.makeText(BaseCardSetActivity.this, "�����뿨��", 3000)
		 * .show(); return; } } else { if (Controller.session.get("cardtype") ==
		 * null) { Request request = new Request(); Card_DataBean card = new
		 * Card_DataBean(); card.setAction("unBind"); request.setData(card);
		 * go(CommandID.map.get("BINDCARD"), request, true);
		 * Controller.session.put("unbindSuss", "0"); return; } else { Request
		 * request = new Request(); Card_DataBean card = new Card_DataBean(); if
		 * (Cardnotext.getText() != null &&
		 * !Cardnotext.getText().toString().trim() .equals("")) {
		 * card.setCard_no(Cardnotext.getText().toString()); } else {
		 * Toast.makeText(BaseCardSetActivity.this, "�����뿨��", 3000).show();
		 * return; } if (Controller.session.get("type") != null) {
		 * card.setCard_type(Controller.session.get("type") .toString()); } else
		 * { card.setCard_type(localCardType); }
		 * 
		 * card.setAction("update"); request.setData(card);
		 * go(CommandID.map.get("UPDATECARD"), request, true); } } } });
		 */}
}
