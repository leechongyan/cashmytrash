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

public class UserAdaptor extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;
    // private final Integer[] mThumbIds;
    private final List<User> users;
    public UserAdaptor(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.user, null);
            TextView userName = gridView.findViewById(R.id.grid_item_name_all);
            TextView email = gridView.findViewById(R.id.grid_item_email);
            // convert to imagebutton for presing
            ImageButton del = gridView.findViewById(R.id.grid_item_trash_all);
            userName.setText(users.get(position).getUsername());
            email.setText(users.get(position).getEmailaddress())  ;

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    // ID matches row!!!
    public long getItemId(int position) {
        return position;
    }
}
