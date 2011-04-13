package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
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
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class game_GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private MySurfaceThread thread;
    private game_Ball ball;
    private game_Pad pad;
    private game_BrickMatrix brickMatrix;
    private Rect underRect;
    private long GameTime;
    private Context context;
    private DisplayMetrics metrics;
    public int viewHeight;
    public int viewWidth;
    private int brickNum = 20;
    private game_GameState gs;
    private Sensor mAccelerometer;
    private Button retry;
    private Bitmap hearts;

    public game_GameSurfaceView(Context context, SensorManager mSensorManager, game_GameState gs) {
        super(context);
        this.context = context;

        this.gs = gs;

        thread = new MySurfaceThread(getHolder(), this);
        getHolder().addCallback(this);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        gs.setState(game_GameState.State.RUNNING);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /**
         * Init rectangle
         */
        Paint rectPaint = new Paint();
        Paint textPaint = new Paint();
        game_Sprite2D heart = new game_Sprite2D(viewWidth, viewHeight);
        rectPaint.setColor(Color.LTGRAY);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(pad.getHeight());


        hearts = BitmapFactory.decodeResource(getResources(), R.drawable.game_heart);
        hearts = Bitmap.createScaledBitmap(hearts, pad.getHeight(), pad.getHeight(), true);





        switch (gs.getState()) {
            case RUNNING:

                Rect[] box = pad.getBox();
                if (box[0].intersect(ball.getBox())) {
                    ball.directionX = -4;
                    ball.directionY = -1;
                }
                if (box[1].intersect(ball.getBox())) {
                    ball.directionX = -3;
                    ball.directionY = -2;
                }
                if (box[2].intersect(ball.getBox())) {
                    ball.directionX = -5;
                    ball.directionY = 0;
                }
                if (box[3].intersect(ball.getBox())) {
                    ball.directionX = 3;
                    ball.directionY = -2;
                }
                if (box[4].intersect(ball.getBox())) {
                    ball.directionX = 4;
                    ball.directionY = -1;
                }


                for (int j = 0; j < brickMatrix.getRow(); j++) {
                    for (int i = 0; i < brickMatrix.getColumn(); i++) {
                        if (brickMatrix.getBrick(i, j).isVisible() && ball.getBox().intersect(brickMatrix.getBrick(i, j).getBox())) {
                            brickMatrix.getBrick(i, j).Update(GameTime, false);

                            if (ball.getBox().intersect(brickMatrix.getBrick(i, j).getBox().left, brickMatrix.getBrick(i, j).getBox().top, brickMatrix.getBrick(i, j).getBox().left + 1, brickMatrix.getBrick(i, j).getBox().bottom)
                                    || ball.getBox().intersect(brickMatrix.getBrick(i, j).getBox().right - 1, brickMatrix.getBrick(i, j).getBox().top, brickMatrix.getBrick(i, j).getBox().right, brickMatrix.getBrick(i, j).getBox().bottom)) {
                                ball.directionX = -ball.directionX;
                            } else {
                                ball.directionY = -ball.directionY;
                            }

                            gs.setScore(100);
                        }

                    }
                }
                if (!ball.Update(GameTime)) {
                    ball = new game_Ball(viewWidth, viewHeight, this);
                    if (gs.getLives() > 0) {
                        gs.setLives(gs.getLives() - 1);
                    } else //lose:
                        gs.setState(game_GameState.State.LOSE);
                }
                pad.Update(GameTime);


                /**
                 * Draw the sprites in the canvas
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
                break;

            case LOSE:


               /*final Button button = (Button) findViewById(R.id.close);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
             }
         });
*/
                break;
        }

    }

    private void init() {

        //create a graphic
        ball = new game_Ball(viewWidth, viewHeight, this);
        pad = new game_Pad(viewWidth, viewHeight, this);
        brickMatrix = new game_BrickMatrix(viewWidth, viewHeight, this, brickNum);
        underRect = new Rect(0, viewHeight - pad.getHeight(), viewWidth, viewHeight);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    switch (action) {
    case (MotionEvent.ACTION_DOWN): // Touch screen pressed

    break;
    case (MotionEvent.ACTION_UP): // Touch screen touch ended
    gs.setState(GameState.State.RUNNING);
    break;
    case (MotionEvent.ACTION_MOVE): // Contact has moved across screen
    break;
    case (MotionEvent.ACTION_CANCEL): // Touch event cancelled
    break;
    }
    return super.onTouchEvent(event);
    }*/
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        viewWidth = holder.getSurfaceFrame().width();
        viewHeight = holder.getSurfaceFrame().height();
        init();
        thread.setRunning(true);
        thread.start();
    }

    @Override
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

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ORIENTATION) {
            return;
        }
        pad.directionX = (int) -event.values[2];
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private class MySurfaceThread extends Thread {

        private SurfaceHolder myThreadSurfaceHolder;
        private game_GameSurfaceView myThreadSurfaceView;
        private boolean myThreadRun = false;

        public MySurfaceThread(SurfaceHolder surfaceHolder, game_GameSurfaceView surfaceView) {
            myThreadSurfaceHolder = surfaceHolder;
            myThreadSurfaceView = surfaceView;
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
}
