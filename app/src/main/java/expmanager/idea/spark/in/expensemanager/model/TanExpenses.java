package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Haresh.Veldurty on 2/23/2017.
 */

public class TanExpenses implements Serializable{

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



}
