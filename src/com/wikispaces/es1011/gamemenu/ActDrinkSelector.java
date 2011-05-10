package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;

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

		doCurrent = new Drink_Order(this).dbOpen();
		dlList = new Drink_OriginalList(this).dbOpen();
	}

	private void showPage(ePage Page) {
		switch (Page) {
		case pgList:
			setContentView(R.layout.drink_list);
			((Button) findViewById(R.id.drink_list_headerpanel_btn_order))
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							showPage(ePage.pgOrder);
						}
					});
			ListView lvList = ((ListView) findViewById(R.id.drink_list_listview));
			Drink_ImageListAdapter dListAdapter = new Drink_ImageListAdapter(
					this, dlList.getDrinkList());
			lvList.setAdapter(dListAdapter);
			lvList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					iDrinkDetailID = id;
					showPage(ePage.pgDetail);
				}
			});

			((TextView) findViewById(R.id.drink_list_headerpanel_label))
					.setText("Order items: " + doCurrent.getItemsCount());

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
			Drink_ImageListAdapter dOrderAdapter = new Drink_ImageListAdapter(
					this, doCurrent.getOrderList());
			lvOrder.setAdapter(dOrderAdapter);
			
			lvOrder.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					
				}
			});
			eBack = ePage.pgOrder;
			break;
		case pgDetail:
			setContentView(R.layout.drink_details);
			((ImageButton) findViewById(R.id.drink_det_btn_plus))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.drinkIncrement(iDrinkDetailID, dlList
									.getDrinkName(iDrinkDetailID));
						}
					});
			((ImageButton) findViewById(R.id.drink_det_btn_minus))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.drinkDecrement(iDrinkDetailID, dlList
									.getDrinkName(iDrinkDetailID));
						}
					});
			((Button) findViewById(R.id.drink_det_btn_back))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							showPage(eBack);
						}
					});
			((TextView) findViewById(R.id.drink_det_name)).setText(dlList
					.getDrinkName(iDrinkDetailID));
			((TextView) findViewById(R.id.drink_det_short)).setText(dlList
					.getDrinkPrice(iDrinkDetailID));
			((TextView) findViewById(R.id.drink_det_longdescr)).setText(dlList
					.getDrinkLong(iDrinkDetailID));
			((ImageView) findViewById(R.id.drink_det_image))
					.setImageResource(getResources().getIdentifier(
							"com.wikispaces.es1011.gamemenu:drawable/drink_"
									+ iDrinkDetailID, null, null));
			break;
		}
	}

	public void onResume() {
		super.onResume();
		showPage(ePage.pgList);
	}
}