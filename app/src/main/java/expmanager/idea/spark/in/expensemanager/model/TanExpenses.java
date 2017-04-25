package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Haresh.Veldurty on 2/23/2017.
 */

public class TanExpenses implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("company_id")
    private int company_id;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("expense_name")
    private String  category;

    @SerializedName("expense_type")
    private String when;

    @SerializedName("amount")
    private double price;

    public TanExpenses(String s, String s1, String s2) {
        this.category = s;
        this.when = s1;
        this.price=Double.valueOf(s2);
    }

    public TanExpenses(String s, String s1, double s2) {
        this.category = s;
        this.when = s1;
        this.price=s2;
    }
    public TanExpenses() {

    }



    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return ""+price;
    }

    public void setPrice(String price) {
        this.price = Double.valueOf(price);
    }

    public double getPriceDouble() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
