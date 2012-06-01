package com.milanix.nepalux.tabui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.milanix.nepalux.R;

/**
 * * Namaste.java contains a code related to tab Namaste.
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class Namaste extends Fragment implements View.OnClickListener {

	private final String NepalTourismYearURL = "http://www.youtube.com/watch?v=9srlv5lB_s0";

	private ImageView imageNamaste;
	private WebView webNamaste;
	private View viewNamaste;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewNamaste = inflater.inflate(R.layout.namaste, container, false);
		webNamaste = (WebView) viewNamaste.findViewById(R.id.webNamaste);
		imageNamaste = (ImageView) viewNamaste.findViewById(R.id.imageNamaste);
		imageNamaste.setOnClickListener(this);

		loadContent();
		if (container == null) {
			return null;
		}
		return viewNamaste;
	}

	private void loadContent() {
		webNamaste.getSettings().setJavaScriptEnabled(true);

		webNamaste.setWebChromeClient(new WebChromeClient());
		webNamaste.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}
		});

		webNamaste.loadUrl("file:///android_asset/namaste.html");
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imageNamaste:
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(NepalTourismYearURL));
			startActivity(intent);
		}
	}
}
