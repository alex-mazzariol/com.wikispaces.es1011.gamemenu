package com.wikispaces.es1011.gamemenu;

/**
 * Status keeping object. Used to persist game status on view resets.
 * 
 * @author Eugenio Enrico
 *
 */
public class Game_Status {
	public int iBricks[];
	public int iBricksHit;
	public int iPadSpeed = 0;
	public int iPadX, iBallX, iBallY, iLevel;
	public int iDirX = 3, iDirY = 3;
	public int iLives;
	public int iDefaultX = 50, iDefaultY = 50;

	/**
	 * Begins with a clean status.
	 */
	public Game_Status() {
		resetGame();
	}
	
	/**
	 * Clears the status to the specified level, default positions and full brick array.
	 * 
	 * @param iL Level to reset the status to.
	 */
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
	
	/**
	 * Clears the status to level 1, default positions, full brick array and 3 lives.
	 */
	public void resetGame() {
		resetLevel(0);
		iLives = 3;
	}
	
	/**
	 * Resets ball position to the center of the screen, and decrements the lives counter.
	 */
	public void loseLife() {
		iBallX = iDefaultX;
		iBallY = iDefaultY;
		iLives--;
	}
}
