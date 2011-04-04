package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ActWaiterCall extends Activity {
	
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private CameraShooter csCamera;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		csCamera = new CameraShooter(this);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		setContentView(R.layout.actwaitercall);
		
		mSurfaceView = (SurfaceView) findViewById(R.id.actwaitercall_camera);
		mSurfaceView.setOnClickListener(csCamera);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(csCamera);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

}
