package com.mt.app.tab.activity;

import java.util.ArrayList;
import java.util.List;

import com.mt.android.R;
import com.mt.android.frame.DemoTabActivity;
import com.mt.android.frame.entity.TabDataBean;
import com.mt.android.sys.mvc.common.Request;
import com.mt.android.sys.mvc.controller.Controller;
import com.mt.android.view.common.CommandID;
import com.mt.app.payment.activity.Discount_04_MainDetailActivity;
import com.mt.app.payment.activity.Discount_04_MainDiscountActivity;
import com.mt.app.payment.activity.Discount_04_Main_youhui_DetailActivity;
import com.mt.app.payment.activity.SearchNearByActivity;
import com.mt.app.payment.requestbean.SeeMerchReqBean;

public class TabDiscount_04_MainListActivity extends DemoTabActivity {

	@Override
	public int[] obtainIds() {
		// TODO Auto-generated method stub
		return new int[]{R.id.discount_title04 ,/*R.id.see_merch_image_parent*/ };
	}

	@Override
	public List<TabDataBean> obtainDatalist() {
		List<TabDataBean> datalist = new ArrayList<TabDataBean>();

		TabDataBean bean1 = new TabDataBean();
		bean1.setTextMess("商家优惠券");
		bean1.setClasses(Discount_04_Main_youhui_DetailActivity.class);
		
		TabDataBean bean2 = new TabDataBean();
		bean2.setTextMess("商家折扣");
//		bean.setClasses(Discount_04_MainDetailActivity.class); 
		bean2.setClasses(Discount_04_MainDiscountActivity.class); 
	
		if("disMer".equals((String)Controller.session.get("Discount_Tab_Flag"))) {
			datalist.add(bean2);
			datalist.add(bean1);
		} else {
			datalist.add(bean1);
			datalist.add(bean2);			
		}
		
		return datalist;
	}
}
