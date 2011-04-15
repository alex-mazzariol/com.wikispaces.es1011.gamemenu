package com.wikispaces.es1011.gamemenu;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Waiter_CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback{
	
	SurfaceHolder mHolder;  // <2>
	SurfaceHolder hPreview;
	public Camera camera; // <3>

	Waiter_CameraPreviewView(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder(); // <4>
		mHolder.addCallback(this); // <5>
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // <6>
	}

	// Called once the holder is ready
	public void surfaceCreated(SurfaceHolder holder) { // <7>
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		hPreview = holder;
		startPreview();
	}
	
	public void startPreview()
	{
		if(hPreview == null) return;
		camera = Camera.open(); // <8>
		try {
			camera.setPreviewDisplay(hPreview); // <9>

			camera.setPreviewCallback(new PreviewCallback() { // <10>
						// Called for each frame previewed
						public void onPreviewFrame(byte[] data, Camera camera) { // <11>
							//Log.d(TAG, "onPreviewFrame called at: "
							//		+ System.currentTimeMillis());
							Waiter_CameraPreviewView.this.invalidate(); // <12>
						}
					});
		} catch (IOException e) { // <13>
			e.printStackTrace();
		}
	}
	
	public void stopPreview()
	{
		hPreview = null;
		if(camera != null)
		{
			camera.stopPreview();
			camera = null;
		}
	}

	// Called when the holder is destroyed
	public void surfaceDestroyed(SurfaceHolder holder) { // <14>
		stopPreview();
	}

	// Called when holder has changed
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
		camera.startPreview();
	}

	/*
	public Waiter_CameraShooter(Context context) {
		super(context);
		
		//setBackgroundColor(0xff000000);
	}
	static final int FOTO_MODE = 0;
	Camera mCamera;
	boolean mPreviewRunning = false;
	SurfaceHolder sHolder;
	int iW, iH;
	
	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {

				//Intent mIntent = new Intent();

				//FileUtilities.StoreByteImage(mContext, imageData,
				//		 50, "ImageName");
				
				//mCamera.startPreview();
				
				//setResult(FOTO_MODE,mIntent);
				//finish();
				//setBackgroundResource(R.color.grey_1);
				captureStop();
			}
		}
	};
	
	public void captureStop()
	{
		mPreviewRunning = false;
		mCamera.stopPreview();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if (mPreviewRunning) {
			mCamera.stopPreview();
		
			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(160, 120);
			mCamera.setParameters(p);
			try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {}
			mCamera.startPreview();
			mPreviewRunning = true;
		}
		
		sHolder = holder;
		iW = w;
		iH = h;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if(mPreviewRunning)
		{
			mCamera.stopPreview();
			mPreviewRunning = false;
			mCamera.release();
		}
	}

	public void onClick(View arg0)
	{
		if(mPreviewRunning)
			mCamera.takePicture(null, mPictureCallback, mPictureCallback);
		else
		{
			//setBackgroundResource(R.color.grey_1);
			setBackgroundColor(0xFF000000);
			
			if(mCamera == null)
				mCamera = Camera.open();
			else
			{
				mCamera.release();
				mCamera = Camera.open();
			}
			
			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(getWidth(), getHeight());
			mCamera.setParameters(p);
			try {
				mCamera.setPreviewDisplay(sHolder);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			mCamera.startPreview();
			mPreviewRunning = true;
		}
	}
	
	public void handleOrientation(int orientation) {
		if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) return;

		//If events happen BEFORE the camera is activated just ignore them
		if(mCamera == null) return;
		
		Camera.Parameters cPar = mCamera.getParameters();
		
	    orientation = (orientation + 45) / 90 * 90;
	    //int rotation = 0;
	    
	    cPar.setRotation(orientation);
	    
	    mCamera.setParameters(cPar);
	}
    */
}
