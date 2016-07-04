package com.mt.app.tab.activity;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;

import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.app.payment.activity.App_Manage1Activity;
import com.mt.app.payment.activity.App_Manage2Activity;

public class TabAppManageCardActivity extends DemoTabActivity {

	@Override
	public int[] obtainIds() {
		// TODO Auto-generated method stub
		return new int[]{R.id.appManage_title , R.id.btn_appManage_unBind ,R.id.tv_appManage_text};
	}

	@Override
	public List<TabDataBean> obtainDatalist() {
		List<TabDataBean> datalist = new ArrayList<TabDataBean>();

		TabDataBean bean = new TabDataBean();
		bean.setTextMess("已选应用");
		bean.setClasses(App_Manage1Activity.class);
		datalist.add(bean);

		bean = new TabDataBean();
		bean.setTextMess("可用应用");
		bean.setClasses(App_Manage2Activity.class);
		datalist.add(bean);
		return datalist;
	}

}
