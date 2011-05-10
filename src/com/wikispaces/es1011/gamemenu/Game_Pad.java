package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class Game_Pad implements IGameDrawable  {

    private Bitmap pad;
    private Game_Status gs;

    //TODO Cancellare questa classe
    public Game_Pad(int CanvasW, int CanvasH, SurfaceView sw, Game_Status gs) {
    /*	this.gs = gs;
        pad = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(sw.getResources(), R.drawable.game_sprite_pad), CanvasW / 4, CanvasH / 20, true);
        
        
        
        gs.setPadBox1(new Rect(gs.getPadXPos(),gs.getPadYPos(),gs.getPadXPos() + pad.getWidth()*1/5,gs.getPadYPos() +  pad.getHeight()));
        gs.setPadBox2(new Rect(gs.getPadXPos() + pad.getWidth()*1/5,gs.getPadYPos(),gs.getPadXPos() + pad.getWidth()*2/5,gs.getPadYPos() +  pad.getHeight()));
        gs.setPadBox3(new Rect(gs.getPadXPos() + pad.getWidth()*2/5,gs.getPadYPos(),gs.getPadXPos() + pad.getWidth()*3/5,gs.getPadYPos() +  pad.getHeight()));
        gs.setPadBox4(new Rect(gs.getPadXPos() + pad.getWidth()*3/5,gs.getPadYPos(),gs.getPadXPos() + pad.getWidth()*4/5,gs.getPadYPos() +  pad.getHeight()));
        gs.setPadBox5(new Rect(gs.getPadXPos() + pad.getWidth()*4/5,gs.getPadYPos(),gs.getPadXPos() + pad.getWidth(),gs.getPadYPos() +  pad.getHeight()));
        */
    }

    public void draw(Canvas canvas) {
        /*canvas.drawBitmap(pad,gs.getPadBox1(),gs.getPadBox1() , null);
        canvas.drawBitmap(pad,gs.getPadBox2(),gs.getPadBox2() , null);
        canvas.drawBitmap(pad,gs.getPadBox3(),gs.getPadBox3() , null);
        canvas.drawBitmap(pad,gs.getPadBox4(),gs.getPadBox4() , null);
        canvas.drawBitmap(pad,gs.getPadBox5(),gs.getPadBox5() , null);*/
    }

}