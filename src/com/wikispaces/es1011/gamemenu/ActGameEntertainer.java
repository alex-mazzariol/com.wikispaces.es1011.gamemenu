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
import android.view.Menu;
import android.view.MenuItem;
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

	private float[] mGData = new float[3];
	private float[] mMData = new float[3];
	private float[] mR = new float[16];
	private float[] mI = new float[16];
	private float[] mOrientation = new float[3];

	private Game_View.Game_Thread gTh;
	private Game_View gVw;

	/**
	 * Contextual menu creation
	 * 
	 * @param menu
	 *            the object to manipulate
	 * @return true if the item was correctly managed
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, 1, 0, "Restart");

		return true;
	}

	/**
	 * Invoked when the user selects an item from the Menu.
	 * 
	 * @param item
	 *            the Menu entry which was selected
	 * @return true if the Menu item was legit (and we consumed it), false
	 *         otherwise
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			gTh.doStart(0);
		}

		return false;
	}

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// Get an instance of the PowerManager
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

		wlLock = mPowerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

		Sensor gsensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor msensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, gsensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, msensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause() {
		super.onPause();
		// TODO Save game progress and destroy surface (and thread!)

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
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
		int type = event.sensor.getType();
		float[] data;
		if (type == Sensor.TYPE_ACCELEROMETER) {
			data = mGData;
		//} else if (type == Sensor.TYPE_MAGNETIC_FIELD) {
			//data = mMData;
		} else {
			// we should not be here.
			return;
		}
		for (int i = 0; i < 3; i++)
			data[i] = event.values[i];

		//SensorManager.getRotationMatrix(mR, mI, mGData, mMData);
		//SensorManager.getOrientation(mR, mOrientation);
		//float incl = SensorManager.getInclination(mI);

		final float rad2deg = (float) (180.0f / Math.PI);
		
		gTh.dPadSpeed = event.values[0] * rad2deg;
		/*Log.d("Compass", "yaw: " + (int) (mOrientation[0] * rad2deg)
					+ "  pitch: " + (int) (mOrientation[1] * rad2deg)
					+ "  roll: " + (int) (mOrientation[2] * rad2deg)
					+ "  incl: " + (int) (incl * rad2deg));*/
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
}