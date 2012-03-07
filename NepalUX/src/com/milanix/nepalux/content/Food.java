package com.milanix.nepalux.content;

import com.milanix.nepalux.R;
import com.milanix.nepalux.menu.AboutUs;
import com.milanix.nepalux.ui.TabsViewPagerFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Food extends Activity implements AdapterView.OnItemClickListener {

	private static final int SHARE_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;
	private final String appName = "com.google.android.googlequicksearchbox";

	private TextView titleFood;
	private ListView listFood;

	private String title;
	private String image;
	private String web;

	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);

		titleFood = (TextView) findViewById(R.id.titleFood);
		listFood = (ListView) findViewById(R.id.listFood);

		createList();
		listFood.setOnItemClickListener(this);

	}

	private void createList() {
		String[] places = new String[] { "Chicken Bhutua", "Chiken Chilli",
				"Chicken Masala", "Chicken Tarkari", "Babari Ko Achaar" };
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, places);
		listFood.setAdapter(adapter);
	}

	private void createContent() {
		Intent i = new Intent(Food.this, Content.class);
		i.putExtra("content", "food");
		i.putExtra("title", title);
		i.putExtra("image", image);
		i.putExtra("web", web);

		startActivity(i);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		title = listFood.getItemAtPosition(position).toString();
		image = title.toLowerCase().replace(" ", "");
		web = title.toLowerCase().replace(" ", "");
		createContent();
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

		switch (item.getItemId()) {
		case ABOUT_ID: {
			Intent intent = new Intent(Food.this,
					AboutUs.class);
			startActivity(intent);
			return true;
		}
		case SHARE_ID: {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + appName));
			startActivity(intent);
			return true;
		}
		}

		return super.onMenuItemSelected(featureId, item);
	}
}
