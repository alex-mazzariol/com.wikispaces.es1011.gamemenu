package com.wikispaces.es1011.gamemenu;

/**
 * TODO sistemare le viste
 */

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActGameEntertainer extends Activity implements
		SensorEventListener, IUpdatable, SurfaceHolder.Callback {

	public static Display display;
	public TextView tv;
	private SensorManager mSensorManager;
	private PowerManager mPowerManager;
	private WindowManager mWindowManager;
	private FrameLayout lGameFrame;

	private Game_SurfaceThread thread;

	private Game_Status gs;
	private IGameSurface actuallyShownView;
	private Game_GameSurfaceView gsw;
	private Game_ReadySurfaceView rsw;

	private LinearLayout gameLayout;
	private Sensor mAccelerometer;

	private Game_Ball ball;
	private Game_Pad pad;
	private Game_BrickMatrix brickMatrix;
	private Rect underRect;
	private int viewHeight, viewWidth;
	private int brickNum = 20;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		display = getWindowManager().getDefaultDisplay();
		// Get an instance of the SensorManager
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// Get an instance of the PowerManager
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

		// Get an instance of the WindowManager
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mWindowManager.getDefaultDisplay();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				getClass().getName());

		/**
		 * Creo il thread per il disegno su schermo
		 */
		thread = new Game_SurfaceThread(this);

		/**
		 * Crea le surface view
		 */
		gs = new Game_Status();
		gsw = new Game_GameSurfaceView(this);
		gsw.getHolder().addCallback(this);
		rsw = new Game_ReadySurfaceView(this, gs);
		rsw.getHolder().addCallback(this);

		/**
		 * Istanzio l'accelerometro
		 */
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mSensorManager.registerListener((SensorEventListener) this,
				mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

		lGameFrame = new FrameLayout(this);
		// lGameFrame.addView(rsw);
		// lGameFrame.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT));
		actuallyShownView = rsw;

		gs.setState(Game_Status.Status.READY);

		setContentView((View) actuallyShownView);
	}

	@Override
	public void onPause() {
		super.onPause();

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
		gs.setPadDirectionX((int) -event.values[2]);
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	private void update() {

		Rect[] box = gs.getPadBoxes();
		if (box[0].intersect(gs.getBallBox())) {
			gs.setBallDirectionX(-4);
			gs.setBallDirectionY(-2);
		}
		if (box[1].intersect(gs.getBallBox())) {
			gs.setBallDirectionX(-3);
			gs.setBallDirectionY(-2);
		}
		if (box[2].intersect(gs.getBallBox())) {
			gs.setBallDirectionX(-5);
			gs.setBallDirectionY(0);
		}
		if (box[3].intersect(gs.getBallBox())) {
			gs.setBallDirectionX(3);
			gs.setBallDirectionY(-2);
		}
		if (box[4].intersect(gs.getBallBox())) {
			gs.setBallDirectionX(4);
			gs.setBallDirectionY(-2);
		}

		for (int j = 0; j < brickMatrix.getRow(); j++) {
			for (int i = 0; i < brickMatrix.getColumn(); i++) {
				if (brickMatrix.getBrick(i, j).isVisible()
						&& gs.getBallBox().intersect(
								brickMatrix.getBrick(i, j).getBox())) {
					brickMatrix.getBrick(i, j).setVisible(false);
					brickMatrix.getBrick(i, j).setBox(new Rect(-1, -1, -1, -1));
				}

				if (gs.getBallBox().intersect(
						brickMatrix.getBrick(i, j).getBox().left,
						brickMatrix.getBrick(i, j).getBox().top,
						brickMatrix.getBrick(i, j).getBox().left + 1,
						brickMatrix.getBrick(i, j).getBox().bottom)
						|| gs.getBallBox().intersect(
								brickMatrix.getBrick(i, j).getBox().right - 1,
								brickMatrix.getBrick(i, j).getBox().top,
								brickMatrix.getBrick(i, j).getBox().right,
								brickMatrix.getBrick(i, j).getBox().bottom)) {
					gs.setBallDirectionX(-gs.getBallDirectionX());
				} else {
					gs.setBallDirectionY(-gs.getBallDirectionY());
				}

				gs.setScore(100);
			}

		}

		// TODO rimuovere dipendenza dal tempo di ball e pad
		/*
		 * if (GameTime - mFrameTimer > speed) { mFrameTimer = GameTime;
		 */

		if (gs.getBallXPos() <= 0) {
			gs.setBallXPos(1);
			gs.setBallDirectionX(-gs.getBallDirectionX());
		}

		if (gs.getBallXPos() >= viewWidth - 1) {
			gs.setBallXPos(viewWidth - 1);
			gs.setBallDirectionX(-gs.getBallDirectionX());
		}

		if (gs.getBallYPos() <= 0) {
			gs.setBallXPos(1);
			gs.setBallDirectionY(-gs.getBallDirectionY());
		}

		if (gs.getBallYPos() >= viewHeight - gs.getBallBox().height()) {
			ball = new Game_Ball(viewWidth, viewHeight, gsw, gs);
			if (gs.getLives() > 0) {
				gs.setLives(gs.getLives() - 1);
			} else // TODO implementare lose:
			{
				gs.setState(Game_Status.Status.LOSE);
			}

		}

		gs.setBallXPos(gs.getBallXPos() + gs.getBallDirectionX() % viewWidth);
		gs.setBallYPos(gs.getBallYPos() + gs.getBallDirectionY() % viewHeight);
		Rect newBox = new Rect(gs.getBallXPos(), gs.getBallYPos(), gs
				.getBallXPos()
				+ gs.getBallBox().width(), gs.getBallBox().height());
		gs.setBallBox(newBox);

		// if (GameTime - mFrameTimer > speed) {
		// mFrameTimer = GameTime;
		final int padWidth = (gs.getPadBox1().width() + gs.getPadBox2().width()
				+ gs.getPadBox3().width() + gs.getPadBox4().width() + gs
				.getPadBox5().width());
		if (gs.getPadXPos() <= 0 || gs.getPadXPos() >= viewWidth - padWidth) {
			gs.setPadDirectionX(-gs.getPadDirectionX());
		}
		gs.setPadXPos(java.lang.Math.abs(gs.getPadXPos()
				+ gs.getPadDirectionX())
				% viewWidth);

		gs.setPadBox1(new Rect(gs.getPadXPos(), gs.getPadYPos(), gs
				.getPadXPos()
				+ padWidth * 1 / 5, gs.getPadYPos() + padWidth));
		gs.setPadBox2(new Rect(gs.getPadXPos() + padWidth * 1 / 5, gs
				.getPadYPos(), gs.getPadXPos() + padWidth * 2 / 5, gs
				.getPadYPos()
				+ padWidth));
		gs.setPadBox3(new Rect(gs.getPadXPos() + padWidth * 2 / 5, gs
				.getPadYPos(), gs.getPadXPos() + padWidth * 3 / 5, gs
				.getPadYPos()
				+ padWidth));
		gs.setPadBox4(new Rect(gs.getPadXPos() + padWidth * 3 / 5, gs
				.getPadYPos(), gs.getPadXPos() + padWidth * 4 / 5, gs
				.getPadYPos()
				+ padWidth));
		gs.setPadBox1(new Rect(gs.getPadXPos() + padWidth * 4 / 5, gs
				.getPadYPos(), gs.getPadXPos() + padWidth, gs.getPadYPos()
				+ padWidth));

		/**
		 * Sposta gli sprite
		 */
		Rect ballTmp = gs.getBallBox();
		ballTmp.set(ballTmp.left+gs.getBallDirectionX(), ballTmp.top+gs.getBallDirectionY(), ballTmp.right+gs.getBallDirectionX(), ballTmp.bottom + gs.getBallDirectionY());		
		gs.setBallBox(ballTmp);
		
		//Rect[] padBox =  gs.getPadBoxes();
		//foreach()
		
		gs.setPadDirectionX(0);
	}

	public void forceUpdate() {
		gs.setGameTime(System.currentTimeMillis());
		update();

		if (gs.getState() == Game_Status.Status.READY)
			actuallyShownView = rsw;

		if (gs.getState() == Game_Status.Status.RUNNING)
			actuallyShownView = gsw;

		actuallyShownView.viewUpdate();
	}

	// ----------------------------------------------------------------------------SurfaceHolder.Callback
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		viewWidth = holder.getSurfaceFrame().width();
		viewHeight = holder.getSurfaceFrame().height();

		actuallyShownView.initDimension(viewWidth, viewHeight);

		if (ball == null)
			ball = new Game_Ball(viewWidth, viewHeight, gsw, gs);

		if (pad == null)
			pad = new Game_Pad(viewWidth, viewHeight, gsw, gs);

		if (brickMatrix == null)
			brickMatrix = new Game_BrickMatrix(viewWidth, viewHeight, gsw,
					brickNum, gs);

		if (underRect == null)
			underRect = new Rect(0, viewHeight - viewWidth / 20, viewWidth,
					viewHeight);

		if (actuallyShownView instanceof Game_GameSurfaceView) {
			((Game_GameSurfaceView) actuallyShownView).initSprites(gs, ball,
					pad, brickMatrix, underRect);
		}

		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}
