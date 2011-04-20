package com.wikispaces.es1011.gamemenu;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Game_GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private long GameTime;
	private Game_GameState gs;
    private Bitmap hearts;
    private int viewHeight, viewWidth;
    private Game_Ball ball;
    private Game_Pad pad;
    private Game_BrickMatrix brickMatrix;
    private Rect underRect;
    private Game_SurfaceThread thread;
    
    
    public Game_GameSurfaceView(Context context,int viewHeight,int viewWidth, Game_Ball ball,Game_Pad pad,Game_BrickMatrix brickMatrix,
    		Rect underRect,
    		Game_SurfaceThread thread) {
        super(context);
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.ball = ball;
        this.pad = pad;
        this.brickMatrix = brickMatrix;
        this.underRect = underRect;
        this.thread = thread;
}

    	@Override
    	protected void onDraw(Canvas canvas) {

        /**
         * Init rectangle
         */
        Paint rectPaint = new Paint();
        Paint textPaint = new Paint();
        Game_Sprite2D heart = new Game_Sprite2D(viewWidth, viewHeight);
        rectPaint.setColor(Color.LTGRAY);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(pad.getHeight());


        hearts = BitmapFactory.decodeResource(getResources(), R.drawable.game_heart);
        hearts = Bitmap.createScaledBitmap(hearts, pad.getHeight(), pad.getHeight(), true);

                /**
                 * Draw the sprite in the canvas
                 *
                 */
                canvas.drawColor(Color.DKGRAY);
                canvas.drawRect(underRect, rectPaint);
                canvas.drawText("SCORE : " + gs.getScore(), 0, viewHeight, textPaint);

                for (int i = 0; i < gs.getLives(); i++) {
                    heart.init(hearts, hearts.getWidth(), hearts.getHeight(), viewWidth - hearts.getWidth() * i, viewHeight - hearts.getHeight());
                    heart.draw(canvas);
                }

                ball.draw(canvas);
                pad.draw(canvas);
                for (int j = 0; j < brickMatrix.getRow(); j++) {
                    for (int i = 0; i < brickMatrix.getColumn(); i++) {
                        if (brickMatrix.getBrick(i, j).isVisible()) {
                            brickMatrix.getBrick(i, j).draw(canvas);
                        }
                    }
                }

        }

    

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        
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

