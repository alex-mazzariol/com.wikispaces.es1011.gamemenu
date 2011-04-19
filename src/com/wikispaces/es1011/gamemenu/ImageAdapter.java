package com.wikispaces.es1011.gamemenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    //@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LinearLayout llLine;
    	LinearLayout llTexts;
    	LinearLayout llFirstRow;
    	ImageView ivDrinkImage;
    	TextView tvName;
    	TextView tvPrice;
    	TextView tvDescr;    	
        
    	if (convertView == null) {  // if it's not recycled, initialize some attributes
        	llLine = new LinearLayout(mContext);        	
            llLine.setOrientation(2);
        	ivDrinkImage = new ImageView(mContext);
        	ivDrinkImage.setLayoutParams(new GridView.LayoutParams(70, 70));
        	ivDrinkImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        	ivDrinkImage.setPadding(8, 8, 8, 8);
        	ivDrinkImage.setImageResource(mThumbIds[position]);
        	llTexts = new LinearLayout(mContext);
        	llTexts.setLayoutParams(new GridView.LayoutParams(-1, 70));
        	llTexts.setOrientation(1);
        	llLine.addView(ivDrinkImage, 0);
        	llLine.addView(llTexts, 1);
        	llFirstRow = new LinearLayout (mContext);
        	llFirstRow.setOrientation(2);
        	tvName = new TextView (mContext);
        	tvName.setLayoutParams(new GridView.LayoutParams(-1, 35));
        	tvName.setText("Name");
        	tvPrice = new TextView (mContext);
        	tvPrice.setLayoutParams(new GridView.LayoutParams(100, 35));
        	tvPrice.setText("5,00 €");
        	llFirstRow.addView(tvName, 0);
        	llFirstRow.addView(tvPrice, 1);
        	tvDescr = new TextView (mContext);
        	tvDescr.setLayoutParams(new GridView.LayoutParams(-1, 35));
        	tvDescr.setText("Description");
        	llTexts.addView(llFirstRow, 0);
        	llTexts.addView(tvDescr, 1);      
    	} 
        
        else {
        	llLine = (LinearLayout) convertView;
        }

    	return llLine;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.img1, R.drawable.img2,
            R.drawable.img3, R.drawable.img5,
            R.drawable.img6, R.drawable.img7,
            R.drawable.img4, R.drawable.img8,
            R.drawable.img9, R.drawable.img10,
            R.drawable.img11, R.drawable.img12,
    };
}
