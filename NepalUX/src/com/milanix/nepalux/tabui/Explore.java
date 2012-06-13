package com.milanix.nepalux.tabui;


import com.milanix.nepalux.R;
import com.milanix.nepalux.content.Culture;
import com.milanix.nepalux.content.Food;
import com.milanix.nepalux.content.Gallery;
import com.milanix.nepalux.content.Place;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Explore extends Fragment {
    
	private View info;
	Button btn_culture;
    Button btn_food;
    Button btn_gallery;    
    Button btn_places;
	private Intent contentType;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		info = inflater.inflate(R.layout.dashboard_layout, container, false);

		initializeVars();
		addListeners();
		if (container == null) {
			return null;
		}
		return info;
	}
	
	
	
	public void initializeVars(){
		btn_culture = (Button) info.findViewById(R.id.btn_culture);
		btn_food = (Button) info.findViewById(R.id.btn_food);
		btn_gallery = (Button) info.findViewById(R.id.btn_gallery);
		btn_places = (Button) info.findViewById(R.id.btn_places);
	}
	

    public void addListeners(){
        btn_places.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				contentType = new Intent();
				contentType.setClass(getActivity(), Place.class);
				startActivity(contentType);
			}
		});
        
        btn_culture.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				contentType = new Intent();
				contentType.setClass(getActivity(), Culture.class);
				startActivity(contentType);
			}
		});
        
        btn_food.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
				contentType = new Intent();
				contentType.setClass(getActivity(), Food.class);
				startActivity(contentType);
			}
		});
        
        btn_gallery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				contentType = new Intent();
				contentType.setClass(getActivity(), Gallery.class);
				startActivity(contentType);
			}
		});
        
	}
	

}