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
	private Game_Status gST;

	public Game_View(Context context, Game_Status gs) {
		super(context);
		mc = context;
		gST = gs;

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
		gThread.doStart();
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
		private int iOffsetXBricks;

		private SurfaceHolder shHolder;
		private int iBrickW, iBrickH;
		private int iPadY;

		private long lTime;

		public Game_Thread(SurfaceHolder sh) {
			shHolder = sh;

			setSurfaceSize(sh.getSurfaceFrame().width(), sh.getSurfaceFrame()
					.height());
		}

		public void doStart() {
			synchronized (shHolder) {
				lTime = System.currentTimeMillis() + 100;
				setStatus(eStatus.Running);
			}
			start();
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
						.createScaledBitmap(BitmapFactory.decodeResource(mc
								.getResources(), R.drawable.game_brick1),
								iBrickW, iBrickH, true);
				bBrick[1] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(mc
								.getResources(), R.drawable.game_brick2),
								iBrickW, iBrickH, true);
				bBrick[2] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(mc
								.getResources(), R.drawable.game_brick3),
								iBrickW, iBrickH, true);
				bBrick[3] = Bitmap
						.createScaledBitmap(BitmapFactory.decodeResource(mc
								.getResources(), R.drawable.game_brick4),
								iBrickW, iBrickH, true);

				gST.iDefaultX = (iCanvasW - bBall.getWidth()) / 2;
				gST.iDefaultY = (iCanvasH - bBall.getHeight()) / 2;
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

			c.drawBitmap(bBall, gST.iBallX, gST.iBallY, null);
			c.drawBitmap(bPad, gST.iPadX, iPadY, null);

			for (int i = 0; i < gST.iLevel; i++) {
				if (gST.iBricks[i] == 0)
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
			c.drawText("Level " + Integer.toString(gST.iLevel - 13)
					+ ", lives " + gST.iLives, 5, 17, pBlue);
		}

		private void updateGame() {

			if (gST.iBallX > (iCanvasW - bBall.getWidth() - 1))
				gST.iDirX *= -1;

			if (gST.iBallX < 1)
				gST.iDirX *= -1;

			if (gST.iBallY < 21)
				gST.iDirY *= -1;

			if (gST.iBallY > (iCanvasH - bBall.getHeight() - 1)) {
				if (gST.iLives > 0) {
					gST.loseLife();
				} else {
					// Game over
					gST.resetGame();
					return;
				}
			}

			// Collisions
			for (int i = 0; i < gST.iLevel; i++) {
				if (gST.iBricks[i] == 0) {
					int iBrickX = iOffsetXBricks + (i % 7) * iBrickW;
					int iBrickY = (int) Math.floor(i / 7) * iBrickH + 20;
					// Check collision
					boolean bXCollision = gST.iBallX > iBrickX
							- bBall.getWidth()
							&& gST.iBallX < iBrickX + iBrickW;
					boolean bYCollision = gST.iBallY > iBrickY
							- bBall.getHeight()
							&& gST.iBallY < iBrickY + iBrickH;
					if (bXCollision && bYCollision) {
						// Collided!
						gST.iBricks[i] = 1;
						gST.iBricksHit++;

						// Check if last brick
						if (gST.iBricksHit == gST.iLevel) {
							// Level cleared
							gST.resetLevel(gST.iLevel - 13);
							return;
						}

						// Update ball direction.
						if (gST.iDirX > 0 && gST.iDirY < 0) {
							if (gST.iBallY < iBrickY + (iBrickH / 2)) {
								// Side hit, change also X direction.
								gST.iDirX *= -1;
							}

							if (gST.iBallX > iBrickX - (bBall.getWidth() / 2)) {
								// Bottom hit, change also Y direction.
								gST.iDirY *= -1;
							}
						} else if (gST.iDirX < 0 && gST.iDirY < 0) {
							if (gST.iBallY < iBrickY + (iBrickH / 2)) {
								// Side hit, change also X direction.
								gST.iDirX *= -1;
							}

							if (gST.iBallX < iBrickX + iBrickW
									- (bBall.getWidth() / 2)) {
								// Bottom hit, change also Y direction.
								gST.iDirY *= -1;
							}
						} else if (gST.iDirY > 0) {
							if (gST.iBallY < iBrickY + (bBall.getHeight() / 2)) {
								// Side hit, change also X direction.
								gST.iDirX *= -1;
							}
						}

						break;
					}
				}
			}

			// Collision with the pad
			boolean bPadCX = gST.iBallX > gST.iPadX - bBall.getWidth()
					&& gST.iBallX < gST.iPadX + bPad.getWidth();
			boolean bPadCY = gST.iBallY > iPadY - bBall.getHeight()
					&& gST.iBallY < iPadY + bPad.getHeight();

			if (bPadCX && bPadCY) {
				// Update ball direction.
				if (gST.iDirX > 0) {
					if (gST.iBallY > iPadY - (bPad.getHeight() / 2)) {
						// Side hit, change also X direction.
						gST.iDirX *= -1;
					}

					if (gST.iBallX > gST.iPadX - (bBall.getWidth() / 2)) {
						// Top hit, change also Y direction.
						gST.iDirY *= -1;
					}
				} else if (gST.iDirX < 0) {
					if (gST.iBallY > iPadY - (bPad.getHeight() / 2)) {
						// Side hit, change also X direction.
						gST.iDirX *= -1;
					}

					if (gST.iBallX < gST.iPadX + bPad.getWidth()
							- (bBall.getWidth() / 2)) {
						// Top hit, change also Y direction.
						gST.iDirY *= -1;
					}
				}

				// Add pad speed
				gST.iDirX += gST.iPadSpeed / 2;

				if (gST.iDirX > 8)
					gST.iDirX = 8;

				if (gST.iDirX < -8)
					gST.iDirX = -8;

			}

			gST.iBallX += gST.iDirX;
			gST.iBallY += gST.iDirY;

			gST.iPadX += gST.iPadSpeed;

			if (gST.iPadX < 0)
				gST.iPadX = 0;

			if (gST.iPadX > iCanvasW - bPad.getWidth())
				gST.iPadX = iCanvasW - bPad.getWidth();
		}
	}
}