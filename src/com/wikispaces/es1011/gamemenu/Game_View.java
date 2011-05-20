package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_View extends SurfaceView implements SurfaceHolder.Callback {
	private Context mc;
	private Game_Thread gThread;

	public Game_View(Context context) {
		super(context);
		mc = context;

		getHolder().addCallback(this);

		gThread = new Game_Thread(getHolder());
	}

	public Game_Thread getThread() {
		return gThread;
	}

	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			gThread.pause();
	}

	public void surfaceChanged(SurfaceHolder arg0, int format, int width,
			int height) {
		gThread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		gThread.setRunning(true);
		gThread.doStart(0);
		gThread.start();
	}

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
		private int iBricks[];
		private int iBricksHit;
		public int iPadSpeed = 0;
		private int iPadX, iPadY, iBallX, iBallY, iLevel;
		private int iOffsetXBricks;

		private int iDefaultX = 50, iDefaultY = 50;

		private int iDirX = 3, iDirY = 3;
		private int iLives;

		private SurfaceHolder shHolder;
		private int iBrickW, iBrickH;

		private long lTime;

		public Game_Thread(SurfaceHolder sh) {
			shHolder = sh;

			setSurfaceSize(sh.getSurfaceFrame().width(), sh.getSurfaceFrame()
					.height());
		}

		public void doStart(int iLevel) {
			synchronized (shHolder) {
				this.iLevel = 14 + iLevel;
				iPadX = 0;
				iBallX = iDefaultX;
				iBallY = iDefaultY;
				iBricksHit = 0;

				iBricks = new int[this.iLevel];
				iLives = 3;

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
				if (System.currentTimeMillis() - lTime > 50) {
					lTime += 50;
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
		}

		public void setRunning(boolean b) {
			bRun = b;
		}

		public void setSurfaceSize(int iWid, int iHei) {
			if (iWid == 0 || iHei == 0)
				return;

			synchronized (shHolder) {
				iCanvasW = iWid;
				iCanvasH = iHei;

				bBall = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
						mc.getResources(), R.drawable.game_sprite_ball),
						iCanvasW / 12, iCanvasW / 12, true);
				bPad = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
						mc.getResources(), R.drawable.game_sprite_pad),
						iCanvasW / 5, iCanvasW / 15, true);

				iBrickW = iCanvasW / 7;
				iOffsetXBricks = (iCanvasW - iBrickW * 7) / 2;
				iBrickH = iBrickW / 3;
				iPadY = iCanvasH - bPad.getHeight();

				bBrick = new Bitmap[4];
				bBrick[0] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(
								mc.getResources(), R.drawable.game_brick1),
								iBrickW, iBrickH, true);
				bBrick[1] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(
								mc.getResources(), R.drawable.game_brick2),
								iBrickW, iBrickH, true);
				bBrick[2] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(
								mc.getResources(), R.drawable.game_brick3),
								iBrickW, iBrickH, true);
				bBrick[3] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(
								mc.getResources(), R.drawable.game_brick4),
								iBrickW, iBrickH, true);

				iDefaultX = (iCanvasW - bBall.getWidth()) / 2;
				iDefaultY = (iCanvasH - bBall.getHeight()) / 2;
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

			c.drawBitmap(bBall, iBallX, iBallY, null);
			c.drawBitmap(bPad, iPadX, iPadY, null);

			for (int i = 0; i < iLevel; i++) {
				if (iBricks[i] == 0)
					c.drawBitmap(bBrick[i % 4], iOffsetXBricks + (i % 7)
							* iBrickW, (int) Math.floor(i / 7) * iBrickH + 20,
							null);
			}

			Paint pBlack = new Paint();
			pBlack.setColor(Color.BLACK);
			Paint pBlue = new Paint();
			pBlue.setColor(Color.argb(0xFF, 0x88, 0x88, 0xff));
			pBlue.setAntiAlias(true);
			pBlue.setTextSize(16);

			c.drawRect(new Rect(0, 0, iCanvasW, 19), pBlack);
			c.drawText("Level " + Integer.toString(iLevel - 13) + ", lives "
					+ iLives, 5, 17, pBlue);
		}

		private void updateGame() {

			// Try to keep 20 FPS
			if (iBallX > (iCanvasW - bBall.getWidth() - 1))
				iDirX *= -1;

			if (iBallX < 1)
				iDirX *= -1;

			if (iBallY < 21)
				iDirY *= -1;

			if (iBallY > (iCanvasH - bBall.getHeight() - 1)) {
				if (iLives > 0) {
					// Restart position
					iBallY = iDefaultY;
					iBallX = iDefaultX;

					iLives--;
				} else {
					// Game over
					doStart(0);
					return;
				}
			}

			// Collisions
			for (int i = 0; i < iLevel; i++) {
				if (iBricks[i] == 0) {
					int iBrickX = iOffsetXBricks + (i % 7) * iBrickW;
					int iBrickY = (int) Math.floor(i / 7) * iBrickH + 20;
					// Check collision
					boolean bXCollision = iBallX > iBrickX - bBall.getWidth()
							&& iBallX < iBrickX + iBrickW;
					boolean bYCollision = iBallY > iBrickY - bBall.getHeight()
							&& iBallY < iBrickY + iBrickH;
					if (bXCollision && bYCollision) {
						// Collided!
						iBricks[i] = 1;
						iBricksHit++;

						// Check if last brick
						if (iBricksHit == iLevel) {
							// Level cleared
							doStart(iLevel + 1);
							return;
						}

						// Update ball direction.
						if (iDirX > 0 && iDirY < 0) {
							if (iBallY < iBrickY + (iBrickH / 2)) {
								// Side hit, change also X direction.
								iDirX *= -1;
							}

							if (iBallX > iBrickX - (bBall.getWidth() / 2)) {
								// Bottom hit, change also Y direction.
								iDirY *= -1;
							}
						} else if (iDirX < 0 && iDirY < 0) {
							if (iBallY < iBrickY + (iBrickH / 2)) {
								// Side hit, change also X direction.
								iDirX *= -1;
							}

							if (iBallX < iBrickX + iBrickW
									- (bBall.getWidth() / 2)) {
								// Bottom hit, change also Y direction.
								iDirY *= -1;
							}
						} else if (iDirY > 0) {
							if (iBallY < iBrickY + (bBall.getHeight() / 2)) {
								// Side hit, change also X direction.
								iDirX *= -1;
							}
						}

						break;
					}
				}
			}

			// Collision with the pad
			boolean bPadCX = iBallX > iPadX - bBall.getWidth()
					&& iBallX < iPadX + bPad.getWidth();
			boolean bPadCY = iBallY > iPadY - bBall.getHeight()
					&& iBallY < iPadY + bPad.getHeight();

			if (bPadCX && bPadCY) {
				// Update ball direction.
				if (iDirX > 0) {
					if (iBallY > iPadY - (bPad.getHeight() / 2)) {
						// Side hit, change also X direction.
						iDirX *= -1;
					}

					if (iBallX > iPadX - (bBall.getWidth() / 2)) {
						// Top hit, change also Y direction.
						iDirY *= -1;
					}
				} else if (iDirX < 0) {
					if (iBallY > iPadY - (bPad.getHeight() / 2)) {
						// Side hit, change also X direction.
						iDirX *= -1;
					}

					if (iBallX < iPadX + bPad.getWidth()
							- (bBall.getWidth() / 2)) {
						// Top hit, change also Y direction.
						iDirY *= -1;
					}
				}
			}

			iBallX += iDirX;
			iBallY += iDirY;

			iPadX += iPadSpeed;

			if(iPadX < 0)
				iPadX = 0;
			
			if(iPadX > iCanvasW - bPad.getWidth())
				iPadX = iCanvasW - bPad.getWidth();
		}
	}
}