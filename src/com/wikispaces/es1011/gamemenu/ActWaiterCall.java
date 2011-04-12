package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.Context;
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
	private CameraShooter csCamera;

	OrientationEventListener mOrientationEventListener;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		csCamera = new CameraShooter(this);
		csCamera.setOnClickListener(csCamera);
		mSurfaceHolder = csCamera.getHolder();
		mSurfaceHolder.addCallback(csCamera);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
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
		 
		mOrientationEventListener = new oelOrMgr(this);
	}
	
	private class oelOrMgr extends OrientationEventListener {
		
		public oelOrMgr(Context ctx){
			super(ctx, SensorManager.SENSOR_DELAY_NORMAL);
		}
	
		@Override
		public void onOrientationChanged(int orientation) {
			csCamera.handleOrientation(orientation);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mOrientationEventListener.canDetectOrientation())
			mOrientationEventListener.enable();		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mOrientationEventListener.disable();
	}
	
}
