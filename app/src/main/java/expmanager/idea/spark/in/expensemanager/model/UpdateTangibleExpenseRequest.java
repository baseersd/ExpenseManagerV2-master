package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramana.Reddy on 4/25/2017.
 */

public class UpdateTangibleExpenseRequest {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("tangible_expense")
    @Expose
    private AddTangibleExpenseRequest tangible_expense;

    public UpdateTangibleExpenseRequest(int id,AddTangibleExpenseRequest tangible_expense){

        this.id = id;
        this.tangible_expense =tangible_expense;

    }
}
