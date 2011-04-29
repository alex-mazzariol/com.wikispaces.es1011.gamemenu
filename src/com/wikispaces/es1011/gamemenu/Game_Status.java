package com.wikispaces.es1011.gamemenu;

import android.graphics.Rect;

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
	    public int brickXmin, brickXmax, brickYmin, brickYmax;
	    private Rect brickBox;

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

	    public void setState(Status state) {
	        this.status = state;
	    }

	    public Status getState() {
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
	    
		public Rect[] getPadBoxes(){
		    Rect[] boxArr = {padBox1, padBox2, padBox3, padBox4, padBox5};
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
			this.brickXmin =  brickXmin;
		}

		public int getBrickXmin() {
			return brickXmin;
		}
		
		public void setBrickXmax(int brickXmax) {
			this.brickXmax =  brickXmax;
		}

		public int getBrickXmax() {
			return brickXmax;
		}
		
		public void setBrickYmin(int brickYmin) {
			this.brickYmin =  brickYmin;
		}

		public int getBrickYmin() {
			return brickYmin;
		}
		
		public void setBrickYmax(int brickYmax) {
			this.brickYmax =  brickYmax;
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

	}

