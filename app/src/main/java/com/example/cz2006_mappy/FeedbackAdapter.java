package com.example.cz2006_mappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FeedbackAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;
    private final Integer[] mThumbIds;

    public FeedbackAdapter(Context context, Integer[] mThumbIds) {
        this.context = context;
        this.mThumbIds = mThumbIds;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.item, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);


            imageView.setImageResource(mThumbIds[position]);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
