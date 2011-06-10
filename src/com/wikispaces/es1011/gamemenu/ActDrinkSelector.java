package com.wikispaces.es1011.gamemenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;
import android.view.*;
import android.view.View.OnClickListener;

/**
 * Activity for the DrinkSelector tab
 * @author Amarilli Alessandro
 *
 */
public class ActDrinkSelector extends Activity {
	private enum ePage {
		pgList, pgOrder, pgDetail
	};

	private Drink_Order doCurrent;
	private Drink_OriginalList dlList;
	private ePage eBack;
	private ePage eCurrentPage = ePage.pgList;
	private long iDrinkDetailID = -1;

	/**
	 * Restores saved page, if any.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			eCurrentPage = savedInstanceState
					.getString("d_page") != null ? ePage
					.valueOf(savedInstanceState.getString("d_page"))
					: ePage.pgList;

			iDrinkDetailID = savedInstanceState.getLong("d_detailID", -1);
			
			eBack = savedInstanceState.getString("d_back") != null ?
					ePage.valueOf(savedInstanceState.getString("d_back"))
					: ePage.pgList;
		}
	}
	
	public void onStart() {
		super.onStart();
		doCurrent = new Drink_Order(this).dbOpen();
		dlList = new Drink_OriginalList(this).dbOpen();
	}

	public void onStop() {
		doCurrent.dbClose();
		dlList.dbClose();
		super.onStop();
	}

	/**
	 * Provides a centralized entry point for page changes. Once a page is loaded, adjusts its content too.
	 * @param Page The page to display.
	 */
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
			((Button) findViewById(R.id.drink_order_btn_back))
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
					iDrinkDetailID = id;
					showPage(ePage.pgDetail);
				}
			});

			((TextView) findViewById(R.id.drink_order_lbl_totdrink))
					.setText(Integer.toString(doCurrent.getItemsCount()));
			((TextView) findViewById(R.id.drink_order_lbl_totcash))
					.setText(doCurrent.getOrderTotal());
			((Button) findViewById(R.id.drink_order_btn_clear))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.orderClear();
							Toast.makeText(v.getContext(), "Order cleared!", 3)
									.show();
							showPage(ePage.pgOrder);
						}
					});
			((Button) findViewById(R.id.drink_order_btn_send))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.orderClear();
							Toast.makeText(v.getContext(), "Order sent!", 3)
									.show();
							showPage(ePage.pgOrder);
						}
					});
			eBack = ePage.pgOrder;
			break;
		case pgDetail:
			setContentView(R.layout.drink_details);
			((ImageButton) findViewById(R.id.drink_det_btn_plus))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.drinkIncrement(iDrinkDetailID,
									dlList.getDrinkName(iDrinkDetailID),
									dlList.getDrinkRawPrice(iDrinkDetailID));
						}
					});
			((ImageButton) findViewById(R.id.drink_det_btn_minus))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							doCurrent.drinkDecrement(iDrinkDetailID,
									dlList.getDrinkName(iDrinkDetailID));
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

		eCurrentPage = Page;
	}

	public void onResume() {
		super.onResume();
		showPage(eCurrentPage);
	}

	/**
	 * Saves the current page, for a nice user experience.
	 */
	public void onSaveInstanceState(Bundle out) {
		out.putString("d_page", eCurrentPage.name().toString());
		out.putString("d_back", eBack.name().toString());
		out.putLong("d_detailID", iDrinkDetailID);
		super.onSaveInstanceState(out);
	}
}