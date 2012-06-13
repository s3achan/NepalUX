package com.milanix.nepalux.info;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.milanix.nepalux.R;

/**
 * * AboutUs.java contains a code related to AboutUs text and feedback
 * submission. Feedback submission code is temporarily removed. This is under
 * lazy development.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class AboutUs extends Activity {
	private WebView webAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		webAbout = (WebView) findViewById(R.id.webAbout);

		loadContent();
	}

	private void loadContent() {
		webAbout.getSettings().setJavaScriptEnabled(true);

		webAbout.setWebChromeClient(new WebChromeClient());
		webAbout.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}
		});

		webAbout.loadUrl("file:///android_asset/about.html");
	}

}
