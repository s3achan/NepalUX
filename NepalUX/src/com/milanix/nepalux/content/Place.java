package com.milanix.nepalux.content;

import com.milanix.nepalux.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Place extends Activity {
	TextView titlePlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place);

		titlePlace = (TextView) findViewById(R.id.titlePlace);
	}

}
