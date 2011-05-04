package com.wikispaces.es1011.gamemenu;

public class Game_SurfaceThread extends Thread {

    private boolean myThreadRun = false;
    private IUpdatable update;
    

    public Game_SurfaceThread(IUpdatable update) {
    	this.update = update;
    }

    public void setRunning(boolean b) {
        myThreadRun = b;
    }

    @Override
    public void run() {
        while (myThreadRun) {
        	update.forceUpdate();
        	try{
        		Thread.sleep(50);
        	}
        	catch(InterruptedException ed)
        	{
        		//Silently eat the exception
        	}
        }
    }
}
