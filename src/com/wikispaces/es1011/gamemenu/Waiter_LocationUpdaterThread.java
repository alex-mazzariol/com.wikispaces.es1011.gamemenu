package com.wikispaces.es1011.gamemenu;

public class Waiter_LocationUpdaterThread extends Thread {

	ActWaiterCall aHolder;
	
	public Waiter_LocationUpdaterThread(ActWaiterCall aHld)
	{
		aHolder = aHld;
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted())
		{
			aHolder.locUpdate();
			try {
				sleep(5000);
			} catch (InterruptedException e) {}
		}
	}
	
}
