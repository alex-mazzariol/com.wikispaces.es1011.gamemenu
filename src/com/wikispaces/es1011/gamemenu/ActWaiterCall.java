package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.*;
import android.view.SurfaceHolder;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ActWaiterCall extends Activity {
	
	private SurfaceHolder mSurfaceHolder;
	private CameraShooter csCamera;
	
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
		tvT_1.setWidth(LayoutParams.WRAP_CONTENT);
		tvT_1.setHeight(LayoutParams.WRAP_CONTENT);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(160, 120);
		
		lp.addRule(RelativeLayout.BELOW, tvT_1.getId());
		
		rLL.addView(tvT_1);

		rLL.addView(csCamera, lp);
				
		setContentView(rLL);
	}

}
