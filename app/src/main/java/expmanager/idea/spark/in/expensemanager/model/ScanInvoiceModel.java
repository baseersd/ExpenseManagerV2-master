package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by Ramana.Reddy on 5/15/2017.
 */

public class ScanInvoiceModel {

    private String category="";
    private String productName="";
    private int quantity=0;
    private String price="";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
