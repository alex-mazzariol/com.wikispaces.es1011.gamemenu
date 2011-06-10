package com.wikispaces.es1011.gamemenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Abstracts the list of all drinks.
 * @author Amarilli Alessandro
 *
 */
public class Drink_OriginalList {

	private class dbHelper extends SQLiteOpenHelper {
		public dbHelper(Context c) {
			super(c, "drink_lst_db", null, 1);
		}

		private final String sLoremIpsum = "Lorem ipsum dolor sit amet, "
				+ "consectetur adipiscing elit. Aliquam adipiscing lorem eu magna "
				+ "vestibulum sollicitudin. Sed quam tellus, cursus eget lobortis "
				+ "volutpat, placerat vitae orci. Nunc vitae lacus magna. Praesent "
				+ "eu semper sem. Etiam id eros lorem, vel adipiscing justo. Nunc "
				+ "aliquam tortor porta velit tempus at fringilla sapien accumsan. "
				+ "Vestibulum consectetur quam sit amet nisl tincidunt rhoncus "
				+ "rhoncus dolor commodo. Nunc sagittis tempus ligula, quis laoreet "
				+ "arcu suscipit vel. Etiam vitae orci eros, a tincidunt massa. "
				+ "Fusce sit amet mi quam, at posuere dui. Cras vel augue turpis, "
				+ "vel rhoncus dolor. Class aptent taciti sociosqu ad litora "
				+ "torquent per conubia nostra, per inceptos himenaeos.";

		public void onCreate(SQLiteDatabase db) {
			db
					.execSQL("create table drink_list (_id integer primary key, d_name text, d_price text, d_description text);");
			// Insert default drink list
			ContentValues cvVal = new ContentValues();
			cvVal.put("d_price", "5.00");
			cvVal.put("d_description", sLoremIpsum);
			for (int i = 1; i < 13; i++) {
				cvVal.put("_id", i);
				cvVal.put("d_name", "Drink " + i);
				db.insert("drink_list", null, cvVal);
			}
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists drink_list");
			onCreate(db);
		}
	}

	private Context mCtx;
	private SQLiteDatabase db;
	private dbHelper dbh;

	/**
	 * Instantiates a drink list object.
	 * @param context The context used to gain access to the database
	 */
	public Drink_OriginalList(Context context) {
		mCtx = context;
	}

	/**
	 * Opens a connection to the database
	 * @return This object, with the connection open.
	 */
	public Drink_OriginalList dbOpen() {
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
	 * Returns a table with the full list of drinks.
	 * @return A table with the full list of drinks.
	 */
	public Cursor getDrinkList() {
		return db.query("drink_list",
				new String[] { "_id", "d_name", "d_price" }, null, null, null,
				null, null);
	}

	/**
	 * Returns the number of items in the list.
	 * @return The number of items in the list.
	 */
	public int getListCount() {
		Cursor mC = db.query("drink_list",
				new String[] { "count(d_name) as d_tot" }, null, null, null,
				null, null);

		if (mC != null) {
			mC.moveToFirst();
			int i = mC.getInt(0);
			mC.close();
			return i;
		} else
			return 0;
	}

	/**
	 * Returns the name of the specified drink
	 * @param drink_id The drink to look for.
	 * @return The name of the specified drink.
	 */
	public String getDrinkName(long drink_id) {
		Cursor mC = db.query("drink_list", new String[] { "d_name" }, "_id="
				+ drink_id, null, null, null, null);
		
		if(mC != null){
			mC.moveToFirst();
			String sRet = mC.getString(0);
			mC.close();
			return sRet;
		}
		else
			return "";
	}
	
	/**
	 * Returns the price of the specified drink, formatted as a string.
	 * @param drink_id The drink to look for.
	 * @return The price of the specified drink, formatted as string with " EUR" appended.
	 */
	public String getDrinkPrice(long drink_id) {
		Cursor mC = db.query("drink_list", new String[] { "d_price" }, "_id="
				+ drink_id, null, null, null, null);
		
		if(mC != null){
			mC.moveToFirst();
			String sRet = mC.getString(0);
			mC.close();
			return sRet + " EUR";
		}
		else
			return "";
	}
	
	/**
	 * Returns the price of the specified drink.
	 * @param drink_id The drink to look for.
	 * @return The price of the specified drink, as a double precision number.
	 */
	public double getDrinkRawPrice(long drink_id) {
		Cursor mC = db.query("drink_list", new String[] { "d_price" }, "_id="
				+ drink_id, null, null, null, null);
		
		if(mC != null){
			mC.moveToFirst();
			double sRet = mC.getDouble(0);
			mC.close();
			return sRet;
		}
		else
			return 0.0;
	}
	
	/**
	 * Returns the long description of the specified drink.
	 * @param drink_id The drink to look for.
	 * @return The string of the long description for the drink.
	 */
	public String getDrinkLong(long drink_id){
		Cursor mC = db.query("drink_list", new String[] { "d_description" }, "_id="
				+ drink_id, null, null, null, null);
		
		if(mC != null){
			mC.moveToFirst();
			String sRet = mC.getString(0);
			mC.close();
			return sRet;
		}
		else
			return "";
	}
}
