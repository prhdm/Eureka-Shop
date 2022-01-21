package data;

import java.util.LinkedList;
import java.util.List;

public class Items {
    public final String seller;
    public final String itemName;
    public String price;
    public final String tag;
    public static  final List<Items> items = new LinkedList<>();
    public Items(String seller , String itemName , String price ,String tag) {
        this.seller = seller;
        this.itemName = itemName;
        this.price = price;
        this.tag = tag;
    }
    public static void addItem(Items i) { items.add(i);}
    public static void removeItem(Items i) {items.remove(i);}

    public static Items getSeller(String seller) {
        Items i = null;
        for (Items item : items) {
            if (item.seller.equals(seller)) {
                i = item;
            }
        }
        return i;
    }

    public static Items getItem(String seller) {
        Items i = null;
        for (Items item : items) {
            if (item.itemName.equals(seller)) {
                i = item;
            }
        }
        return i;
    }
}
