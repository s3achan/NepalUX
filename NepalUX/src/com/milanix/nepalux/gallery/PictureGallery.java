package com.milanix.nepalux.gallery;

import com.milanix.nepalux.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

/**
 * * Picture.java contains a code related providing image view. This code is
 * deprecated and grid gallery will be implemented.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class PictureGallery extends Activity implements
		AdapterView.OnItemClickListener {

	ImageView galleryImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		galleryImage = (ImageView) findViewById(R.id.galleryImage);
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));

		g.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(PictureGallery.this, "" + position, Toast.LENGTH_SHORT)
				.show();
		galleryImage.setImageResource(ImageAdapter.mImageIds[position]);

	}
}