package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.SurfaceView;

public class Game_Pad extends Game_Sprite2D {

    public int directionX = 0;
    public int directionY = 0;
    private Rect box1,box2,box3,box4,box5;
    private Bitmap pad;

    public Game_Pad(int CanvasW, int CanvasH, SurfaceView sw) {
        super(CanvasW, CanvasH);
        pad = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_sprite_pad), CanvasW / 4, CanvasH / 20, true);
        init(pad, pad.getWidth(), pad.getHeight(), 0, CanvasH - 2 * pad.getHeight());
        box1 = new Rect(this.getXPos(),this.getYPos(),this.getXPos() + pad.getWidth()*1/5,this.getYPos() +  pad.getHeight());
        box2 = new Rect(this.getXPos()+pad.getWidth()*1/5,this.getYPos(),this.getXPos() + pad.getWidth()*2/5,this.getYPos() +  pad.getHeight());
        box3 = new Rect(this.getXPos()+pad.getWidth()*2/5,this.getYPos(),this.getXPos() + pad.getWidth()*3/5,this.getYPos() +  pad.getHeight());
        box4 = new Rect(this.getXPos()+pad.getWidth()*3/5,this.getYPos(),this.getXPos() + pad.getWidth()*4/5,this.getYPos() +  pad.getHeight());
        box5 = new Rect(this.getXPos()+pad.getWidth()*4/5,this.getYPos(),this.getXPos() + pad.getWidth(),this.getYPos() +  pad.getHeight());

    }

    public void Update(long GameTime) {
        if (GameTime - mFrameTimer > speed) {
            mFrameTimer = GameTime;
            if (mXPos <= 0 || mXPos >= CanvasW - pad.getWidth()) {
                directionX = -directionX;
            }
            mXPos = java.lang.Math.abs(mXPos + directionX) % CanvasW;
            box1.set(this.getXPos(),this.getYPos(),this.getXPos() + pad.getWidth()*1/5,this.getYPos() +  pad.getHeight());
            box2.set(this.getXPos()+pad.getWidth()*1/5,this.getYPos(),this.getXPos() + pad.getWidth()*2/5,this.getYPos() +  pad.getHeight());
            box3.set(this.getXPos()+pad.getWidth()*2/5,this.getYPos(),this.getXPos() + pad.getWidth()*3/5,this.getYPos() +  pad.getHeight());
            box4.set(this.getXPos()+pad.getWidth()*3/5,this.getYPos(),this.getXPos() + pad.getWidth()*4/5,this.getYPos() +  pad.getHeight());
            box5.set(this.getXPos()+pad.getWidth()*4/5,this.getYPos(),this.getXPos() + pad.getWidth(),this.getYPos() +  pad.getHeight());
            directionX = 0;
        }
    }

    public Rect[] getBox(){
    Rect[] boxArr = {box1, box2,box3,box4,box5};
    return boxArr;

    }

    public int getHeight() {
        return pad.getHeight();
    }
}
