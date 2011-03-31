package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.os.*;
import android.widget.*;

public class ActWaiterCall extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Artists tab");
        setContentView(textview);
    }

}
