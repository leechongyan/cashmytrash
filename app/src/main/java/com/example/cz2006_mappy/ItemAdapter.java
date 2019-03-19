package com.example.cz2006_mappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;

    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.items = items;

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

            imageView.setImageResource(R.drawable.ic_menu_camera);

            TextView itemName = (TextView) gridView.findViewById(R.id.grid_item_name);
            TextView itemPrice = (TextView) gridView.findViewById(R.id.grid_item_price);
            TextView itemUsername = (TextView) gridView.findViewById(R.id.grid_item_username);
            TextView itemDescription = (TextView) gridView.findViewById(R.id.grid_item_description);

            String name = items.get(position).getItem_name();
            double price = items.get(position).getPrice();
            String username = items.get(position).getSeller_username();
            String description = items.get(position).getItem_description();

            itemName.setText(name);
            itemPrice.setText(Double.toString(price));
            itemUsername.setText(username);
            itemDescription.setText(description);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return items.size();
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
