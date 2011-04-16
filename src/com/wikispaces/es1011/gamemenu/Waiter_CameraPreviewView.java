package com.wikispaces.es1011.gamemenu;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;

public class Waiter_CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback{
	
	SurfaceHolder mHolder;
	SurfaceHolder hPreview;
	public Camera camera = null;
	int iOrientation = 0;

	Waiter_CameraPreviewView(Context context) {
		super(context);

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		//Init the handle to the preview holder, so startPreview() can be
		// called.
		hPreview = holder;
		startPreview();
	}
	
	public void startPreview()
	{
		if(hPreview == null || camera != null) return;
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(hPreview);
			camera.setPreviewCallback(new PreviewCallback() {
						// Called for each frame previewed
						public void onPreviewFrame(byte[] data, Camera camera) {
							Waiter_CameraPreviewView.this.invalidate();
						}
					});
			camera.setDisplayOrientation(iOrientation);
			camera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopPreview()
	{
		if(camera != null)
		{
			camera.stopPreview();
			
			//Next two calls are needed before release() because if
			// a callback fires just after release() has been called
			// the activity may crash unexpectedly.
			camera.setPreviewCallback(null);
			try {
				camera.setPreviewDisplay(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			camera.release();
			camera = null;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		stopPreview();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		hPreview = holder;
		startPreview();
	}

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
				stopPreview();
			}
		}
	};

	public void takePhoto()
	{
		if(camera != null)
			camera.takePicture(null, mPictureCallback, mPictureCallback);
	}

	public void correctOrientation(int rotation) {
		iOrientation = (rotation * (-90) + 90) % 360;
		Log.d("Waiter", "Corrected orientation " + rotation);
	}
}
