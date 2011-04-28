package com.wikispaces.es1011.gamemenu;

	public class Game_Status {

	    public Status status;
	    private int lives;
	    private int score;
	    private long GameTime;

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
	}

