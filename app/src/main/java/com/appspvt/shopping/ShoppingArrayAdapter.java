package com.appspvt.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ShoppingArrayAdapter extends ArrayAdapter<ShoppingItem> {

    public ShoppingArrayAdapter(Context context, List<ShoppingItem> shoppingItemList){
        super(context,0,shoppingItemList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View shoppingListItemView = convertView;
        if(shoppingListItemView == null) {
            shoppingListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.shopping_list_item, parent, false);
        }

        ShoppingItem shoppingItem = getItem(position);
        assert shoppingItem != null;
        TextView itemName = shoppingListItemView.findViewById(R.id.item_name);
        itemName.setText(shoppingItem.getItemName());

        TextView quantity = shoppingListItemView.findViewById(R.id.quantity);
        quantity.setText(shoppingItem.getQuantity());

        return shoppingListItemView;

    }
}
