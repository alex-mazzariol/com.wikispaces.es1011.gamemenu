package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

public class Waiter_OrientationListen extends OrientationEventListener {

//	Waiter_CameraShooter csAct;
	
	public Waiter_OrientationListen(Context ctx/*, Waiter_CameraShooter csToRotate*/){
		super(ctx, SensorManager.SENSOR_DELAY_NORMAL);
		//csAct = csToRotate;
	}

	@Override
	public void onOrientationChanged(int orientation) {
	//	csAct.handleOrientation(orientation);
	}

}
