package com.milanix.nepalux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.milanix.nepalux.ui.TabsViewPagerFragmentActivity;

public class Splash extends Activity {

	ViewFlipper vf;

	@Override
	protected void onCreate(Bundle calcInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(calcInstanceState);
		setContentView(R.layout.splash);

		vf = (ViewFlipper) findViewById(R.id.splashVF);

		setAnimations();
		splashTimer();
	}

	private void splashTimer() {
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					sleep(1500);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					Intent appCore = new Intent(Splash.this,
							TabsViewPagerFragmentActivity.class);
					startActivity(appCore);
				}
			}
		};
		splashTimer.start();
	}

	// This animation is dumb at the moment
	private void setAnimations() {
		vf.setOutAnimation(AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.fade));
		vf.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade));
		vf.startFlipping();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
