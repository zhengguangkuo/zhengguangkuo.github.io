package com.mt.app.payment.activity;

/**
 * 演示覆盖物的用法
 */
public  class DisDetailMapActivity extends MapDetailsBaseActivity {

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		double lat = getIntent().getDoubleExtra("lat", 0.0);
		double lon = getIntent().getDoubleExtra("lon", 0.0);
		String merchantName = getIntent().getStringExtra("merchantName");
		this.merchantNames = new String[]{merchantName};
		this.merchantLons = new String[]{lon+""};
		this.merchantLats = new String[]{lat+""};
		this.showMap();
	}

	@Override
	public void onPointClick(int index) {
		// TODO Auto-generated method stub
		System.out.println(merchantNames[index] + "被点击了");
	}
	
}
