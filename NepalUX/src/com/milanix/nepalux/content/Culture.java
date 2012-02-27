package com.milanix.nepalux.content;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.milanix.nepalux.R;

public class Culture extends Activity {
	TextView titlePlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.culture);

		titlePlace = (TextView) findViewById(R.id.titleCulture);
	}

}