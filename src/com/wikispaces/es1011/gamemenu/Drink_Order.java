package com.wikispaces.es1011.gamemenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Drink_Order {

	private class dbHelper extends SQLiteOpenHelper {
		public dbHelper(Context c) {
			super(c, "drink_order_db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db
					.execSQL("create table drink_order (_id integer primary key, d_name text, d_qty integer not null);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists drink_order");
			onCreate(db);
		}

	}

	private Context mCtx;
	private SQLiteDatabase db;
	private dbHelper dbh;

	public Drink_Order(Context context) {
		mCtx = context;
	}

	public Drink_Order dbOpen() {
		dbh = new dbHelper(mCtx);
		db = dbh.getWritableDatabase();
		return this;
	}

	public void dbClose() {
		dbh.close();
	}

	/**
	 * Inserts an item in the table. Sets also its quantity to 1.
	 * 
	 * @param drink_id
	 *            The identifier for the drink to add.
	 * @return -1 if failed.
	 */
	private long insertItem(long drink_id, String d_name) {
		// Automaticamente l'inserimento di un drink in un ordine
		// significa inserire una riga con quantità 1
		ContentValues cvData = new ContentValues();
		cvData.put("_id", drink_id);
		cvData.put("d_qty", 1);
		cvData.put("d_name", d_name);

		return db.insert("drink_order", null, cvData);
	}

	public Cursor getOrderList() {
		return db.query("drink_order",
				new String[] { "_id", "d_name", "d_qty" }, null, null, null,
				null, null);
	}

	/**
	 * Gets the quantity associated with the specified drink.
	 * 
	 * @param drink_id
	 *            The identifier for the drink to search for
	 * @return The quantity associated with the drink_id. Returns -1 if no such
	 *         row in table.
	 */
	private int getDrinkQuantity(long drink_id) {
		Cursor mC = db.query("drink_order", new String[] { "d_qty" }, "_id="
				+ drink_id, null, null, null, null);

		if (mC != null && mC.getCount() > 0) {
			mC.moveToFirst();
			int i = mC.getInt(0);
			mC.close();
			return i;
		} else {
			return -1;
		}
	}

	private long setDrinkQuantity(long drink_id, String d_name, int d_qty) {
		ContentValues cV = new ContentValues();
		cV.put("_id", drink_id);
		cV.put("d_qty", d_qty);
		cV.put("d_name", d_name);

		return db.update("drink_order", cV, "_id=" + drink_id, null);
	}

	public long drinkIncrement(long drink_id, String drink_name) {
		int iActual = getDrinkQuantity(drink_id);

		if (iActual < 0) {
			// Bisogna aggiungere la riga
			if (insertItem(drink_id, drink_name) < 0)
				return 0;
			else
				return 1;
		} else {
			if (setDrinkQuantity(drink_id, drink_name, iActual + 1) < 0)
				return iActual;
			else
				return iActual + 1;
		}
	}

	public long drinkDecrement(long drink_id, String drink_name) {
		int iActual = getDrinkQuantity(drink_id);

		if (iActual > 0) {
			if (setDrinkQuantity(drink_id, drink_name, iActual - 1) < 0)
				return iActual;
			else
				return iActual - 1;
		}

		return iActual;
	}

	public void orderClear() {
		db.execSQL("delete from drink_order");
	}

	public int getItemsCount() {
		Cursor mC = db.query("drink_order",
				new String[] { "sum(d_qty) as d_tot" }, null, null, null, null,
				null);

		if (mC != null && mC.getCount() > 0) {
			mC.moveToFirst();
			int i = mC.getInt(0);
			mC.close();
			return i;
		} else
			return 0;
	}
	
	public int getListCount() {
		Cursor mC = db.query("drink_order",
				new String[] { "count(d_qty) as d_tot" }, null, null, null, null,
				null);

		if (mC != null && mC.getCount() > 0) {
			mC.moveToFirst();
			int i = mC.getInt(0);
			mC.close();
			return i;
		} else
			return 0;
	}

	public String getOrderTotal() {
		// TODO
		return "0.0 EUR";
	}
}
