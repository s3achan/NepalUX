package com.milanix.nepalux.tabui;

import com.milanix.nepalux.R;
import com.milanix.nepalux.R.layout;
import com.milanix.nepalux.content.Culture;
import com.milanix.nepalux.content.Food;
import com.milanix.nepalux.content.Place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Explore extends Fragment implements View.OnClickListener {
	ImageView imagePlace;
	ImageView imageFood;
	ImageView imageCulture;
	View info;
	Intent contentType;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		info = inflater.inflate(R.layout.explore, container, false);
		initializeVars();
		addListener();
		if (container == null) {
			return null;
		}
		return info;
	}

	private void initializeVars() {
		imagePlace = (ImageView) info.findViewById(R.id.imagePlace);
		imageFood = (ImageView) info.findViewById(R.id.imageFood);
		imageCulture = (ImageView) info.findViewById(R.id.imageCulture);
	}

	private void addListener() {
		imagePlace.setOnClickListener(this);
		imageFood.setOnClickListener(this);
		imageCulture.setOnClickListener(this);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imagePlace:
			contentType = new Intent();
			contentType.setClass(getActivity(), Place.class);
			startActivity(contentType);
			break;

		case R.id.imageFood:
			contentType = new Intent();
			contentType.setClass(getActivity(), Food.class);
			startActivity(contentType);
			break;

		case R.id.imageCulture:
			contentType = new Intent();
			contentType.setClass(getActivity(), Culture.class);
			startActivity(contentType);
			break;
		}
	}

}
