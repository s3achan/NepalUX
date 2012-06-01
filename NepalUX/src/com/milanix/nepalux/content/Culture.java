package com.milanix.nepalux.content;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.milanix.nepalux.R;
import com.milanix.nepalux.info.AboutUs;
import com.milanix.nepalux.search.SearchableDictionary;

/**
 * * Culture.java contains a list related to culture. This class is deprecated
 * and will be removed.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class Culture extends Activity implements
		AdapterView.OnItemClickListener {

	private static final int HELP_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;

	private ListView listCulture;

	private String title;
	private String image;
	private String web;

	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.culture);

		listCulture = (ListView) findViewById(R.id.listCulture);

		createList();
		listCulture.setOnItemClickListener(this);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Cultures to experience");

	}

	private void createList() {
		String[] places = new String[] { "Dashain", "Tihar", "Holi",
				"Gai Jatra", "Buddha Jayanti" };
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, places);
		listCulture.setAdapter(adapter);
	}

	private void createContent() {
		Intent i = new Intent(Culture.this, Content.class);
		i.putExtra("content", "culture");
		i.putExtra("title", title);
		i.putExtra("image", image);
		i.putExtra("web", web);
		startActivity(i);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		title = listCulture.getItemAtPosition(position).toString();
		image = title.toLowerCase().replace(" ", "");
		web = title.toLowerCase().replace(" ", "");
		createContent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		menu.add(0, HELP_ID, 0, "Help");
		menu.add(0, ABOUT_ID, 0, "About");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.mainmenu, menu);

		return true;

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case ABOUT_ID: {
			Intent intent = new Intent(this, AboutUs.class);
			startActivity(intent);
			return true;
		}
		case HELP_ID: {
			return true;
		}
		case R.id.menu_search: {
			Intent intent = new Intent(Culture.this, SearchableDictionary.class);
			startActivity(intent);
			return true;
		}

		}

		return super.onMenuItemSelected(featureId, item);
	}

}
