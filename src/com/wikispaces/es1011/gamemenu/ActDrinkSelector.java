package com.wikispaces.es1011.gamemenu;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.*;

public class ActDrinkSelector extends ListActivity {
	Cursor mCursor;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        	        
	        /*setContentView(R.layout.actdrinkselector);

	        GridView gridview = (GridView) findViewById(R.id.gridview);
	        gridview.setAdapter(new ImageAdapter(this));

	        gridview.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Toast.makeText(ActDrinkSelector.this, "" + position, Toast.LENGTH_SHORT).show();
	            }
	        });*/
	        /*drinkList.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	            // When clicked, show a toast with the TextView text
	            Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	                Toast.LENGTH_SHORT).show();
	          }
	        });*/
	        
	        ImageAdapter drinkList = new ImageAdapter(this);
	        
	         // Bind to our new adapter.
	         setListAdapter(drinkList);

	    }

}
