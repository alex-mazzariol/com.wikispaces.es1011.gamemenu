package com.wikispaces.es1011.gamemenu;

public class Waiter_LocationUpdaterThread extends Thread {

	IUpdatable aHolder;
	
	public Waiter_LocationUpdaterThread(IUpdatable aHld)
	{
		aHolder = aHld;
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted())
		{
			aHolder.forceUpdate();
			try {
				sleep(5000);
			} catch (InterruptedException e) {}
		}
	}
	
}
