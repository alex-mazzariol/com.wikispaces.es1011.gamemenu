package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Game_ReadySurfaceView extends SurfaceView implements IGameSurface {

	private int viewWidth;
	private int viewHeight;
	private Game_Status gs;
	private Bitmap one, two, three, go;
	private int j,i,k;
	private boolean flag;
	private Rect rect;
	
	public Game_ReadySurfaceView(Context context, Game_Status gs) {
		super(context);
		this.gs=gs;
		one = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_one);
		two = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_two);
		three = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_three);
		go = BitmapFactory.decodeResource(getResources(),
				R.drawable.game_go);
	}

	public void onDraw(Canvas canvas){
		if (j < 5) {
            canvas.drawColor(Color.BLACK);
            one = Bitmap.createScaledBitmap(one, one.getWidth() / 2, one.getHeight() / 2, true);
            rect = new Rect(viewWidth/2-one.getWidth()/2, viewHeight/2-one.getHeight()/2, viewWidth/2 + one.getWidth()/2, viewHeight/2 +one.getHeight()/2);
            canvas.drawBitmap(one, rect, rect, null); 
            
            SystemClock.sleep(100);
            j++;
        } else if (i < 5) {

            canvas.drawColor(Color.BLACK);
            two = Bitmap.createScaledBitmap(two, two.getWidth() / 2, two.getHeight() / 2, true);
            rect = new Rect(viewWidth/2-two.getWidth()/2, viewHeight/2-two.getHeight()/2, viewWidth/2 + two.getWidth()/2, viewHeight/2 +two.getHeight()/2);
            canvas.drawBitmap(two, rect, rect, null); 
            SystemClock.sleep(100);
            i++;
        } else if (k < 5) {

            canvas.drawColor(Color.BLACK);
            three = Bitmap.createScaledBitmap(three, three.getWidth() / 2, three.getHeight() / 2, true);
            rect = new Rect(viewWidth/2-three.getWidth()/2, viewHeight/2-three.getHeight()/2, viewWidth/2 + three.getWidth()/2, viewHeight/2 +three.getHeight()/2);
            canvas.drawBitmap(three, rect, rect, null); 
            SystemClock.sleep(100);
            k++;

        } else if (flag) {

            go = Bitmap.createScaledBitmap(go, go.getWidth()/2, go.getHeight()/2, true);
            
            rect = new Rect(viewWidth/2-go.getWidth()/2, viewHeight/2-go.getHeight()/2, viewWidth/2 + go.getWidth()/2, viewHeight/2 +go.getHeight()/2);
            canvas.drawBitmap(go, rect, rect, null); 
            
            flag = false;

        } else {

            SystemClock.sleep(500);
            gs.setStatus(Game_Status.Status.RUNNING);
            j = 1;
            i = 1;
            k = 1;
            flag = true;
        }
				
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