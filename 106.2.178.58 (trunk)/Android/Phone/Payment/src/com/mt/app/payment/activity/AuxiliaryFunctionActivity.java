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
import android.widget.Switch;
import android.widget.TextView;

import com.mt.android.R;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Response;

public class AuxiliaryFunctionActivity extends BaseActivity {
	 private ListView listview;
     private AFAdapter adapter;
     private TextView title;
     private Button onoff;
  
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateContent(savedInstanceState);
	    setContentView(R.layout.auxiliaryfuction);
	    title=(TextView)findViewById(R.id.titlewelcome);
	    onoff=(Button)findViewById(R.id.onoff);
		listview=(ListView)findViewById(R.id.aflistview);
		title.setText("辅助功能");
		String[] AF = new String[]{"推送设置","其他功能","其他功能"};
		List<Map<String, Object>> listmap=new ArrayList<Map<String,Object>>();
        for(int i=0;i< AF.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("text",AF[i]);
            listmap.add(map);
	}
     adapter=new AFAdapter(this, listmap);
     listview.setAdapter(adapter);
     onoff.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    finish();	
		}
	});
	}
	class  AFAdapter extends BaseAdapter{
		private Context context;
		private List<Map<String, Object>> listItems;
	    private LayoutInflater listContainer;
	    public final class  ListItemView{
	        public  TextView  text;
	        public Switch afswitch;

	  }   
	    
	 public AFAdapter(Context context,List<Map<String , Object>>listItems) {
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
	            convertView=listContainer.inflate(R.layout.afitem, null);
	             //获取控件对象
	             listItemView.text=(TextView)convertView.findViewById(R.id.AFtextView);
	             listItemView.afswitch=(Switch)convertView.findViewById(R.id.AFswitch);
	             listItemView.text.setText((String)listItems.get(position).get("text"));
	             //设置控件集到converView
	             convertView.setTag(listItemView);
	           return convertView;
		}
	   }
	@Override
	public void onError(Response response) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(Response response) {
		// TODO Auto-generated method stub
		
	}
}

