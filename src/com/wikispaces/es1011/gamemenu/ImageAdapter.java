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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ImageView imageView;
    	TextView tvLabel;
    	LinearLayout llIcon;
        
    	if (convertView == null) {  // if it's not recycled, initialize some attributes
        	llIcon = new LinearLayout(mContext);
            llIcon.setOrientation(1);
        	imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setImageResource(mThumbIds[position]);
            tvLabel = new TextView(mContext);
            tvLabel.setLayoutParams(new GridView.LayoutParams(70, 20));
            tvLabel.setText("testo");
            llIcon.addView(imageView, 0);
            llIcon.addView(tvLabel, 1);
    	} 
        
        else {
        	llIcon = (LinearLayout) convertView;
        }

    	return llIcon;
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
