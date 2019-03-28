package com.example.cz2006_mappy;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Transaction;
import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ItemTransactionAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    private Context context;

//    private List<ItemTransaction> transactions;
    private ItemTransactionViewModel mItemTransactionViewModel;
    private ItemViewModel mItemViewModel;
    private List<Item> items;


    public ItemTransactionAdapter(Context context, List<Item> items) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.items = items;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = mInflater.inflate(R.layout.mypurchase, null);

            // set image based on selected text
//            ImageView imageView = (ImageView) gridView
//                    .findViewById(R.id.grid_item_image);

//            imageView.setImageResource(R.drawable.ic_menu_camera);
//
            TextView itemName = (TextView) gridView.findViewById(R.id.grid_item_name_my_purchases);
            TextView seller_Username = (TextView) gridView.findViewById(R.id.grid_item_seller_username_my_purchases);
            TextView itemId = (TextView) gridView.findViewById(R.id.grid_item_id_my_purchases);
            TextView itemPrice = (TextView) gridView.findViewById(R.id.grid_item_price_my_purchases);
//            TextView transactionId = (TextView) gridView.findViewById(R.id.grid_item_transaction_id_my_purchases);

            String item_name = items.get(position).getItem_name();
            int item_id = items.get(position).getItem_id();
            String seller_username = items.get(position).getSeller_username();
            Double price = items.get(position).getPrice();

            itemName.setText(item_name);
            itemId.setText(item_id);
            seller_Username.setText(seller_username);
            itemPrice.setText(Double.toString(price));


//            int transaction_id = transactions.get(position).getTransaction_id();
//            String seller_username = transactions.get(position).getSeller_username();
//            List<Integer> items_id= mItemTransactionViewModel.getItemTransaction("gabriella");
//            for(int i =0; i< items_id.size(); i++){
//                int item_id = items_id.get(i);
//                Item todisplay = mItemViewModel.getItem(item_id);
//                itemName.setText(todisplay.getItem_name());
//                seller_Username.setText(seller_username);
//                itemId.setText(todisplay.getItem_id());
//                itemPrice.setText(Double.toString(todisplay.getPrice()));
//                transactionId.setText(Double.toString(transaction_id));
//            }

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
