package com.wikispaces.es1011.gamemenu;

public class Game_Status {
	public int iBricks[];
	public int iBricksHit;
	public int iPadSpeed = 0;
	public int iPadX, iBallX, iBallY, iLevel;
	public int iDirX = 3, iDirY = 3;
	public int iLives;
	public int iDefaultX = 50, iDefaultY = 50;

	public Game_Status() {
		resetGame();
	}
	
	public void resetLevel(int iL){
		iLevel = 14 + iL;
		iBricksHit = 0;
		iBricks = new int[iLevel];
		
		iPadX = 0;
		iBallX = iDefaultX;
		iBallY = iDefaultY;
		iDirX = 3;
		iDirY = 3;
	}
	
	public void resetGame() {
		resetLevel(0);
		iLives = 3;
	}
	
	public void loseLife() {
		iBallX = iDefaultX;
		iBallY = iDefaultY;
		iLives--;
	}
}
