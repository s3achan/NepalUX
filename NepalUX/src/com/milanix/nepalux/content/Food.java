package com.milanix.nepalux.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.milanix.nepalux.R;

public class Food extends Activity {
	TextView titleFood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food);

		titleFood = (TextView) findViewById(R.id.titleFood);
	}

}