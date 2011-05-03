package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_GameSurfaceView extends SurfaceView implements IGameSurface {

	private Game_Status gs;
	private Bitmap hearts;
	private int viewHeight, viewWidth;
	private Game_Ball ball;
	private Game_Pad pad;
	private Game_BrickMatrix brickMatrix;
	private Rect underRect;
	

	public Game_GameSurfaceView(Context context) {
		super(context);
}

	@Override
	protected void onDraw(Canvas canvas) {

		/**
		 * Init rectangle
		 */
		Paint rectPaint = new Paint();
		Paint textPaint = new Paint();
		rectPaint.setColor(Color.LTGRAY);
		textPaint.setColor(Color.BLUE);
		textPaint.setTextSize(viewHeight / 20);

		hearts = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_heart);
		hearts = Bitmap.createScaledBitmap(hearts, viewHeight / 20, viewHeight / 20, true);

		/**
		 * Draw the sprite in the canvas
		 * 
		 */
		canvas.drawColor(Color.DKGRAY);
		canvas.drawRect(underRect, rectPaint);
		canvas.drawText("SCORE : " + gs.getScore(), 0, viewHeight, textPaint);

		for (int i = 0; i < gs.getLives(); i++) {
			canvas.drawBitmap(hearts, new Rect(hearts.getWidth(), hearts.getHeight(), viewWidth
					- hearts.getWidth() * i, viewHeight - hearts.getHeight()), new Rect(hearts.getWidth(), hearts.getHeight(), viewWidth
							- hearts.getWidth() * i, viewHeight - hearts.getHeight()), null);
		}

		ball.draw(canvas);
		pad.draw(canvas);
		/*for (int j = 0; j < brickMatrix.getRow(); j++) {
			for (int i = 0; i < brickMatrix.getColumn(); i++) {
				if (brickMatrix.getBrick(i, j).isVisible()) {
					brickMatrix.getBrick(i, j).draw(canvas);
				}
			}
		}*/

	}

	public void viewUpdate() {
		viewUpdateHandler.sendEmptyMessage(0);
	}

	private Handler viewUpdateHandler = new Handler() {

		public void handleMessage(Message msg) {
			Canvas c = null;
			SurfaceHolder sH = getHolder();
			try {
				
				c = sH.lockCanvas(null);
				synchronized (sH) {
					onDraw(c);
				}
			} finally {
				if (c != null) {
					sH.unlockCanvasAndPost(c);
				}
			}
		}
	};

	public void initDimension(int width, int height) {
		
		this.viewHeight = height;
		this.viewWidth = width;
	}
	
	public void initSprites(Game_Status gs, 
			Game_Ball ball, Game_Pad pad, Game_BrickMatrix brickMatrix,
			Rect underRect){
		this.gs = gs;
		this.ball = ball;
		this.pad = pad;
		this.brickMatrix = brickMatrix;
		this.underRect = underRect;
	}
}