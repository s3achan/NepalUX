package com.milanix.nepalux.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.milanix.nepalux.R;

public class AboutUs extends Activity implements View.OnClickListener,
		RatingBar.OnRatingBarChangeListener {
	private WebView webAbout;
	private TextView textFeedback;
	private EditText feedbackMessage;
	private RatingBar rating;
	private Button submit;

	private float ratingValue;
	private final String devEmail = "gnumilanix@gmail.com";
	private String feedbackString;
	private final String appName = "NepalUX";

	private boolean feedbackSent = false;
	private static SharedPreferences settings;
	private final static String PREFS = "Sec365Prefs";

	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFS, 0);
	}

	private static SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		webAbout = (WebView) findViewById(R.id.webAbout);
		textFeedback = (TextView) findViewById(R.id.textFeedback);
		feedbackMessage = (EditText) findViewById(R.id.feedbackMessage);
		rating = (RatingBar) findViewById(R.id.rating);
		submit = (Button) findViewById(R.id.submit);

		getPrefs();
		if(feedbackSent==true)
			disableFeedback();
		
		feedbackMessage.setMaxLines(5);
		
		submit.setOnClickListener(this);
		rating.setOnClickListener(this);

		rating.setOnRatingBarChangeListener(this);

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

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.submit:
			getVars();
			if (feedbackMessage.getText() == null || ratingValue == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(
						"Please type feedback and rate the application.")
						.setTitle("Email invalid")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();

			} else {
				sendMail();

				feedbackSent = true;
				editor.putBoolean("feedbackSent", feedbackSent);
				editor.commit();
				disableFeedback();

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Thanl your for your feedback.")
						.setTitle("Thank you")
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();

			}
			break;
		}
	}

	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		Toast.makeText(AboutUs.this, "New Rating: " + rating,
				Toast.LENGTH_SHORT).show();
		ratingValue = rating;

	}

	private void sendMail() {
		String emailAddress[] = { devEmail };
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailAddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"NepalUX feedback");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, feedbackString);
		startActivity(emailIntent);
	}

	private void getVars() {
		feedbackString = "Application: " + appName + "/n" + "Rating :"
				+ ratingValue + "/n" + feedbackMessage.getText().toString();
	}

	private void disableFeedback() {
		feedbackMessage.setText("Feedback already sent to developer. You cannot send feedback again from this device.");
		rating.setProgress(0);
		submit.setEnabled(false);
		feedbackMessage.setEnabled(false);
		rating.setEnabled(false);
		submit.setEnabled(false);
	}

	public void getPrefs() {
		settings = AboutUs.getSharedPreferences(getApplicationContext());
		editor = settings.edit();

		feedbackSent = settings.getBoolean("feedbackSent", false);
	}

}
