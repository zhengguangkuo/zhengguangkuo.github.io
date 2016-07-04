package com.mt.app.payment.test;
import java.util.List;  

import com.mt.android.R;
  
import android.app.Activity;  
import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class MyAdapter extends BaseAdapter {  
    private List<Menu> list;  
    private Context context;  
    private Activity activity;  
    private ImageLoader imageLoader;  
  
    private ViewHolder viewHolder;  
  
    public MyAdapter(Context context) {  
        this.context = context;  
        this.activity = (Activity) context;  
        imageLoader = new ImageLoader(context);  
    }  
  
    public void setData(List<Menu> list) {  
        this.list = list;  
    }  
  
    @Override  
    public int getCount() {  
        return list.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return list.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        if (convertView == null) {  
            convertView = LayoutInflater.from(context).inflate(  
                    R.layout.testactivity02_list_item, null);  
            viewHolder = new ViewHolder();  
            viewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);  
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.item_iv);  
            convertView.setTag(viewHolder);  
        } else {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
        viewHolder.tv.setText(list.get(position).getDishes());  
        imageLoader.DisplayImage(list.get(position).getPicPath(), activity,  
                viewHolder.iv);  
        return convertView;  
    }  
  
    private class ViewHolder {  
        private ImageView iv;  
        private TextView tv;  
    }  
  
} 