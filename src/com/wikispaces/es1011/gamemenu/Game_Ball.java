package com.wikispaces.es1011.gamemenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class Game_Ball implements IGameDrawable {

	private Bitmap ball;
	private Game_Status gs;


	public Game_Ball(int CanvasW, int CanvasH, SurfaceView sw, Game_Status gs) {

		this.gs = gs;
		ball = BitmapFactory.decodeResource(sw.getResources(),
				R.drawable.game_sprite_ball);
		ball = Bitmap.createScaledBitmap(ball, CanvasW / 20, CanvasW / 20, true);
		
		
		gs.setBallBox(new Rect(gs.getBallXPos(), gs.getBallYPos(), gs.getBallXPos()
				+ ball.getWidth(), gs.getBallYPos() + ball.getHeight()));
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(ball,gs.getBallBox(),gs.getBallBox() , null);
		
	}

}