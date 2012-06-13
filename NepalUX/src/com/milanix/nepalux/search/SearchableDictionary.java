package com.milanix.nepalux.search;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.milanix.nepalux.R;
import com.milanix.nepalux.content.Content;
import com.milanix.nepalux.util.LazyAdapter;

/**
 * * SearchableDictionary.java contains a code related to UI for searching
 * content. This class is customized implementation of SearchableDictionary
 * provided by Google API Sample.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class SearchableDictionary extends Activity implements
		AdapterView.OnItemClickListener {

	private ListView mListView;
	private Intent contentIntent;

	private String content;
	private String title;
	private String image;
	private String web;

	public static final String KEY_ITEM = "item";
	public static final String KEY_DESCRIPTION = "type";

	final ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		mListView = (ListView) findViewById(R.id.list);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Uri uri = intent.getData();
			Cursor cursor = getApplicationContext().getContentResolver().query(
					uri, null, null, null, null);

			if (cursor == null) {
				finish();
			} else {
				cursor.moveToFirst();

				int wIndex = cursor
						.getColumnIndexOrThrow(DictionaryDatabase.KEY_ITEM);

				int dIndex = cursor
						.getColumnIndexOrThrow(DictionaryDatabase.KEY_DESCRIPTION);

				contentIntent = new Intent(SearchableDictionary.this,
						Content.class);

				content = cursor.getString(dIndex);
				title = cursor.getString(wIndex);
				image = title.toLowerCase().replace(" ", "");
				web = title.toLowerCase().replace(" ", "");

				createContent();

			}
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			showResults(query);
		}
	}

	private void showResults(String query) {

		Cursor cursor = getApplicationContext().getContentResolver().query(
				DictionaryProvider.CONTENT_URI, null, null,
				new String[] { query }, null);

		if (cursor == null) {
			final String[] NO_RESULTS = new String[] { getString(
					R.string.no_results, new Object[] { query }) };

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					NO_RESULTS);

			mListView.setAdapter(adapter);

		} else {

			if (cursor.moveToFirst()) {
				do {

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(KEY_ITEM, cursor.getString(cursor
							.getColumnIndex(DictionaryDatabase.KEY_ITEM)));
					map.put(KEY_DESCRIPTION,
							cursor.getString(cursor
									.getColumnIndex(DictionaryDatabase.KEY_DESCRIPTION)));

					wordList.add(map);

				} while (cursor.moveToNext());
			}
			
			LazyAdapter adapter = new LazyAdapter(this, wordList);
			mListView.setAdapter(adapter);

			mListView.setOnItemClickListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.dict_search, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search)
					.getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setFocusable(true);
			searchView.setIconified(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			this.finish();
			onSearchRequested();
			return true;
		default:
			return false;
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		HashMap<String, String> word = new HashMap<String, String>();
		word = wordList.get(position);

		contentIntent = new Intent(SearchableDictionary.this, Content.class);

		title = word.get(KEY_ITEM);
		image = title.toLowerCase().replace(" ", "");
		web = title.toLowerCase().replace(" ", "");
		content = word.get(KEY_DESCRIPTION);

		createContent();
	}

	private void createContent() {
		contentIntent.putExtra("content", content);
		contentIntent.putExtra("title", title);
		contentIntent.putExtra("image", image);
		contentIntent.putExtra("web", web);

		startActivity(contentIntent);
	}
}
