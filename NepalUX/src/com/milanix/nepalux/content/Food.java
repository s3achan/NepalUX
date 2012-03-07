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

public class Food extends Activity implements AdapterView.OnItemClickListener {
	TextView titleFood;
	ListView listFood;

	String title;
	String image;
	String web;

	ArrayAdapter<String> adapter;

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

}
