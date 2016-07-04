package com.mt.android.help.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.mt.android.R;
import com.mt.android.frame.listview.ListViewAdapter;
import com.mt.android.help.listview.HelpListViewAdapter;
import com.mt.android.help.listview.Person;
import com.mt.android.sys.common.view.BaseActivity;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.common.Response;
import com.mt.android.view.common.BaseCommandID;
import com.mt.android.view.form.BaseFormImpl;

/**
 * 帮助工程主菜单界面，创建一些菜单
 * @author SKS
 *
 */
public class ListViewShowActivity extends BaseActivity {
	private static Logger log = Logger.getLogger(ListViewShowActivity.class);
	private ListView listView = null;
	private List<Person> datas = new ArrayList<Person>();
	@Override
	protected void onCreateContent(Bundle savedInstanceState) {
		setContentView(R.layout.help_listviewshow);
		log.debug("ListViewShowActivity show");
		listView = (ListView) findViewById(R.id.help_listview);
	
		Request request = new Request();
		go(BaseCommandID.map.get("LISTVIEW_INIT"), request, false);
		//添加上下文菜单
		registerForContextMenu(listView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("人员登记说明");
		menu.add(0, 1, 1, "功能选择1");
		menu.add(0, 2, 2, "功能选择2");
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int selectedPosition = ((AdapterContextMenuInfo) item.getMenuInfo()).position;
	
		Toast.makeText(ListViewShowActivity.this, "姓名:" + datas.get(selectedPosition).getName() + ", 菜单:" + item.getItemId() ,
				Toast.LENGTH_LONG).show();
		return super.onContextItemSelected(item);
	}
	@Override
	public void onSuccess(Response response)
	{
		new BaseFormImpl().bean2Form(response.getData(), findViewById(R.id.help_listview), this);
	}
	
	@Override
	public void onError(Response response)
	{
		
	}
}
