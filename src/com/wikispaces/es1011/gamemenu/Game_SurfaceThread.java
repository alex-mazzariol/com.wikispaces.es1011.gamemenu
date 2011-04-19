package com.wikispaces.es1011.gamemenu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class Game_SurfaceThread extends Thread {

    private SurfaceHolder myThreadSurfaceHolder;
    private Game_GameSurfaceView myThreadSurfaceView;
    private boolean myThreadRun = false;
    private long GameTime;

    public Game_SurfaceThread(SurfaceHolder surfaceHolder, Game_GameSurfaceView surfaceView) {
        myThreadSurfaceHolder = surfaceHolder;
        myThreadSurfaceView = surfaceView;
        this.GameTime =GameTime;
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        while (myThreadRun) {
            Canvas c = null;
            try {
                GameTime = System.currentTimeMillis();
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
}

