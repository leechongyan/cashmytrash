package com.example.cz2006_mappy;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ImageButton;
import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;
    private FeedbackViewModel model;
    // private final Integer[] mThumbIds;
    private final List<Feedback> feedbacks;
    public FeedbackAdapter(Context context, List<Feedback> feedbacks, FeedbackViewModel model) {
        this.model = model;
        this.context = context;
        this.feedbacks = feedbacks;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.feedback, null);
            TextView userName = gridView.findViewById(R.id.grid_item_name_all);
            TextView feedbackID = gridView.findViewById(R.id.ID);
            Button seeMore = gridView.findViewById(R.id.seeFeedback);
            feedbackID.setText(Integer.toString(feedbacks.get(position).getFeedback_id()));
            userName.setText(feedbacks.get(position).getSender_username());
            // feedbackID.setText("123");
            Button del = gridView.findViewById(R.id.grid_item_trash_all);
            TextView feedback_content = gridView.findViewById(R.id.feedbackText);
            // feedback_content.setText(feedbacks.get(position).getFeedback_content());
            int width = 100;
            int height = 100;
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(context);
            popupWindow.setWidth(width);
            popupWindow.setHeight(height);
            popupWindow.setContentView(feedbackID);

            seeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("FEEDBACK").
                            setMessage(feedbacks.get(position).
                                    getFeedback_content()).
                            setNeutralButton("OK", null).show();
                }
            });

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.deleteFeedback(feedbacks.get(position));
                }
            });




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
