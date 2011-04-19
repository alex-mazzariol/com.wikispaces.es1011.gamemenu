package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.location.Criteria;
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
	private Location lcLast;
	private Criteria cBest;
	private LocationManager lMan;
	private TextView tvLocation;
	private Waiter_LocationUpdaterThread thUpdater;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		csCamera = new Waiter_CameraPreviewView(this);
		
		lMan = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		cBest = new Criteria();
		cBest.setAccuracy(Criteria.ACCURACY_FINE);
		cBest.setCostAllowed(false);
		cBest.setSpeedRequired(false);
		cBest.setAltitudeRequired(false);
		cBest.setBearingRequired(false);
		
		tvLocation = new TextView(this);
		tvLocation.setHeight(40);
		tvLocation.setGravity(Gravity.CENTER_HORIZONTAL);
		
		//locUpdate();
		
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
		rLL.addView(tvLocation);
		
		rLL.setOnClickListener(this);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(rLL);
		
		//Location updater setup
		thUpdater = new Waiter_LocationUpdaterThread(this);
	}
	
	public void locUpdate()
	{
		lcLast = lMan.getLastKnownLocation(lMan.getBestProvider(cBest, false));
		locUpdHandler.sendEmptyMessage(0);
	}
	
	private Handler locUpdHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	if(lcLast != null)
	    			tvLocation.setText(lcLast.toString());
	    		else
	    			tvLocation.setText("Unknown location");
	        }
	};
	
	@Override
	public void onResume() {
		super.onResume();
		thUpdater.start();
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
		thUpdater.interrupt();
		csCamera.stopPreview();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		thUpdater.interrupt();
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
