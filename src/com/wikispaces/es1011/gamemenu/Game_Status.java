package com.wikispaces.es1011.gamemenu;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;

public class Game_Status {

	public Status status;
	public int lives;
	public int score;
	public long GameTime;
	public int ballDirectionX;
	public int ballDirectionY;
	public int ballXPos;
	public int ballYPos;
	public Rect ballBox;
	public int padDirectionX;
	public int padDirectionY;
	public int padXPos;
	public int padYPos;
	public Rect padBox1, padBox2, padBox3, padBox4, padBox5;
	public int brickXmin, brickXmax, brickYmin, brickYmax;
	public Rect brickBox;
	public boolean brickVisible;
	public int[] iBricks;

	public void save(Bundle bundle) {
		bundle.putInt("lives", lives);
		bundle.putInt("score", score);
		bundle.putInt("ballDirectionX", ballDirectionX);
		bundle.putInt("ballDirectionY", ballDirectionY);
		bundle.putInt("ballXPos", ballXPos);
		bundle.putInt("ballYPos", ballYPos);
		bundle.putString("ballBox", ballBox.flattenToString());
		bundle.putInt("padDirectionX", padDirectionX);
		bundle.putInt("padDirectionY", padDirectionY);
		bundle.putInt("padXPos", padXPos);
		bundle.putInt("padYPos", padYPos);
		bundle.putStringArray("padBoxes", new String[] {
				padBox1.flattenToString(), padBox2.flattenToString(),
				padBox3.flattenToString(), padBox4.flattenToString(),
				padBox5.flattenToString() });
		bundle.putIntArray("brickValues", new int[] { brickXmin, brickXmax,
				brickYmin, brickYmax });
		bundle.putString("brickBox", brickBox.flattenToString());
		bundle.putBoolean("brickVisible", brickVisible);
		bundle.putIntArray("bricks", iBricks);
	}

	public void load(Bundle bundle) {
		if(bundle == null)
			return;
		
		lives = bundle.getInt("lives");
		score = bundle.getInt("score");
		ballDirectionX = bundle.getInt("ballDirectionX");
		ballDirectionY = bundle.getInt("ballDirectionY");
		ballXPos = bundle.getInt("ballXPos");
		ballYPos = bundle.getInt("ballYPos");
		ballBox = Rect.unflattenFromString(bundle.getString("ballBox"));
		padDirectionX = bundle.getInt("padDirectionX");
		padDirectionY = bundle.getInt("padDirectionY");
		padXPos = bundle.getInt("padXPos");
		padYPos = bundle.getInt("padYPos");
		padBox1 = Rect
				.unflattenFromString(bundle.getStringArray("padBoxes")[0]);
		padBox2 = Rect
				.unflattenFromString(bundle.getStringArray("padBoxes")[1]);
		padBox3 = Rect
				.unflattenFromString(bundle.getStringArray("padBoxes")[2]);
		padBox4 = Rect
				.unflattenFromString(bundle.getStringArray("padBoxes")[3]);
		padBox5 = Rect
				.unflattenFromString(bundle.getStringArray("padBoxes")[4]);
		brickXmin = bundle.getIntArray("brickValues")[0];
		brickXmax = bundle.getIntArray("brickValues")[1];
		brickYmin = bundle.getIntArray("brickValues")[2];
		brickYmax = bundle.getIntArray("brickValues")[3];
		brickBox = Rect.unflattenFromString(bundle.getString("brickBox"));
		brickVisible = bundle.getBoolean("brickVisible");
		iBricks = bundle.getIntArray("bricks");
	}

	public static enum Status {

		LOSE, READY, RUNNING, WIN, PAUSE;
	}

	public Game_Status() {
		resetGame();
	}
	
	public void resetGame()
	{
		score = 0;
		lives = 3;
		ballDirectionX = -5;
		ballDirectionY = -5;
		ballXPos = 100;
		ballDirectionY = 100;
		ballBox = new Rect();
		padBox1 = new Rect();
		padBox2 = new Rect();
		padBox3 = new Rect();
		padBox4 = new Rect();
		padBox5 = new Rect();
	}
}
