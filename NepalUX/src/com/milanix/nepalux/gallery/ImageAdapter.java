package com.milanix.nepalux.gallery;

import com.milanix.nepalux.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * * ImageAdapter.java contains a code related providing image choosing view.
 * This code is deprecated and grid gallery will be implemented.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class ImageAdapter extends BaseAdapter {
	int mGalleryItemBackground;
	private Context mContext;

	public static Integer[] mImageIds = { R.drawable.culture, R.drawable.food,
			R.drawable.place, R.drawable.namaste, R.drawable.namchebazzar,
			R.drawable.pokhara, };

	public ImageAdapter(Context c) {
		mContext = c;

		TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
		mGalleryItemBackground = a.getResourceId(
				R.styleable.HelloGallery_android_galleryItemBackground, 0);
		a.recycle();
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);

		i.setImageResource(mImageIds[position]);
		i.setLayoutParams(new Gallery.LayoutParams(250, 175));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setBackgroundResource(mGalleryItemBackground);

		return i;
	}
}