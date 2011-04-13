package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class game_Sprite2D {

    protected Bitmap mSprite;
    protected int mXPos;
    protected int mYPos;
    protected Rect mSRectangle;
    protected int mNoOfFrames;
    protected int mCurrentFrame;
    protected long mFrameTimer;
    protected int mSpriteHeight;
    protected int mSpriteWidth;
    protected int CanvasW, CanvasH;
    protected int WidthHieghtRatio;
    public final int speed = 5;
    public final int brickNum = 30;


    public game_Sprite2D(int aCanvasW, int aCanvasH) {
        mSRectangle = new Rect(0, 0, 0, 0);
        mFrameTimer = 0;
        mCurrentFrame = 0;
        WidthHieghtRatio =  aCanvasW / aCanvasH;
        CanvasW = aCanvasW;
        CanvasH = aCanvasH;
    }

    public void init(Bitmap theBitmap, int Width, int Height, int xPos, int yPos) {
        mSprite = theBitmap;
        mSpriteHeight = Height;
        mSpriteWidth = Width;
        mSRectangle.top = 0;
        mSRectangle.bottom = mSpriteHeight;
        mSRectangle.left = 0;
        mSRectangle.right = mSpriteWidth;
        mXPos = xPos;
        mYPos = yPos;
        CanvasW -= Width;
        CanvasH -= Height;

    }

    public void draw(Canvas canvas) {
        Rect dest = new Rect(getXPos(), getYPos(), getXPos() + mSpriteWidth,
                getYPos() + mSpriteHeight);
        /**
         * Scale the bitmap
         */
        canvas.drawBitmap(mSprite, mSRectangle, dest, null);
    }

    public int getYPos() {
        return mYPos;
    }

    public int getXPos() {
        return mXPos;
    }
}
