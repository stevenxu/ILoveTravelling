package org.agilesource.android.ilovetravelling.main;


import org.agilesource.android.main.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabBar extends TabActivity {

	TabHost tabHost;
	TabSpec firstTabSpec;
	TabSpec secondTabSpec;
	TabSpec threeTabSpec;
	TabSpec fourTabSpec;
	TabSpec	fiveTabSpec;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.tab);
		//setTitle("商旅助手");

		/* TabHost will have Tabs */
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setBackgroundResource(R.drawable.nav_background);

		/*
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		/* tid1 is firstTabSpec Id. Its used to access outside. */
		firstTabSpec = tabHost.newTabSpec("tid1");
		secondTabSpec = tabHost.newTabSpec("tid2");
		threeTabSpec = tabHost.newTabSpec("tid3");
		fourTabSpec = tabHost.newTabSpec("tid4");
		fiveTabSpec = tabHost.newTabSpec("tid5");

		/* TabSpec setIndicator() is used to set name for the tab. */
		/* TabSpec setContent() is used to set content for a particular tab. */
		firstTabSpec.setIndicator("天气预报", getResources().getDrawable(
				R.drawable.tab1));
		secondTabSpec.setIndicator("汇率转换", getResources().getDrawable(
				R.drawable.tab2));
		threeTabSpec.setIndicator("新浪微博", getResources().getDrawable(
				R.drawable.tab3));
		fourTabSpec.setIndicator("世界时间", getResources().getDrawable(
				R.drawable.tab4));
		fiveTabSpec.setIndicator("记账", getResources().getDrawable(
				R.drawable.tab5));

		firstTabSpec.setContent(new Intent(this, WeatherActivity.class));
		secondTabSpec.setContent(new Intent(this, ExchangeRateActivity.class));
		threeTabSpec.setContent(new Intent(this, IndexActivity.class));
		fourTabSpec.setContent(new Intent(this, IndexActivity.class));
		fiveTabSpec.setContent(new Intent(this, IndexActivity.class));

		/* Add tabSpec to the TabHost to display. */
		
		tabHost.addTab(firstTabSpec);
		tabHost.addTab(secondTabSpec);
		tabHost.addTab(threeTabSpec);
		tabHost.addTab(fourTabSpec);
		tabHost.addTab(fiveTabSpec);
	}
}
