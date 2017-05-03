package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by Baseer on 4/6/2017.
 */

public class Item {

    public static final int ITEM_CATEGORY = 0;
    public static final int ITEM_PRODUCT = 1;

    private int type;
    private int itemId;
    private String itemName;
    private String itemURL;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Item(){

    }

    public Item(int id, int type, String name, String url){
        this.type = type;
        this.itemId = id;
        this.itemName = name;
        this.itemURL = url;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }
}
