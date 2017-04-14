package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Baseer on 4/14/2017.
 */

public class CategoryItem implements Serializable{

    @SerializedName("category")
    private Category category;

    @SerializedName("products")
    private List<Product> products = null;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
