package com.wikispaces.es1011.gamemenu;

import java.util.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Drink_Order extends BaseAdapter {
	public HashMap<String, Integer> lOrder;
	private Context mContext;

	public Drink_Order(Context c) {
		lOrder = new HashMap<String, Integer>();
		mContext = c;
		
		lOrder.put("toni", 2);
	}

	public int getCount() {
		return lOrder.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout llLine;
		TextView tvName;
		TextView tvCount;

		Map.Entry<String, Integer> mArr[] = lOrder.entrySet().toArray(null);
		
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			llLine = new LinearLayout(mContext);
			llLine.setOrientation(2);
			llLine.setLayoutParams(new GridView.LayoutParams(-1, 70));

			tvName = new TextView(mContext);
			tvName.setLayoutParams(new GridView.LayoutParams(-1, 35));
			tvName.setText(mArr[position].getKey());

			tvCount = new TextView(mContext);
			tvCount.setLayoutParams(new GridView.LayoutParams(-1, 35));
			tvCount.setText(mArr[position].getValue());

			llLine.addView(tvName, 0);
			llLine.addView(tvCount, 1);
		}

		else {
			llLine = (LinearLayout) convertView;
		}

		return llLine;
	}
}
