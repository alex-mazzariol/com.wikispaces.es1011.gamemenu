package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

public class Game_ReadySurfaceView extends SurfaceView implements IGameSurface {

	private int viewWidth;
	private int viewHeight;
	
	public Game_ReadySurfaceView(Context context, Game_Status gs) {
		super(context);
		final TextView tv = new TextView(context);
		tv.setText("SCORE" + gs.getScore());
		tv.setHeight(80);
		Button bu = new Button(context);
		bu.setText("Continue");
		bu.setWidth(100);
		bu.setHeight(80);

		/*
		 * bu.setOnClickListener(new View.OnClickListener() {
		 * 
		 * public void onClick(View v) {
		 * 
		 * gsw.init(); gs.setState(GameState.State.READY); setContentView(gsw);
		 * 
		 * } });
		 * 
		 * 
		 * 
		 * gamelayout.addView(tv); gamelayout.addView(bu); setContentView(bu);
		 */
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
}
