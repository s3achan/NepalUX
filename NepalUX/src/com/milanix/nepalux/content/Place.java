package com.milanix.nepalux.content;

import com.milanix.nepalux.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Place extends Activity implements AdapterView.OnItemClickListener {
	TextView titlePlace;
	ListView listPlace;

	String title;
	String image;
	String web;

	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place);

		titlePlace = (TextView) findViewById(R.id.titlePlaces);
		listPlace = (ListView) findViewById(R.id.listPlaces);

		createList();
		listPlace.setOnItemClickListener(this);

	}

	private void createList() {
		String[] places = new String[] { "Namche Bazzar", "Pokhara", "Jomson",
				"Muktinath", "Annapurna Circuit" };
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, places);
		listPlace.setAdapter(adapter);
	}

	private void createContent() {
		Intent i = new Intent(Place.this, Content.class);
		i.putExtra("title", title);
		i.putExtra("image", image);
		i.putExtra("web", web);
		startActivity(i);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		title = listPlace.getItemAtPosition(position).toString();
		image = title.toLowerCase().replace(" ", "") + ".jpg";
		web = title.toLowerCase().replace(" ", "") + ".html";
		createContent();
	}

}
