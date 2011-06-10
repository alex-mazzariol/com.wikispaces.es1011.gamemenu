package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Data Adapter for a list with images and two columns of text. The first one left-aligned, the second one right-aligned.
 * @author Amarilli Alessandro
 *
 */
public class Drink_ImageListAdapter extends BaseAdapter {
	private Context mC;
	private Cursor cur;

	/**
	 * Instantiates an Adapter given a context and a cursor on the data.
	 * @param cx The Context used to extract resources 
	 * @param c The Cursor on data
	 */
	public Drink_ImageListAdapter(Context cx, Cursor c) {
		mC = cx;
		cur = c;
	}

	/**
	 * Closes the associated cursor.
	 */
	protected void finalize() {
		cur.close();
	}

	/**
	 * Returns the number of elements in the cursor.
	 */
	public int getCount() {
		return cur.getCount();
	}

	/**
	 * To keep the adapter simple, always returns null.
	 */
	public Object getItem(int position) {
		return null;
	}

	/**
	 * Returns the database ID of the item at specified position.
	 */
	public long getItemId(int position) {
		cur.moveToPosition(position);
		return cur.getLong(cur.getColumnIndex("_id"));
	}

	/**
	 * Returns the View object associated with the specified item.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout vOut;

		if (convertView == null) {
			vOut = (LinearLayout) View.inflate(mC, R.layout.drink_listitem,
					null);
		} else {
			vOut = (LinearLayout) convertView;
		}

		cur.moveToPosition(position);

		((ImageView) vOut.findViewById(R.id.drink_listitem_img))
				.setImageResource(mC.getResources().getIdentifier(
						"com.wikispaces.es1011.gamemenu:drawable/drink_"
								+ cur.getString(0), null, null));

		((TextView) vOut.findViewById(R.id.drink_listitem_tx1)).setText(cur
				.getString(1));
		((TextView) vOut.findViewById(R.id.drink_listitem_tx2)).setText(cur
				.getString(2));
		return vOut;
	}

}
