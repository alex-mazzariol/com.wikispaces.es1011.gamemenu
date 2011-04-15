package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ActGameEntertainer extends Activity {

    public static Display display;
    public TextView tv;
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private game_GameState gs;
   private game_GameSurfaceView gsw;
   private LinearLayout gameLayout;

   /** Called when the activity is first created.
    * @param savedInstanceState
    */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       display = getWindowManager().getDefaultDisplay();
       // Get an instance of the SensorManager
       mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

           
       // Get an instance of the PowerManager
       mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

       // Get an instance of the WindowManager
       mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
       mWindowManager.getDefaultDisplay();
       
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


       mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

       gs = new game_GameState();
       gsw = new game_GameSurfaceView(this, mSensorManager, gs);

       gs.setState(game_GameState.State.READY);


   }

   @Override
   public void onPause() {
       super.onPause();
       gameLayout = new LinearLayout(this);
       final TextView tv = new TextView(this);
       tv.setText("SCORE" + gs.getScore());
       tv.setHeight(80);
       Button bu = new Button(this);
       bu.setText("Continue");
       bu.setWidth(100);
       bu.setHeight(80);

       bu.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {
               onResume();
           }
       });

       gameLayout.addView(tv);
       gameLayout.addView(bu);
       setContentView(gameLayout);

       return;

   }

   @Override
   protected void onStart() {
       super.onStart();
       setContentView(gsw);
       
   }

   @Override
   protected void onRestart() {
       super.onRestart();
   }

   @Override
   protected void onResume() {
       super.onResume();
       
   }

   @Override
   protected void onStop() {
       super.onStop();
   }

   @Override
   protected void onDestroy() {
       super.onDestroy();
   }
        


}
