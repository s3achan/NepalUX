package com.milanix.nepalux;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.milanix.nepalux.tabui.Explore;
import com.milanix.nepalux.tabui.Info;
import com.milanix.nepalux.tabui.Namaste;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class TabsViewPagerFragmentActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private static final int SHARE_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;

	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}

	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_viewpager_layout);

		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		this.intialiseViewPager();
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, Namaste.class.getName()));
		fragments.add(Fragment.instantiate(this, Info.class.getName()));
		fragments.add(Fragment.instantiate(this, Explore.class.getName()));
		this.mPagerAdapter = new PagerAdapter(
				super.getSupportFragmentManager(), fragments);
		//
		this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("namaste").setIndicator("Namaste"),
				(tabInfo = new TabInfo("Namaste", Namaste.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("info").setIndicator("Info"),
				(tabInfo = new TabInfo("Info", Info.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost
				.newTabSpec("xplore").setIndicator("Xplore"),
				(tabInfo = new TabInfo("Explore", Explore.class, args)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);

		mTabHost.setOnTabChangedListener(this);
	}

	private static void AddTab(TabsViewPagerFragmentActivity activity,
			TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	public void onTabChanged(String tag) {

		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, SHARE_ID, 0, "Share");
		menu.add(0, ABOUT_ID, 0, "About");
		return result;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		/*
		 * switch (item.getItemId()) { case ABOUT_ID: { Intent intent = new
		 * Intent(NepalUXActivity.this, About.class); startActivity(intent);
		 * return true; } case SHARE_ID: { return true; } }
		 */

		return super.onMenuItemSelected(featureId, item);
	}

}
