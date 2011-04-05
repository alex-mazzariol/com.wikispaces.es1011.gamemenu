package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.view.*;

public class ActDrinkSelector extends Activity {
 
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.actdrinkselector);
	
	    GridView gridview = (GridView) findViewById(R.id.ads_gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(ActDrinkSelector.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });	
	}

}
