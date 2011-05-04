package com.wikispaces.es1011.gamemenu;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;

public class Game_Status {

	public Status status;
	private int lives;
	private int score;
	private long GameTime;
	private int ballDirectionX;
	private int ballDirectionY;
	private int ballXPos;
	private int ballYPos;
	private Rect ballBox;
	private int padDirectionX;
	private int padDirectionY;
	private int padXPos;
	private int padYPos;
	private Rect padBox1, padBox2, padBox3, padBox4, padBox5;
	private int brickXmin, brickXmax, brickYmin, brickYmax;
	private Rect brickBox;
	private boolean brickVisible;
	private Bundle bundle;

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
		load(bundle);
	}

	public Bundle getBundle() {
		return save();
	}

	private Bundle save() {
		bundle.putInt("lives", getLives());
		bundle.putInt("score", getScore());
		bundle.putInt("ballDirectionX", getBallDirectionX());
		bundle.putInt("ballDirectionY", getBallDirectionY());
		bundle.putInt("ballXPos", getBallXPos());
		bundle.putInt("ballYPos", getBallYPos());
		bundle.putString("ballBox", ballBox.flattenToString());
		bundle.putInt("padDirectionX", getPadDirectionX());
		bundle.putInt("padDirectionY", getPadDirectionY());
		bundle.putInt("padXPos", getPadXPos());
		bundle.putInt("padYPos", getPadYPos());
		bundle.putStringArray("padBoxes", new String[] {
				padBox1.flattenToString(), padBox2.flattenToString(),
				padBox3.flattenToString(), padBox4.flattenToString(),
				padBox5.flattenToString() });
		bundle.putIntArray("brickValues", new int[] { brickXmin, brickXmax,
				brickYmin, brickYmax });
		bundle.putString("brickBox", brickBox.flattenToString());
		bundle.putBoolean("brickVisible", brickVisible);
		return bundle;
	}

	private void load(Bundle bundle) {
		setLives(bundle.getInt("lives"));
		setScore(bundle.getInt("score"));
		setBallDirectionX(bundle.getInt("ballDirectionX"));
		setBallDirectionY(bundle.getInt("ballDirectionY"));
		setBallXPos(bundle.getInt("ballXPos"));
		setBallYPos(bundle.getInt("ballYPos"));
		ballBox = Rect.unflattenFromString(bundle.getString("ballBox"));
		setPadDirectionX(bundle.getInt("padDirectionX"));
		setPadDirectionY(bundle.getInt("padDirectionY"));
		setPadXPos(bundle.getInt("padXPos"));
		setPadYPos(bundle.getInt("padYPos"));
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
		setBrickVisible(bundle.getBoolean("brickVisible"));
	}

	public static enum Status {

		LOSE, READY, RUNNING, WIN, PAUSE;
	}

	public void setGameTime(long gameTime) {
		GameTime = gameTime;
	}

	public long getGameTime() {
		return GameTime;
	}

	public Game_Status() {
		this.score = 0;
		this.lives = 3;
		this.ballDirectionX = -5;
		this.ballDirectionY = -5;
		this.ballXPos = 100;
		this.ballDirectionY = 100;
		this.ballBox = new Rect();
		this.padBox1 = new Rect();
		this.padBox2 = new Rect();
		this.padBox3 = new Rect();
		this.padBox4 = new Rect();
		this.padBox5 = new Rect();
	}

	public void setScore(int points) {
		score += points;
	}

	public int getScore() {
		return score;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setBallDirectionX(int ballDirectionX) {
		this.ballDirectionX = ballDirectionX;
	}

	public int getBallDirectionX() {
		return ballDirectionX;
	}

	public void setBallDirectionY(int ballDirectionY) {
		this.ballDirectionY = ballDirectionY;
	}

	public int getBallDirectionY() {
		return ballDirectionY;
	}

	public void setBallXPos(int ballXPos) {
		this.ballXPos = ballXPos;
	}

	public int getBallXPos() {
		return ballXPos;
	}

	public void setBallYPos(int ballYPos) {
		this.ballYPos = ballYPos;
	}

	public int getBallYPos() {
		return ballYPos;
	}

	public Rect getBallBox() {
		return ballBox;
	}

	public void setBallBox(Rect ballBox) {
		this.ballBox = ballBox;
	}

	public Rect[] getPadBoxes() {
		Rect[] boxArr = { padBox1, padBox2, padBox3, padBox4, padBox5 };
		return boxArr;
	}

	public Rect getPadBox1() {
		return padBox1;
	}

	public void setPadBox1(Rect padBox1) {
		this.padBox1 = padBox1;
	}

	public Rect getPadBox2() {
		return padBox2;
	}

	public void setPadBox2(Rect padBox2) {
		this.padBox2 = padBox2;
	}

	public Rect getPadBox3() {
		return padBox3;
	}

	public void setPadBox3(Rect padBox3) {
		this.padBox3 = padBox3;
	}

	public Rect getPadBox4() {
		return padBox4;
	}

	public void setPadBox4(Rect padBox4) {
		this.padBox4 = padBox4;
	}

	public Rect getPadBox5() {
		return padBox5;
	}

	public void setPadBox5(Rect padBox5) {
		this.padBox5 = padBox5;
	}

	public void setPadDirectionX(int padDirectionX) {
		this.padDirectionX = padDirectionX;
	}

	public int getPadDirectionX() {
		return padDirectionX;
	}

	public void setPadDirectionY(int padDirectionY) {
		this.padDirectionY = padDirectionY;
	}

	public int getPadDirectionY() {
		return padDirectionY;
	}

	public void setPadXPos(int padXPos) {
		this.padXPos = padXPos;
	}

	public int getPadXPos() {
		return padXPos;
	}

	public void setPadYPos(int padYPos) {
		this.padYPos = padYPos;
	}

	public int getPadYPos() {
		return padYPos;
	}

	public void setBrickXmin(int brickXmin) {
		this.brickXmin = brickXmin;
	}

	public int getBrickXmin() {
		return brickXmin;
	}

	public void setBrickXmax(int brickXmax) {
		this.brickXmax = brickXmax;
	}

	public int getBrickXmax() {
		return brickXmax;
	}

	public void setBrickYmin(int brickYmin) {
		this.brickYmin = brickYmin;
	}

	public int getBrickYmin() {
		return brickYmin;
	}

	public void setBrickYmax(int brickYmax) {
		this.brickYmax = brickYmax;
	}

	public int getBrickYmax() {
		return brickYmax;
	}

	public void setBrickBox(Rect brickBox) {
		this.brickBox = brickBox;
	}

	public Rect getBrickBox() {
		return brickBox;
	}

	public void setBrickVisible(boolean brickVisible) {
		this.brickVisible = brickVisible;
	}

	public boolean isBrickVisible() {
		return brickVisible;
	}

}
