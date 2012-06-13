package com.milanix.nepalux.tabui;

import com.milanix.nepalux.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * * Info.java contains a code related to things to do in Nepal
 * 
 * NepalUX
 * 
 * @author Milan Rajbhandari
 * @version 1.0
 */

public class Info extends Fragment implements OnClickListener {

	RelativeLayout infoVFLayout1;
	RelativeLayout infoVFLayout2;
	RelativeLayout infoVFLayout3;
	RelativeLayout infoVFLayout4;
	ViewFlipper vf;

	View vi = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View info = inflater.inflate(R.layout.info, container, false);

		vi = inflater.inflate(R.layout.info, null);

		initialize();
		setAnimations();
		setListeners();

		return vi;
	}

	private void initialize() {
		vf = (ViewFlipper) vi.findViewById(R.id.infoVF);

		infoVFLayout1 = (RelativeLayout) vi.findViewById(R.id.infoVFLayout1);
		infoVFLayout2 = (RelativeLayout) vi.findViewById(R.id.infoVFLayout2);
		infoVFLayout3 = (RelativeLayout) vi.findViewById(R.id.infoVFLayout3);
		infoVFLayout4 = (RelativeLayout) vi.findViewById(R.id.infoVFLayout4);
	}

	private void setListeners() {
		infoVFLayout1.setOnClickListener(this);
		infoVFLayout2.setOnClickListener(this);
		infoVFLayout3.setOnClickListener(this);
		infoVFLayout4.setOnClickListener(this);
	}

	private void setAnimations() {
		vf.setOutAnimation(AnimationUtils.loadAnimation(getActivity()
				.getApplicationContext(), R.anim.push_left_out));
		vf.setInAnimation(AnimationUtils.loadAnimation(getActivity()
				.getApplicationContext(), R.anim.push_left_in));
	}

	@Override
	public void onPause() {
		vf.stopFlipping();
		super.onPause();
	}

	@Override
	public void onResume() {
		vf.startFlipping();
		super.onResume();
	}

	// Dummy listeners should call activity lateron
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.infoVFLayout1:
			makeToast("ViewFlipper 1");
			break;
		case R.id.infoVFLayout2:
			makeToast("ViewFlipper 2");
			break;
		case R.id.infoVFLayout3:
			makeToast("ViewFlipper 3");
			break;
		case R.id.infoVFLayout4:
			makeToast("ViewFlipper 4");
			break;
		}
	}

	public void makeToast(String message) {
		Context context = getActivity().getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
}
