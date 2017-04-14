package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Baseer on 4/13/2017.
 */

public class ProductListResponse implements Serializable{

    @SerializedName("productList")
    private List<CategoryItem> productList;

    public List<CategoryItem> getProductList() {
        return productList;
    }

    public void setProductList(List<CategoryItem> productList) {
        this.productList = productList;
    }
}
