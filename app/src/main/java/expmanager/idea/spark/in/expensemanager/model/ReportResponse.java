package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RamanaRedddy on 4/16/17.
 */

public class ReportResponse implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("sum")
    private double sum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
