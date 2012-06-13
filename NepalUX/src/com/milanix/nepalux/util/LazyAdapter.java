package com.milanix.nepalux.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.milanix.nepalux.R;
import com.milanix.nepalux.R.drawable;
import com.milanix.nepalux.search.SearchableDictionary;

/**
 * * LazyAdapter.java contains a code related to creating custom list view for
 * search result and content choosing.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @author s3achan
 * @version 1.0
 */

public class LazyAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	private int drawablePlaceId;

	// private String content;
	private String title;
	private String image;
	private String description;

	TextView tvtitle;
	TextView tvinfo;
	ImageView tvimage;

	public LazyAdapter(Activity activity,
			ArrayList<HashMap<String, String>> data) {
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null)
			view = inflater.inflate(R.layout.list_row, null);

		tvtitle = (TextView) view.findViewById(R.id.title);
		tvinfo = (TextView) view.findViewById(R.id.description);
		tvimage = (ImageView) view.findViewById(R.id.list_image);

		HashMap<String, String> word = new HashMap<String, String>();

		word = data.get(position);

		title = word.get(SearchableDictionary.KEY_ITEM);
		image = title.toLowerCase().replace(" ", "");
		description = word.get(SearchableDictionary.KEY_DESCRIPTION);

		getId();
		setUI();

		return view;
	}

	private void setUI() {
		tvtitle.setText(title);
		tvinfo.setText(description);
		tvimage.setImageResource(drawablePlaceId);
	}

	private void getId() {
		try {
			Class<drawable> res = R.drawable.class;
			Field field = res.getField(image);
			drawablePlaceId = field.getInt(null);
		} catch (Exception e) {
			drawablePlaceId = R.drawable.unavailable;
		}
	}
}