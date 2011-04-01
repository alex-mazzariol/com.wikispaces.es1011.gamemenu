package com.wikispaces.es1011.gamemenu;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.*;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

public class ActWaiterCall extends Activity implements SurfaceHolder.Callback, OnClickListener {
	static final int FOTO_MODE = 0;
	Camera mCamera;
	boolean mPreviewRunning = false;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.actwaitercall);
		mSurfaceView = (SurfaceView) findViewById(R.id.actwaitercall_camera);
		mSurfaceView.setOnClickListener(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {

				Intent mIntent = new Intent();

				//FileUtilities.StoreByteImage(mContext, imageData,
				//		 50, "ImageName");
				
				
				
				mCamera.startPreview();
				
				setResult(FOTO_MODE,mIntent);
				//finish();
			}
		}
	};

	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}

		Camera.Parameters p = mCamera.getParameters();
		p.setPreviewSize(w, h);
		mCamera.setParameters(p);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {}
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}

	public void onClick(View arg0) {
		mCamera.takePicture(null, mPictureCallback, mPictureCallback);
	}

}
