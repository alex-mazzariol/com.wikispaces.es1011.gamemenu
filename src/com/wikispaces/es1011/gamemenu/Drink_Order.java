package com.wikispaces.es1011.gamemenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Abstracts the list of drinks in the current order.
 * @author Amarilli Alessandro
 *
 */
public class Drink_Order {

	private class dbHelper extends SQLiteOpenHelper {
		public dbHelper(Context c) {
			super(c, "drink_order_db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table drink_order (_id integer primary key, d_price text, d_name text, d_qty integer not null);");
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

	/**
	 * Instantiates a drink order object.
	 * @param context The context used to gain access to the database
	 */
	public Drink_Order(Context context) {
		mCtx = context;
	}

	/**
	 * Opens a connection to the database
	 * @return This object, with the connection open.
	 */
	public Drink_Order dbOpen() {
		dbh = new dbHelper(mCtx);
		db = dbh.getWritableDatabase();
		return this;
	}

	/**
	 * Closes the connection to the database and frees associated resources.
	 */
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
	private long insertItem(long drink_id, String d_name, double dPrice) {		
		ContentValues cvData = new ContentValues();
		cvData.put("_id", drink_id);
		cvData.put("d_qty", 1);
		cvData.put("d_name", d_name);
		cvData.put("d_price", dPrice);

		return db.insert("drink_order", null, cvData);
	}

	/**
	 * Returns a table with the current order.
	 * @return The order table
	 */
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

	/**
	 * Updates the table to new values.
	 * @param drink_id The drink to update
	 * @param d_name The name of the drink
	 * @param d_qty The quantity to set
	 * @return True if update successful.
	 */
	private long setDrinkQuantity(long drink_id, String d_name, int d_qty) {
		ContentValues cV = new ContentValues();
		cV.put("_id", drink_id);
		cV.put("d_qty", d_qty);
		cV.put("d_name", d_name);

		return db.update("drink_order", cV, "_id=" + drink_id, null);
	}

	/**
	 * Adds one to the current order quantity of specified drink. To keep the database simple, many parameters should be passed.
	 * @param drink_id The drink to increment
	 * @param drink_name The name of the drink
	 * @param d_price The price of the drink
	 * @return The updated value
	 */
	public long drinkIncrement(long drink_id, String drink_name, double d_price) {
		int iActual = getDrinkQuantity(drink_id);

		if (iActual < 0) {
			// Insert the row too
			if (insertItem(drink_id, drink_name, d_price) < 0)
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

	/**
	 * Decrements the quantity of specified drink. To keep the database simple, many parameters should be passed.
	 * @param drink_id The drink to decrement
	 * @param drink_name The name of the drink
	 * @return The updated value.
	 */
	public long drinkDecrement(long drink_id, String drink_name) {
		int iActual = getDrinkQuantity(drink_id);

		if (iActual > 1) {
			if (setDrinkQuantity(drink_id, drink_name, iActual - 1) < 0)
				return iActual;
			else
				return iActual - 1;
		} else if (iActual == 1) {
			if (deleteOrder(drink_id) < 0)
				return iActual;
			else
				return iActual - 1;
		}

		return iActual;
	}

	/**
	 * Completely removes a row from the order
	 * @param drink_id The drink to remove
	 * @return True if deletion successful.
	 */
	private int deleteOrder(long drink_id) {
		return db.delete("drink_order", "_id=" + drink_id, null);
	}

	/**
	 * Clears the order.
	 */
	public void orderClear() {
		db.execSQL("delete from drink_order");
	}

	/**
	 * Returns the total number of items in the order.
	 * @return The total number of items.
	 */
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

	/**
	 * Returns the number of different drinks in the order.
	 * @return The number of different drinks.
	 */
	public int getListCount() {
		Cursor mC = db.query("drink_order",
				new String[] { "count(d_qty) as d_tot" }, null, null, null,
				null, null);

		if (mC != null && mC.getCount() > 0) {
			mC.moveToFirst();
			int i = mC.getInt(0);
			mC.close();
			return i;
		} else
			return 0;
	}

	/**
	 * Calculates the grand total of the current order, summing up prices for every item.
	 * @return The grand total of the current order.
	 */
	public String getOrderTotal() {
		Cursor mC = db.query("drink_order",
				new String[] { "d_qty", "d_price" }, null, null, null, null,
				null);

		if (mC != null && mC.getCount() > 0) {
			mC.moveToFirst();
			
			double i = mC.getDouble(1) * mC.getInt(0);
			while(!mC.isLast())
			{
				mC.moveToNext();
				i += mC.getDouble(1) * mC.getInt(0);
			}
			mC.close();
			return i + " EUR";
		} else
			return "0.0 EUR";
	}
}
