package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.view.*;

public class ActDrinkSelector extends Activity {
	private enum ePage {
		pgList, pgOrder, pgDetail
	};

	private Drink_Order doCurrent;
	private Drink_OriginalList dlList;
	private ePage eBack;
	private Long iDrinkDetailID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		doCurrent = new Drink_Order(this);
		dlList = new Drink_OriginalList(this);
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
			ListView lvList = ((ListView) findViewById(R.id.drink_list_listview));
			SimpleCursorAdapter dList = new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_2, dlList.getDrinkList(),
					new String[] { "d_name", "d_price" }, new int[] {
							android.R.id.text1, android.R.id.text2 });
			lvList.setAdapter(dList);
			lvList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					iDrinkDetailID = id;
					showPage(ePage.pgDetail);
				}
			});

			eBack = ePage.pgList;
			break;
		case pgOrder:
			setContentView(R.layout.drink_order);
			((Button) findViewById(R.id.drink_btn_back_list))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							showPage(ePage.pgList);
						}
					});
			LinearListView lvOrder = ((LinearListView) findViewById(R.id.drink_riepilogo));
			// TODO Agganciare gli eventi della lista e dei bottoni
			eBack = ePage.pgOrder;
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
			//TODO Pescare i dati corretti!
			((TextView) findViewById(R.id.drink_det_name))
					.setText(iDrinkDetailID.toString());
			break;
		}
	}

	public void onPause() {
		doCurrent.dbClose();
		dlList.dbClose();
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		doCurrent.dbOpen();
		dlList.dbOpen();
		showPage(ePage.pgList);
	}
}