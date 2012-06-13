package com.milanix.nepalux.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.milanix.nepalux.R;
import com.milanix.nepalux.info.AboutUs;
import com.milanix.nepalux.search.SearchableDictionary;
import com.milanix.nepalux.util.LazyAdapter;

/**
 * * Place.java contains a list related to place.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class Place extends Activity implements AdapterView.OnItemClickListener {

	private static final int HELP_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;

	public static final String KEY_ITEM = "item";
	public static final String KEY_DESCRIPTION = "type";

	private ListView listPlace;

	private String title;
	private String image;
	private String web;

	final ArrayList<HashMap<String, String>> placeList = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		listPlace = (ListView) findViewById(R.id.list);

		if (placeList.isEmpty()) {
			loadContent();
			createList();
		}

		listPlace.setOnItemClickListener(this);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Places to visit");

	}

	private void createList() {
		LazyAdapter adapter = new LazyAdapter(this, placeList);
		listPlace.setAdapter(adapter);

		listPlace.setOnItemClickListener(this);

	}

	private void createContent() {
		Intent i = new Intent(Place.this, Content.class);
		i.putExtra("content", "place");
		i.putExtra("title", title);
		i.putExtra("image", image);
		i.putExtra("web", web);
		startActivity(i);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		HashMap<String, String> word = new HashMap<String, String>();
		word = placeList.get(position);

		title = word.get(KEY_ITEM);
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
			Intent intent = new Intent(Place.this, SearchableDictionary.class);
			startActivity(intent);
			return true;
		}

		}

		return super.onMenuItemSelected(featureId, item);
	}

	private void loadContent() {
		final Resources resources = this.getResources();
		InputStream inputStream = resources.openRawResource(R.raw.content);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] strings = TextUtils.split(line, "-");
				if (strings.length < 4) {
					if (strings[1].trim().equals("place")) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put(KEY_ITEM, strings[0]);
						map.put(KEY_DESCRIPTION, strings[2]);

						placeList.add(map);
					}
				}
			}
		} catch (IOException ignored) {
			makeToast("Error while loading content, Please try again.");
		} finally {
			try {
				reader.close();
			} catch (IOException ignored) {

			}
		}
	}

	public void makeToast(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}

}
