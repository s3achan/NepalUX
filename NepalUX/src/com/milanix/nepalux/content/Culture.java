package com.milanix.nepalux.content;

import com.milanix.nepalux.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Culture extends Activity implements AdapterView.OnItemClickListener {
	private TextView titleCulture;
	private ListView listCulture;

	private String title;
	private String image;
	private String web;

	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.culture);

		titleCulture = (TextView) findViewById(R.id.titleCulture);
		listCulture = (ListView) findViewById(R.id.listCulture);

		createList();
		listCulture.setOnItemClickListener(this);

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

}
