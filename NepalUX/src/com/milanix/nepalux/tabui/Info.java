package com.milanix.nepalux.tabui;

import com.milanix.nepalux.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends Fragment {
	WebView webView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View info = inflater.inflate(R.layout.info, container, false);
		webView = (WebView) info.findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.i("Info.WebView", description);
			}
		});

		webView.loadUrl("http://en.wikipedia.org/wiki/Nepal");

		// webView.loadUrl("file:///android_asset/myfile.htm");
		if (container == null) {
			return null;
		}
		return info;
	}
}
