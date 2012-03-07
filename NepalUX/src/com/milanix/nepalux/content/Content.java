package com.milanix.nepalux.content;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.milanix.nepalux.R;
import com.milanix.nepalux.R.drawable;
import com.milanix.nepalux.menu.AboutUs;

public class Content extends Activity implements View.OnClickListener {

	private static final int SHARE_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;
	private final String appName = "com.google.android.googlequicksearchbox";

	private WebView webContent;
	private TextView titleContent;
	private ImageView imageContent;

	private String title;
	private String content;
	private String image;
	private String web;

	private int drawablePlaceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		Bundle extras = getIntent().getExtras();

		titleContent = (TextView) findViewById(R.id.titleContent);
		imageContent = (ImageView) findViewById(R.id.imageContent);
		webContent = (WebView) findViewById(R.id.webContent);

		content = extras.getString("content");
		title = extras.getString("title");
		image = extras.getString("image");
		web = extras.getString("web") + ".html";

		titleContent.setText(title);
		titleContent.setGravity(Gravity.CENTER);
		titleContent.setTypeface(null, Typeface.BOLD);

		if (content.equals("place"))
			imageContent.setOnClickListener(this);

		getId();
		imageContent.setImageResource(drawablePlaceId);

		loadContent();
	}

	private void loadContent() {
		webContent.getSettings().setJavaScriptEnabled(true);

		webContent.setWebChromeClient(new WebChromeClient());
		webContent.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}
		});

		webContent.loadUrl("file:///android_asset/" + web);
	}

	private void getId() {
		try {
			Class<drawable> res = R.drawable.class;
			Field field = res.getField(image);
			drawablePlaceId = field.getInt(null);
		} catch (Exception e) {
			Log.e("ResID", "Failure to get drawable id.");
		}
	}

	private void pointMe() {
		String uri = "geo:" + "0,0?q=" + title;
		startActivity(new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(uri)));
	}

	public void onClick(View v) {
		pointMe();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, SHARE_ID, 0, "Share");
		menu.add(0, ABOUT_ID, 0, "About");
		return result;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case ABOUT_ID: {
			Intent intent = new Intent(Content.this, AboutUs.class);
			startActivity(intent);
			return true;
		}
		case SHARE_ID: {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + appName));
			startActivity(intent);
			return true;
		}
		}

		return super.onMenuItemSelected(featureId, item);
	}

}
