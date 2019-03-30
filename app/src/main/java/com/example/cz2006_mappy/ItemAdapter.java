package com.example.cz2006_mappy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
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


            TextView itemName = (TextView) gridView.findViewById(R.id.grid_item_name);
            TextView itemPrice = (TextView) gridView.findViewById(R.id.grid_item_price);
            TextView itemUsername = (TextView) gridView.findViewById(R.id.grid_item_username);
            TextView itemDescription = (TextView) gridView.findViewById(R.id.grid_item_description);
            TextView itemId = (TextView) gridView.findViewById(R.id.grid_item_id);

            String name = items.get(position).getItem_name();
            double price = items.get(position).getPrice();
            String username = items.get(position).getSeller_username();
            String description = items.get(position).getItem_description();
            int id = items.get(position).getItem_id();
            byte[] image = items.get(position).getImage();
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,150,150,false));
            itemName.setText(name);
            itemPrice.setText(Double.toString(price));
            itemUsername.setText(username);
            itemDescription.setText(description);
            itemId.setText(Integer.toString(id));

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
