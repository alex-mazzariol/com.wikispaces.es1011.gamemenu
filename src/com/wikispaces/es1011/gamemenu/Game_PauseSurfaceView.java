package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_PauseSurfaceView extends SurfaceView implements IGameSurface {

	private int viewHeight, viewWidth;
	private Game_Status gs;

	public Game_PauseSurfaceView(Context context, Game_Status gs) {
		super(context);
		this.gs = gs;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint textPaint = new Paint();

		textPaint.setColor(Color.BLUE);
		textPaint.setTextSize(viewHeight/10);
		canvas.drawText("YOUR FINAL SCORE : " + gs.getScore(), 0, viewHeight, textPaint);
	}

	public void initDimension(int width, int height) {

		this.viewHeight = height;
		this.viewWidth = width;
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

}