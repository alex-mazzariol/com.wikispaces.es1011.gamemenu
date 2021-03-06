package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Main activity for the Game tab.
 * @author Eugenio Enrico
 *
 */
public class ActGameEntertainer extends Activity implements SensorEventListener {

	private PowerManager.WakeLock wlLock;
	private SensorManager mSensorManager;
	private PowerManager mPowerManager;

	private Sensor mAccelerometer;

	private Game_View gVw;
	private Game_Status gST;

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
			gST.resetGame();
		}

		return false;
	}

	/**
	 * Called when the activity is first created.
	 * Gets a reference to the Sensor manager (to handle the accelerometer) and a reference to the Power manager
	 * (to lock the screen backlight always on). 
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

		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener((SensorEventListener) this,
				mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

		gST = new Game_Status();
	}

	/**
	 * Automatically called when the activity goes to background.
	 * Causes the backlight lock to be released and the thread to be stopped.
	 */
	@Override
	public void onPause() {
		gVw.surfaceDestroyed(null);
		gVw = null;
		wlLock.release();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		super.onPause();
	}

	/**
	 * Automatically called when the activity should save its state.
	 * Saves the game progresses.
	 * 
	 * @param outB The Bundle object game status will be saved into.
	 */
	protected void onSaveInstanceState(Bundle outB) {
		outB.putInt("g_BX", gST.iBallX);
		outB.putInt("g_BY", gST.iBallY);
		outB.putInt("g_DX", gST.iDirX);
		outB.putInt("g_DY", gST.iDirY);
		outB.putInt("g_Level", gST.iLevel);
		outB.putInt("g_Lives", gST.iLives);
		outB.putInt("g_BHit", gST.iBricksHit);
		outB.putInt("g_PX", gST.iPadX);
		outB.putIntArray("g_Bricks", gST.iBricks);
	}

	/**
	 * Automatically called when the activity is about to resume normal operation.
	 * Reloads the game progress.
	 * 
	 * @param inB The Bundle object containing the saved status.
	 */
	protected void onRestoreInstanceState(Bundle inB) {
		if (inB != null) {
			gST.iBallX = inB.getInt("g_BX");
			gST.iBallY = inB.getInt("g_BY");
			gST.iDirX = inB.getInt("g_DX");
			gST.iDirY = inB.getInt("g_DY");
			gST.iPadX = inB.getInt("g_PX");
			gST.iBricks = inB.getIntArray("g_Bricks");
			gST.iLives = inB.getInt("g_Lives");
			gST.iLevel = inB.getInt("g_Level");
			gST.iBricksHit = inB.getInt("g_BHit");
		}
	}

	/**
	 * Automatically called when the activity becomes foreground.
	 * Fixes the screen orientation to portrait
	 */
	@Override
	protected void onResume() {
		wlLock.acquire();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		gVw = new Game_View(this, gST);

		setContentView(gVw);

		super.onResume();
	}

	/**
	 * Receives the sensor event notification.
	 * Saves in the Game_Status object the tilt value, to be used as pad speed.
	 */
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}

		gST.iPadSpeed = -(int) event.values[0];
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
}
