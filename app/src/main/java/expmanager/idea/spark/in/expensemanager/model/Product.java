package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Baseer on 4/13/2017.
 */

public class Product implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("company_id")
    private String companyId;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("product_image_url")
    private String productImageUrl;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
