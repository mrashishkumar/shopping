package com.appspvt.shopping;

public class ShoppingItem {

    private String itemName;
    private String quantity;

   public ShoppingItem(){
       // Empty constructor required
    }

    public ShoppingItem(String itemName, String quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }
}
