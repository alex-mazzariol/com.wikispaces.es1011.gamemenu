package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
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
import android.widget.*;
import android.view.*;

public class ActDrinkSelector extends Activity {
	private enum ePage {
		pgList, pgOrder, pgDetail
	};

	private Drink_Order doCurrent;
	private ePage eBack;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		doCurrent = new Drink_Order(this);
		
		showPage(ePage.pgList);
	}

	private void showPage(ePage Page) {
		switch (Page) {
		case pgList:
			setContentView(R.layout.drink_list);
			((Button) findViewById(R.id.drink_headerpanel_btn_order))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							showPage(ePage.pgOrder);
						}
					});
			// TODO Agganciare gli eventi della lista
			break;
		case pgOrder:
			setContentView(R.layout.drink_order);
			((Button) findViewById(R.id.drink_btn_back_list))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							showPage(ePage.pgList);
						}
					});
			((LinearListView) findViewById(R.id.drink_riepilogo)).setAdapter(doCurrent);
			// TODO Agganciare gli eventi della lista e dei bottoni
			break;
		case pgDetail:
			setContentView(R.layout.drink_details);
			// TODO Agganciare gli eventi dei bottoni +-
			((Button) findViewById(R.id.drink_det_btn_back))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							showPage(eBack);
						}
					});
			break;
		}
	}
}