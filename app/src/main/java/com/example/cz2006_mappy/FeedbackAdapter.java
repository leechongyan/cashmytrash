package com.example.cz2006_mappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;
    // private final Integer[] mThumbIds;
    private final List<Feedback> feedbacks;
    public FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.feedback, null);
            TextView userName = gridView.findViewById(R.id.grid_item_name_all);
            // convert to imagebutton for presing
            ImageButton del = gridView.findViewById(R.id.grid_item_trash_all);
            userName.setText(feedbacks.get(position).getSender_username());
            // set image based on selected text
//            ImageView imageView = (ImageView) gridView
//                    .findViewById(R.id.grid_item_image);

            // imageView.setImageResource();


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public Object getItem(int position) {
        return feedbacks.get(position);
    }

    @Override
    // ID matches row!!!
    public long getItemId(int position) {
        return position;
    }
}
