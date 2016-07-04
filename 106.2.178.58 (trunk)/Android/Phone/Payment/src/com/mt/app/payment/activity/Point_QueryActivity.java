package com.mt.app.payment.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miteno.coupon.entity.MpayUserCredits;
import com.miteno.mpay.entity.MpayApp;
import com.mt.android.R;
import com.mt.android.sys.bean.base.ResponseBean;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.sys.util.StringUtil;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.responsebean.AppBind_DataBean;
import com.mt.app.payment.responsebean.PointQueryResult;
import com.mt.app.payment.responsebean.Point_QueryRespBean;

public class Point_QueryActivity extends BaseActivity{
	private ListView listview;
    private TextView title;
    PointQueryAdapter PointQueryAdapter;
    List<String> PQid=new ArrayList<String>();
    List<String> PQName=new ArrayList<String>();
    private Button onoff;
     @Override
    protected void onCreateContent(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreateContent(savedInstanceState);
        setContentView(R.layout.point_query_layout);
		listview=(ListView)findViewById(R.id.cqlistview);
		title=(TextView)findViewById(R.id.titlewelcome);
		onoff=(Button)findViewById(R.id.onoff);
		title.setText("积分查询");
	    	onoff.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
      Request request = new Request();
      go(CommandID.map.get("POINTQUERY1"), request, true);

     }
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
		if(response.getData() == null){
			return ;
		}
		
		PointQueryResult point=(PointQueryResult)response.getData();
		
		List<Map<String, Object>> listmap1=new ArrayList<Map<String,Object>>();
		
		if(point.getRows().size() != 0){
			for(int i=0;i<point.getRows().size();i++){
		         Map<String, Object> map=new HashMap<String, Object>();
		         map.put("i", point.getRows().get(i).getCouponIssuer().getCname());
		         map.put("ip", point.getRows().get(i).getCredits());
		         listmap1.add(map);
	        PointQueryAdapter=new PointQueryAdapter(this, listmap1);
	        listview.setAdapter(PointQueryAdapter);    
		}
		}else{
			Toast.makeText(this, "您还没有积分哦！", 3000).show();
		}
		
    
	}
	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		try {

			if ((ResponseBean) response.getData() != null) {
				
				Toast.makeText(this, ((ResponseBean) response.getData()).getMessage(), Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	class PointQueryAdapter extends BaseAdapter{
    	private Context context;
    	private List<Map<String, Object>> listItems;
        private LayoutInflater listContainer;
        public final class  ListItemView{
            public  TextView  i;
            public  TextView  ip;
            

      }   
        
     public PointQueryAdapter(Context context,List<Map<String , Object>>listItems) {
            // TODO Auto-generated constructor stub
            this.context=context;
            listContainer=LayoutInflater.from(context);
            this.listItems=listItems;
            
        }

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

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		   ListItemView listItemView=null;
                 listItemView=new ListItemView();
                 //获取list_item布局文件的视图
                convertView=listContainer.inflate(R.layout.point_query_item_layout, null);
                 //获取控件对象
                 listItemView.i=(TextView)convertView.findViewById(R.id.institutions);
                 listItemView.ip=(TextView)convertView.findViewById(R.id.institutionspoint);
                 listItemView.i.setText((String)listItems.get(position).get("i"));
                 listItemView.ip.setText((String)listItems.get(position).get("ip"));
                 //设置控件集到converView
                 convertView.setTag(listItemView);
               return convertView;
    	}
}

}
