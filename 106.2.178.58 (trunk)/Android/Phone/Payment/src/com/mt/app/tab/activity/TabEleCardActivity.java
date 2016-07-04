package com.mt.app.tab.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.frame.entity.FrameDataSource;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.app.payment.activity.EleCard_BusinessCardActivity;
import com.mt.app.payment.activity.EleCard_PaymentActivity;

public class TabEleCardActivity extends DemoTabActivity {
	@Override
	public int[] obtainIds() {
		// TODO Auto-generated method stub
		return new int[]{R.id.elePay_title  , R.id.elePay_add};
	}

	@Override
	public List<TabDataBean> obtainDatalist() {
		List<TabDataBean> datalist = new ArrayList<TabDataBean>();

		TabDataBean bean = new TabDataBean();
		bean.setClasses(EleCard_PaymentActivity.class);
		bean.setTextMess("Ö§¸¶¿¨");
		datalist.add(bean);

		bean = new TabDataBean();
		bean.setTextMess("µç×ÓÈ¯");
		bean.setClasses(EleCard_BusinessCardActivity.class);
		datalist.add(bean);

		return datalist;
	}
}
