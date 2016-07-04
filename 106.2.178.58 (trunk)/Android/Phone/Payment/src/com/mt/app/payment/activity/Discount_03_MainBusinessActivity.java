package com.mt.app.payment.activity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.mpay.entity.UserCareMerch;
import com.mt.android.R;
import com.mt.android.protocol.http.client.ImageHttpClient;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.requestbean.MerchListReqBean;
import com.mt.app.payment.requestbean.SeeMerchReqBean;
import com.mt.app.payment.responsebean.ImageResultBean;
import com.mt.app.payment.responsebean.SelectMerchListResult;
import com.mt.app.payment.tools.ImageBufferFree;
import com.mt.app.payment.tools.ImageTool;

public class Discount_03_MainBusinessActivity extends BaseActivity {
    private LinearLayout buslaytou;
    private boolean flag =false;
	private GridView gridView;
	// 图片的文字标题
	ArrayList<String> titles = new ArrayList<String>();
	// 图片ID数组
	private ArrayList<String> images = new ArrayList<String>();
	//商户id
	ArrayList<String> ids = new ArrayList<String>();
	private Button btnBack,cancelbusbtn;
	
	private ArrayList<ImageView> imageList = new ArrayList<ImageView>();
	private StringBuilder sbb = new StringBuilder();
	
	private PictureAdapter adapter;
	
	Map<String, SoftReference<Bitmap>> caches;
	
