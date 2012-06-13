package com.milanix.nepalux.content;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.milanix.nepalux.R;
import com.milanix.nepalux.R.drawable;
import com.milanix.nepalux.info.AboutUs;
import com.milanix.nepalux.search.SearchableDictionary;

/**
 * * Content.java contains a dynamic content creation code.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class Content extends Activity implements View.OnClickListener {

	ShareActionProvider mShareActionProvider;

	private static final int HELP_ID = Menu.FIRST;
	private static final int ABOUT_ID = Menu.FIRST + 1;

	private WebView webContent;
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

		imageContent = (ImageView) findViewById(R.id.imageContent);
		webContent = (WebView) findViewById(R.id.webContent);

		content = extras.getString("content");
		title = extras.getString("title");
		image = extras.getString("image");
		web = extras.getString("web") + ".html";

		System.out.println(content);

		if (content.equals("place"))
			imageContent.setOnClickListener(this);

		getId();
		imageContent.setImageResource(drawablePlaceId);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);

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
			drawablePlaceId = R.drawable.unavailable;
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

		super.onCreateOptionsMenu(menu);
		menu.add(0, HELP_ID, 0, "Help");
		menu.add(0, ABOUT_ID, 0, "About");

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.mainmenu, menu);

		mShareActionProvider = (ShareActionProvider) menu.findItem(
				R.id.menu_share).getActionProvider();

		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");

		mShareActionProvider.setShareIntent(shareIntent);
		return true;

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case ABOUT_ID: {
			Intent intent = new Intent(Content.this, AboutUs.class);
			startActivity(intent);
			return true;
		}
		case HELP_ID: {
			return true;
		}
		case R.id.menu_search: {
			Intent intent = new Intent(Content.this, SearchableDictionary.class);
			startActivity(intent);

			return true;
		}
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
