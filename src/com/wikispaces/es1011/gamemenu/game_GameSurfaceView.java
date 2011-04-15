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


public class game_GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {


    private game_MySurfaceThread thread;
    private game_Ball ball;
    private game_Pad pad;
    private game_BrickMatrix brickMatrix;
    private Rect underRect;
    private long GameTime;
    public int viewHeight;
    public int viewWidth;
    private int brickNum = 20;
    private game_GameState gs;
    private Sensor mAccelerometer;
    private Bitmap hearts, one, two, three, go;
    private game_Sprite2D prova;
    private int i, j, k = 1;
    private boolean flag = true;
    
    public game_GameSurfaceView(Context context, SensorManager mSensorManager, game_GameState gs) {
        super(context);
         this.gs = gs;

        thread = new game_MySurfaceThread(getHolder(), this);
        getHolder().addCallback(this);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        gs.setState(game_GameState.State.READY);
        one = BitmapFactory.decodeResource(getResources(), R.drawable.game_one);
        two = BitmapFactory.decodeResource(getResources(), R.drawable.game_two);
        three = BitmapFactory.decodeResource(getResources(), R.drawable.game_three);
        go = BitmapFactory.decodeResource(getResources(), R.drawable.game_go);

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
                    ball.directionY = -2;
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
                    ball.directionY = -2;
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
                    {
                        gs.setState(game_GameState.State.LOSE);
                    }
                }
                pad.Update(GameTime);


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
                break;

            case LOSE:

                thread.setRunning(false);
                return;


                
            case READY:
                if (j < 5) {
                    canvas.drawColor(Color.BLACK);
                    three = Bitmap.createScaledBitmap(three, three.getWidth() / 2, three.getHeight() / 2, true);
                    prova = new game_Sprite2D(viewWidth, viewHeight);
                    prova.init(three, three.getWidth(), three.getHeight(), (viewWidth - three.getWidth()) / 2, (viewHeight - three.getHeight()) / 2);
                    prova.draw(canvas);
                    SystemClock.sleep(100);
                    j++;
                } else if (i < 5) {

                    canvas.drawColor(Color.BLACK);
                    two = Bitmap.createScaledBitmap(two, two.getWidth() / 2, two.getHeight() / 2, true);
                    prova = new game_Sprite2D(viewWidth, viewHeight);
                    prova.init(two, two.getWidth(), two.getHeight(), (viewWidth - two.getWidth()) / 2, (viewHeight - two.getHeight()) / 2);
                    prova.draw(canvas);
                    SystemClock.sleep(100);
                    i++;
                } else if (k < 5) {

                    canvas.drawColor(Color.BLACK);
                    one = Bitmap.createScaledBitmap(one, one.getWidth() / 2, one.getHeight() / 2, true);
                    prova = new game_Sprite2D(viewWidth, viewHeight);
                    prova.init(one, one.getWidth(), one.getHeight(), (viewWidth - one.getWidth()) / 2, (viewHeight - one.getHeight()) / 2);
                    prova.draw(canvas);
                    SystemClock.sleep(100);
                    k++;

                } else if (flag) {

                    go = Bitmap.createScaledBitmap(go, go.getWidth()/2, go.getHeight()/2, true);
                    prova = new game_Sprite2D(viewWidth, viewHeight);
                    prova.init(go, go.getWidth(), go.getHeight(), (viewWidth - go.getWidth()) / 2, (viewHeight - go.getHeight()) / 2);
                    prova.draw(canvas);
                    
                    flag = false;

                } else {

                    SystemClock.sleep(500);
                    gs.setState(game_GameState.State.RUNNING);
                    j = 1;
                    i = 1;
                    k = 1;
                    flag = true;
                }
                break;
        }

    }

    public void init() {

        //create a graphic
        ball = new game_Ball(viewWidth, viewHeight, this);
        pad = new game_Pad(viewWidth, viewHeight, this);
        brickMatrix = new game_BrickMatrix(viewWidth, viewHeight, this, brickNum);
        underRect = new Rect(0, viewHeight - pad.getHeight(), viewWidth, viewHeight);
    }

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

    private class game_MySurfaceThread extends Thread {

        private SurfaceHolder myThreadSurfaceHolder;
        private game_GameSurfaceView myThreadSurfaceView;
        private boolean myThreadRun = false;

        public game_MySurfaceThread(SurfaceHolder surfaceHolder, game_GameSurfaceView surfaceView) {
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
