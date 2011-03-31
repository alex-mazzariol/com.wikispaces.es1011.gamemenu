package com.wikispaces.es1011.gamemenu;

import android.app.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.os.*;

public class GameMenu extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ActDrinkSelector.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("drinkSelector").setIndicator("Drinks",
                          res.getDrawable(R.drawable.ic_tab_actdrinkselector ))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, ActWaiterCall.class);
        spec = tabHost.newTabSpec("waiterCall").setIndicator("Waiter",
                          res.getDrawable(R.drawable.ic_tab_actwaitercall))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ActGameEntertainer.class);
        spec = tabHost.newTabSpec("game").setIndicator("Game",
                          res.getDrawable(R.drawable.ic_tab_actgameentertainer))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);
    }
}