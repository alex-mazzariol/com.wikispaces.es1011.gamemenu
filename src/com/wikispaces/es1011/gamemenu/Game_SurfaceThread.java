package com.wikispaces.es1011.gamemenu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_SurfaceThread extends Thread {

    private boolean myThreadRun = false;
    private long GameTime;
    private IUpdatable update;
    

    public Game_SurfaceThread() {
    
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        while (myThreadRun) {
            Canvas c = null;
            try {
                setGameTime(System.currentTimeMillis());
                c = myThreadSurfaceHolder.lockCanvas(null);
                synchronized (myThreadSurfaceHolder) {
                    myThreadSurfaceView.onDraw(c);
                }
            } finally {
                if (c != null) {
                    myThreadSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

	public void setGameTime(long gameTime) {
		GameTime = gameTime;
	}

	public long getGameTime() {
		return GameTime;
	}
}

