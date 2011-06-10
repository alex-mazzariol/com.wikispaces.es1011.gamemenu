package com.wikispaces.es1011.gamemenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A list view that does not automatically scroll.
 * @author Amarilli Alessandro
 *
 */
public class LinearListView extends LinearLayout {
	Adapter adapter;
	Observer observer = new Observer(this);
	OnItemClickListener oAction = null;

	public LinearListView(Context context) {
		super(context);
	}

	public LinearListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setOnItemClickListener(OnItemClickListener oAct) {
		oAction = oAct;
	}

	public void setAdapter(Adapter adapter) {
		if (this.adapter != null)
			this.adapter.unregisterDataSetObserver(observer);

		this.adapter = adapter;
		adapter.registerDataSetObserver(observer);
		observer.onChanged();
	}

	private class Observer extends DataSetObserver {
		LinearListView context;

		public Observer(LinearListView context) {
			this.context = context;
		}

		@Override
		public void onChanged() {
			List<View> oldViews = new ArrayList<View>(context.getChildCount());

			for (int i = 0; i < context.getChildCount(); i++)
				oldViews.add(context.getChildAt(i));

			Iterator<View> iter = oldViews.iterator();

			context.removeAllViews();

			for (int i = 0; i < context.adapter.getCount(); i++) {
				View convertView = iter.hasNext() ? iter.next() : null;
				View vRes = context.adapter.getView(i, convertView, context);
				vRes
						.setOnClickListener((OnClickListener) new ParameterizedOnClickListener(
								i, context));
				context.addView(vRes);
			}
			super.onChanged();
		}

		@Override
		public void onInvalidated() {
			context.removeAllViews();
			super.onInvalidated();
		}
	}

	private class ParameterizedOnClickListener implements OnClickListener {
		private LinearListView cTx;
		private int iPos;

		public ParameterizedOnClickListener(int i, LinearListView c) {
			iPos = i;
			cTx = c;
		}

		public void onClick(View v) {
			oAction.onItemClick(null, null, iPos, cTx.adapter.getItemId(iPos));
		}
	}
}
