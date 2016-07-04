package com.mt.app.payment.activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.miteno.mpay.entity.MpayApp;
import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.global.Globals;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.activity.App_Manage1Activity.AMAdapter;
import com.mt.app.payment.common.RegCodeInput;
import com.mt.app.payment.requestbean.AppBindUnBindReqBean;
import com.mt.app.payment.requestbean.CheckRegCodeBean;
import com.mt.app.payment.requestbean.ObtainPayAppReqBean;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class App_Manage2Activity extends BaseActivity implements
		OnScrollListener {
	ArrayList<String> appname = new ArrayList<String>();
	ArrayList<String> appid = new ArrayList<String>();
	ArrayList<String> appphoto = new ArrayList<String>();
	private int visibleLastIndex = 0; // ���Ŀ���������
	private int visibleItemCount; // ��ǰ���ڿɼ�������
	private Handler handler = new Handler();
	private int totalNum; // ��
	private ListView listview2;
	private RegCodeInput regcode;
	private AMAdapter adapter;
	private View loadMoreView;
	private LinearLayout pbLinear;
	List<String> list = new ArrayList<String>();
	private String flag = "first"; // �Ƿ��ǵ�һ�μ�������
	private int page = 1; // ��ǰ���ڵ�ҳ����
	private boolean flg = false;
	private int curSize = 0; 

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		
		setContentView(R.layout.app_manageritem2);

		if (DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null) {
			DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
		}
		Globals.AppManage = this;
		
	}

	public void loadData() {
		DemoTabActivity.tvBindUnBind.setText("��");
		Request request = new Request();
		ObtainPayAppReqBean bean = new ObtainPayAppReqBean();
		bean.setPage(page);
		bean.setRows(10);
		bean.setAppType("3");
		request.setData(bean);
		go(CommandID.map.get("APPMANAGE1"), request, true);
	}
	
	private void initParams(){
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		list.clear();
		flg = false; 
		flag = "first";
		
		curSize = 0;
		page = 1;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(pbLinear != null){
			pbLinear.setVisibility(View.GONE);
			
		}
		list.clear();
		flg = false; 
		flag = "first";
		
		curSize = 0; 
		
		Globals.AppManage = this;
		DemoTabActivity.tvBindUnBind.setText("��");

		if (DemoTabActivity.tvText != null && DemoTabActivity.tvText[0] != null
				&& DemoTabActivity.tvText[1] != null) {
			DemoTabActivity.tvText[1].setTextColor(Color.WHITE);
			DemoTabActivity.tvText[0].setTextColor(Color.GRAY);
			DemoTabActivity.tvText[0].setShadowLayer(0, 0, 0, 0);
		}
		page = 1;
		loadData();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (adapter != null) {
			adapter.onAdapterDestroy();
		}
	}

	@Override
	public void TAB() {

		// ��֤��
		if (Controller.session.get("regcode") == null) {// ���û��������֤��,�򷵻�
			regcode = new RegCodeInput(App_Manage2Activity.this);
			regcode.showDialog();
			return;
		}
		// ��֤��

		if (list.size() != 0) {
			StringBuffer sb = new StringBuffer();
			Request request = new Request();
			AppBindUnBindReqBean app = new AppBindUnBindReqBean();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			app.setApp_id(sb.toString());
			app.setAction("bind");
			request.setData(app);
			go(CommandID.map.get("APPMANAGE3"), request, true);
			list.removeAll(list);
		} else {
			Toast.makeText(this, "��ѡ��Ӧ�ã�", Toast.LENGTH_SHORT).show();
		}
	}

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

	/* ��֤�� */
	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		try {

			/* ��֤�� */
			if (regcode != null && regcode.isRegResponse(response)) {
				return;
			}
			/* ��֤�� */
			if ((ResponseBean) response.getData() != null) {

				showToast(App_Manage2Activity.this,
						((ResponseBean) response.getData()).getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
		if(response != null 
				&& response.getBussinessType() != null
				&& response.getBussinessType().equals("bind_sort")){
			initParams();
		}
		
		/* ��֤�� */
		if (regcode != null && regcode.isRegResponse(response)) {
			return;
		}
		/* ��֤�� */
		if (flag.equals("first")) {
			appname.clear();
			appid.clear();
			appphoto.clear();
			ImageResultBean be = (ImageResultBean) response.getData();
			AppBind_DataBean point = (AppBind_DataBean) be.getRespBean();
			totalNum = (int)point.getTotal();
			for (int i = 0; i < point.getRows().size(); i++) {
				if (Integer.parseInt(point.getRows().get(i).getBind_flag()) == 0) {
					appname.add(point.getRows().get(i).getApp_name());
					appid.add(point.getRows().get(i).getId());
					appphoto.add(point.getRows().get(i).getPic_path());
				}
			}
			
			List<Map<String, Object>> listmap2 = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < appname.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", appname.get(i));
				map.put("image", appphoto.get(i));
				map.put("id", appid.get(i));
				listmap2.add(map);
			}
			
			listview2 = (ListView) findViewById(R.id.AM2listview);
			if(loadMoreView == null){
				loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
				pbLinear = (LinearLayout) loadMoreView.findViewById(R.id.pb_linear);
				listview2.addFooterView(loadMoreView); // �����б�ײ���ͼ
				loadMoreView.setVisibility(View.VISIBLE);
			}
			
			listview2.setOnScrollListener(this);
			adapter = new AMAdapter(this, listmap2);
			listview2.setAdapter(adapter);
			curSize = curSize + point.getRows().size(); 
		} else {
			ImageResultBean be = (ImageResultBean) response.getData();
			AppBind_DataBean point = (AppBind_DataBean) be.getRespBean();
			for (int i = 0; i < point.getRows().size(); i++) {
				if (Integer.parseInt(point.getRows().get(i).getBind_flag()) == 1) {
					appname.add(point.getRows().get(i).getApp_name());
					appid.add(point.getRows().get(i).getId());
					appphoto.add(point.getRows().get(i).getPic_path());
				}
			}
			for (int i = 0; i < appname.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("text", appname.get(i));
				map.put("image", appphoto.get(i));
				map.put("id", appid.get(i));
				adapter.addItem(map);
				adapter.notifyDataSetChanged(); // ���½���

				pbLinear.setVisibility(View.GONE);
			}
			curSize = curSize + point.getRows().size(); 
			flg = false;
		}
		try {
			ImageResultBean be = (ImageResultBean) response.getData();
			if (be.getMessage() != null) {

				showToast(App_Manage2Activity.this,
						be.getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void loadMoreData() {
		page++;
		flag = "no";
		// �������ȡ����
		loadData();
	}

	class AMAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> listItems;
		private LayoutInflater listContainer;
		Map<String, SoftReference<Bitmap>> caches;

		public final class ListItemView {
			public TextView text;
			public CheckBox checkbox;
			public ImageView image;
			String id;

		}

		public AMAdapter(Context context, List<Map<String, Object>> listItems) {
			// TODO Auto-generated constructor stub
			caches = new HashMap<String, SoftReference<Bitmap>>();
			this.context = context;
			listContainer = LayoutInflater.from(context);
			this.listItems = listItems;

		}

		private Handler handler = new Handler() { // �첽ˢ��ͼƬ
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					notifyDataSetChanged();
					break;
				}
			}
		};

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		public void addItem(Map<String, Object> newItem) {
			this.listItems.add(newItem);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ListItemView listItemView = new ListItemView();
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.app_amitem_layout,
					null);
			// ��ȡ�ؼ�����
			listItemView.text = (TextView) convertView
					.findViewById(R.id.AMtextView);
			listItemView.checkbox = (CheckBox) convertView
					.findViewById(R.id.AMcheckBox);
			listItemView.image = (ImageView) convertView
					.findViewById(R.id.AMimageView);
			listItemView.text.setText((String) listItems.get(position).get(
					"text"));
			listItemView.id = ((String) listItems.get(position).get("id"));
			
			String imageName = (String) listItems.get(position).get("image");

			new ImageTool().setImagePath(listItemView.image, imageName, caches, handler, R.drawable.xiao_t);
			
			listItemView.checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (listItemView.checkbox.isChecked() == true) {
						list.add(listItemView.id);
					}
					if (listItemView.checkbox.isChecked() == false) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i) == listItemView.id) {
								list.remove(i);
							}
						}
					}
				}
			});

			// ���ÿؼ�����converView
			convertView.setTag(listItemView);
			return convertView;
		}

		public void onAdapterDestroy() {
			new ImageTool().imageFree(caches);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		this.visibleItemCount = visibleItemCount;
		this.visibleLastIndex = firstVisibleItem + this.visibleItemCount - 1;
		// ������еļ�¼ѡ��������ݼ������������Ƴ��б�ײ���ͼ
		if (curSize == totalNum ) {
			try{
				flg = true;
				loadMoreView.setVisibility(View.GONE);
				}
				catch(Exception e) {
					
				}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		int itemsLastIndex = adapter.getCount() - 1;
		int lastIndex = itemsLastIndex + 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex  && !flg) {
			// ������Զ�����,��������������첽�������ݵĴ���
			pbLinear.setVisibility(View.VISIBLE);
			loadMoreView.setVisibility(View.VISIBLE);
			flg = true;
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreData(); // ��������
				}

			}, 2000);
		}
	}

}
