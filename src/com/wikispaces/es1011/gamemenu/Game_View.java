package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_View extends SurfaceView implements SurfaceHolder.Callback {
	private Context mc;
	private Game_Thread gThread;

	public Game_View(Context context) {
		super(context);
		mc = context;

		getHolder().addCallback(this);

		gThread = new Game_Thread(getHolder(), context, null);
		// TODO Check handler...
	}

	public Game_Thread getThread() {
		return gThread;
	}

	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			gThread.pause();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int width,
			int height) {
		gThread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		gThread.setRunning(true);
		gThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean bb = true;
		gThread.setRunning(false);
		while (bb) {
			try {
				gThread.join();
				bb = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public static enum eStatus {
		Lose, Pause, Ready, Running, Win
	}

	class Game_Thread extends Thread {
		private int iCanvasW, iCanvasH;
		private eStatus sStatus;
		private boolean bRun = false;

		private Bitmap bBall, bPad, bBrick[];

		private Handler mHndl;
		private SurfaceHolder shHolder;

		private long lTime;

		public Game_Thread(SurfaceHolder sh, Context cx, Handler hndl) {
			shHolder = sh;
			mHndl = hndl;

			bBall = BitmapFactory.decodeResource(cx.getResources(),
					R.drawable.game_ball);
			bPad = BitmapFactory.decodeResource(cx.getResources(),
					R.drawable.game_sprite_pad);
		}

		public void doStart() {
			synchronized (shHolder) {
				// TODO init x, y, varie

				lTime = System.currentTimeMillis() + 100;
				setStatus(eStatus.Running);
			}
		}

		public void setStatus(eStatus newVal) {
			synchronized (shHolder) {
				sStatus = newVal;
			}
		}

		public void pause() {
			synchronized (shHolder) {
				if (sStatus == eStatus.Running)
					setStatus(eStatus.Pause);
			}
		}

		public void run() {
			while (bRun) {
				Canvas c = null;
				try {
					c = shHolder.lockCanvas();
					synchronized (shHolder) {
						if (sStatus == eStatus.Running)
							updateGame();
						doDraw(c);
					}
				} finally {
					if (c != null)
						shHolder.unlockCanvasAndPost(c);
				}
			}
		}

		public void setRunning(boolean b) {
			bRun = b;
		}

		public void setSurfaceSize(int iWid, int iHei) {
			synchronized (shHolder) {
				iCanvasW = iWid;
				iCanvasH = iHei;
			}
		}

		public void unpause() {
			synchronized (shHolder) {
				lTime = System.currentTimeMillis() + 100;
			}
			setStatus(eStatus.Running);
		}

		private void doDraw(Canvas c) {
			c.drawARGB(255, 80, 80, 80);

			c.drawBitmap(bBall, 50, 50, new Paint());
		}

		private void updateGame() {
			// TODO game logic
		}
	}
}
