package com.wikispaces.es1011.gamemenu;

import com.wikispaces.es1011.gamemenu.Game_View.eStatus;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActGameEntertainer extends Activity implements SensorEventListener {

	private PowerManager.WakeLock wlLock;
	private SensorManager mSensorManager;
	private PowerManager mPowerManager;

	private Sensor mAccelerometer;

	private int viewHeight, viewWidth;
	private Game_View.Game_Thread gTh;
	private Game_View gVw;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// Get an instance of the PowerManager
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

		wlLock = mPowerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener((SensorEventListener) this,
				mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause() {
		super.onPause();
		//TODO Save game progress and destroy surface (and thread!)
		gVw.surfaceDestroyed(null);
		wlLock.release();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		wlLock.acquire();
		
		gVw = new Game_View(this);
		gTh = gVw.getThread();
		
		setContentView(gVw);
		
		gTh.setStatus(eStatus.Running);
		
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ORIENTATION) {
			return;
		}
		// TODO Segnalareeeeee
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
}