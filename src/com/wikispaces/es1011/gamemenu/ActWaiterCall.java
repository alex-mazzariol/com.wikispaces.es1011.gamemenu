package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.*;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActWaiterCall extends Activity implements OnClickListener {
	
	private Waiter_CameraPreviewView csCamera;
	private LinearLayout rLL;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		csCamera = new Waiter_CameraPreviewView(this);
		
		/*
		LocationManager lMan = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		Location lcLast = lMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		TextView tvT_2 = new TextView(this);
		
		if(lcLast != null)
			tvT_2.setText(lcLast.toString());
		else
			tvT_2.setText("Unknown location");
		
		rLL.addView(tvT_2);*/
		rLL = new LinearLayout(this);
		rLL.setOrientation(LinearLayout.VERTICAL);
		rLL.setGravity(Gravity.CENTER_HORIZONTAL);
		TextView tvT_1 = new TextView(this);
		tvT_1.setText("Click the black area to\nsend a request to the waiter.");
		tvT_1.setWidth(LayoutParams.FILL_PARENT);
		tvT_1.setHeight(40);
		tvT_1.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LayoutParams lPar = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lPar.setMargins(5, 5, 5, 5);
		
		rLL.setLayoutParams(lPar);
		rLL.addView(tvT_1);
		
		rLL.addView(csCamera, 180, 240);
		rLL.setOnClickListener(this);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(rLL);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int iR = getWindowManager().getDefaultDisplay().getRotation();
		if(iR == 1 || iR == -1)
		{
			rLL.getChildAt(1).setLayoutParams(new LayoutParams(240, 180));
		}
		else
		{
			rLL.getChildAt(1).setLayoutParams(new LayoutParams(180, 240));
		}
		csCamera.correctOrientation(iR);
		csCamera.startPreview();
	}
	
	@Override
	protected void onPause() {
		csCamera.stopPreview();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		csCamera.stopPreview();
		super.onStop();
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	//@Override
	public void onClick(View arg0) {
		//TODO Make the request to the waiter
		csCamera.takePhoto();
		csCamera.stopPreview();
	}
}
