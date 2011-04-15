package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.*;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ActWaiterCall extends Activity {
	
	private SurfaceHolder mSurfaceHolder;
	private Waiter_CameraPreviewView csCamera;

	Waiter_OrientationListen mOrientationEventListener;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		csCamera = new Waiter_CameraPreviewView(this);
		//mOrientationEventListener = new Waiter_OrientationListen(this, csCamera);
		
		//csCamera.setOnClickListener(csCamera);
		mSurfaceHolder = csCamera.getHolder();
		mSurfaceHolder.addCallback(csCamera);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		RelativeLayout rLL = new RelativeLayout(this);
		
		TextView tvT_1 = new TextView(this);
		tvT_1.setText("Click.");
		tvT_1.setWidth(LayoutParams.FILL_PARENT);
		tvT_1.setHeight(LayoutParams.FILL_PARENT);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(160, 120);
		
		lp.addRule(RelativeLayout.ABOVE, tvT_1.getId());
		
		rLL.addView(tvT_1);

		rLL.addView(csCamera, lp);
		
		setContentView(rLL);
		/*
		LocationManager lMan = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		Location lcLast = lMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		TextView tvT_2 = new TextView(this);
		
		if(lcLast != null)
			tvT_2.setText(lcLast.toString());
		else
			tvT_2.setText("Unknown location");
		
		rLL.addView(tvT_2);*/
	}
	
	@Override
	public void onStart() {
		super.onStart();
		/*if (mOrientationEventListener.canDetectOrientation())
			mOrientationEventListener.enable();*/
		csCamera.startPreview();
	}
	
	@Override
	protected void onPause() {
		csCamera.stopPreview();
		//mOrientationEventListener.disable();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		csCamera.stopPreview();
		//mOrientationEventListener.disable();
		super.onStop();
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    /*
	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	    // Checks whether a hardware keyboard is available
	    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
	        Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
	        Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
	    }*/
	}
}