	private int[] picState;  //存放图片勾选状态的数组
	private Map<String , String> sbbmap = new HashMap<String , String>();

	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
		setContentView(R.layout.discount_03_mainbusiness);
		buslaytou = (LinearLayout)findViewById(R.id.buslaytou);
		btnBack = (Button) findViewById(R.id.btn_carefull_merch_back);
		cancelbusbtn=(Button)findViewById(R.id.cancelbusbtn);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Discount_03_MainBusinessActivity.this.finish();
			}
		});
		cancelbusbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sbbmap.size() == 0){
					Toast.makeText(Discount_03_MainBusinessActivity.this, "您还没有选择要取消关注的商家！", Toast.LENGTH_LONG).show();
				}else{
					Request request = new Request();
					SeeMerchReqBean re = new SeeMerchReqBean();
					re.setHandleType("0");  //取消关注
					for(String idss : sbbmap.values()){
						sbb.append(idss);
						sbb.append(",");
					}
					sbb.substring(0 , sbb.length()-1);
					re.setMerchIds(sbb.toString());  
					request.setData(re);
					go(CommandID.map.get("SeeMerchant"), request, true);
				}
				for(ImageView img : imageList){
					img.setVisibility(View.INVISIBLE);
				}
				buslaytou.setVisibility(View.INVISIBLE);
				flag=false;
			}
		});
		setDatas();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_MENU){
			if(flag==false){
				buslaytou.setVisibility(View.VISIBLE);
				if(imageList != null && imageList.size() != 0){
					for(ImageView img : imageList){
						img.setVisibility(View.VISIBLE);
					}
				}
		       flag=true;
		}else{
			buslaytou.setVisibility(View.INVISIBLE);
			if(imageList != null && imageList.size() != 0){
				for(ImageView img : imageList){
					img.setVisibility(View.INVISIBLE);
				}
			}
			   flag=false;
		}
			return true;

		}
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(flag==true){
				buslaytou.setVisibility(View.INVISIBLE);
				flag=false;
			}else{
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void setDatas(){
		Request request = new Request();
		MerchListReqBean re = new MerchListReqBean();
		re.setPage(1);
		re.setRows(100);
		request.setData(re);
		go(CommandID.map.get("SelectMerchList"), request, true);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		if(imageList != null && imageList.size() != 0){
//			for(ImageView img : imageList){
//				img.setVisibility(View.VISIBLE);
//			}
//		}
//		menu.add(0, 100, 0, "取消关注");
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	// 菜单项被选择事件
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case 100:
//			
//			break;
//		}
//
//		return false;
//	}

	class PictureAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<Picture> pictures;
		public PictureAdapter(ArrayList<String> titles, ArrayList<String> images, Context context) {
			super();
			notifyDataSetChanged();
			caches = new HashMap<String, SoftReference<Bitmap>>();
			pictures = new ArrayList<Picture>();
			inflater = LayoutInflater.from(context);
			for (int i = 0; i < images.size(); i++) {
				Picture picture = new Picture(titles.get(i), images.get(i));
				pictures.add(picture);
			}
		}
		@Override
		public int getCount() {
			if (null != pictures) {
				return pictures.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return pictures.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.picture_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.image_merch_pic);
				viewHolder.imageDelete = (ImageView)convertView.findViewById(R.id.image_select_delete);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			Picture pp = pictures.get(position);
			viewHolder.title.setText(pp.getTitle());
				
			String imageName = pp.getImageId();
			
			new ImageTool().setImagePath(viewHolder.image, imageName, caches, handler, R.drawable.xiao_t);
			
			imageList.add(viewHolder.imageDelete);
			viewHolder.imageDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(picState[position] == 0){
						viewHolder.imageDelete.setImageResource(R.drawable.xuanze2);
						sbbmap.put(String.valueOf(position), ids.get(position));
						picState[position] = 1;
					}else if(picState[position] == 1){
						viewHolder.imageDelete.setImageResource(R.drawable.xuanze1);
						picState[position] = 0;
						sbbmap.remove(String.valueOf(position));
					}
					
				}
			});
			return convertView;
		}
		
		private Handler handler = new Handler() { // 异步刷新图片
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					notifyDataSetChanged();
					break;
				}
			}
		};
		
		public void onAdapterDestroy(){
			new ImageTool().imageFree(caches);
		}
	}

	class ViewHolder {
		public TextView title;
		public ImageView image;
		public ImageView imageDelete ; 
	}

	class Picture {
		private String title;
		private String imageId;

		public Picture() {
			super();
		}

		public Picture(String title, String imageId) {
			super();
			this.title = title;
			this.imageId = imageId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getImageId() {
			return imageId;
		}

		public void setImageId(String imageId) {
			this.imageId = imageId;
		}
		
	}

	@Override
	public void onSuccess(Response response) {
		if(response.getBussinessType() != null && response.getBussinessType().equals("SeeMerchant")){
			try {

				if ((ResponseBean) response.getData() != null) {
					
					Toast.makeText(this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();

				}
				
				for(String idd : sbbmap.values()){
					int indexx = ids.indexOf(idd);
					titles.remove(indexx);
					images.remove(indexx);
					ids.remove(indexx);
				}
				
				imageList.clear();
				
				sbbmap.clear();
				
				
				picState = new int[images.size()];
				for(int i = 0 ; i< picState.length ; i++){
					picState[i] = 0;
				}
				
				adapter = new PictureAdapter(titles, images, this);
				gridView.setAdapter(adapter);
				
				
				
//				adapter.notifyDataSetChanged();
				

//				setDatas();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			ImageResultBean result = (ImageResultBean) response.getData();
			List<UserCareMerch> obtainList = ((SelectMerchListResult) result
					.getRespBean()).getRows();
			gridView = (GridView) findViewById(R.id.gridview);
			titles.clear();
			images.clear();
			ids.clear();
			if(obtainList.size() != 0){
				for(int i = 0 ; i < obtainList.size() ; i++){
					titles.add(obtainList.get(i).getMerchant().getCshort()) ;
					String a=obtainList.get(i).getMerchant().getTrademark_pic();
					images.add(a);
					ids.add(obtainList.get(i).getId().getMerch_id());
				}
			}
			
			picState = new int[images.size()];
			for(int i = 0 ; i< picState.length ; i++){
				picState[i] = 0;
			}
			
			adapter = new PictureAdapter(titles, images, this);
			gridView.setAdapter(adapter);

			gridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
//					Toast.makeText(Discount_03_MainBusinessActivity.this,
//							"pic" + (position + 1), Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		for(ImageView imgV : imageList){
			imgV.setImageResource(R.drawable.xuanze1);
		}
		sbbmap.clear();
		for(int i = 0 ; i< picState.length ; i++){
			picState[i] = 0;
		}

		Toast.makeText(Discount_03_MainBusinessActivity.this,
				((ResponseBean)response.getData()).getMessage(), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(adapter != null){
			adapter.onAdapterDestroy();
		}
	}
	
	
}
