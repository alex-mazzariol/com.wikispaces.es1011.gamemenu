package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

public class game_Ball extends game_Sprite2D {

    private Bitmap ball;
    private Rect box;
    public int directionX = -5;
    public int directionY = -5;

    public game_Ball(int CanvasW, int CanvasH, SurfaceView sw) {
        super(CanvasW, CanvasH);

        ball = BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_sprite_ball);
        /**
         * Scale the ball
         */
        ball = Bitmap.createScaledBitmap(ball, CanvasW/20, CanvasW/20, true);
        init(ball, ball.getWidth(), ball.getHeight(), CanvasW - 5 * ball.getWidth(), CanvasH - 3 *  ball.getHeight());
        box = new Rect(this.getXPos(),this.getYPos(),this.getXPos() + ball.getWidth(),this.getYPos() +  ball.getHeight());



    }

    public boolean Update(long GameTime) {
        if (GameTime - mFrameTimer > speed) {
            mFrameTimer = GameTime;

            if (mXPos <= 0) {
                mXPos = 1;
                directionX = -directionX;
            }

            if (mXPos >= CanvasW - 1) {
                mXPos = CanvasW - 1;
                directionX = -directionX;
            }

            if (mYPos <= 0) {

                mYPos = 1;
                directionY = -directionY;
            }

            if (mYPos >= CanvasH -  ball.getHeight()) {
                return false;
            }


            mXPos = mXPos + directionX % CanvasW;
            mYPos = mYPos + directionY % CanvasH;
            box.set(this.getXPos(),this.getYPos(),this.getXPos() + ball.getWidth(),this.getYPos() +  ball.getHeight());


        }
        return true;
    }

    public Rect getBox(){
    return box;}
}

