package com.example.cz2006_mappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemToDeliverAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;

    private List<Item> items;

    public ItemToDeliverAdapter(Context context, List<Item> items) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.items = items;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.item_to_deliver, null);

            TextView itemId = (TextView) gridView.findViewById(R.id.grid_item_id_to_deliver);
            TextView itemName = (TextView) gridView.findViewById(R.id.grid_item_name_to_deliver);
            TextView itemPrice = (TextView) gridView.findViewById(R.id.grid_item_price_to_deliver);
            TextView itemUsername = (TextView) gridView.findViewById(R.id.grid_item_username_to_deliver);
            TextView itemToken = (TextView) gridView.findViewById(R.id.grid_item_token_to_deliver);
            TextView itemDescription = (TextView) gridView.findViewById(R.id.grid_item_description_to_deliver);

            ImageView imageView_trash = (ImageView) gridView
                    .findViewById(R.id.grid_item_trash_to_deliver);

            imageView_trash.setImageResource(R.drawable.ic_delete);

            ImageView imageView_message = (ImageView) gridView
                    .findViewById(R.id.grid_item_message_to_deliver);

            imageView_message.setImageResource(R.drawable.ic_message);

            int id = items.get(position).getItem_id();
            String name = items.get(position).getItem_name();
            double price = items.get(position).getPrice();
            String username = items.get(position).getSeller_username();
            int token = items.get(position).getToken();
            String description = items.get(position).getItem_description();

            itemId.setText(Integer.toString(id));
            itemName.setText(name);
            itemPrice.setText(Double.toString(price));
            itemUsername.setText(username);
            itemToken.setText(Integer.toString(token));
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
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {return position; }
}
