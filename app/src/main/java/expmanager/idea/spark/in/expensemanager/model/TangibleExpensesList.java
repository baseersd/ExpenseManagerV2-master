package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Baseer on 4/20/2017.
 */

public class TangibleExpensesList implements Serializable{

    @SerializedName("tangibleExpensesList")
    private List<TanExpenses> mTangibleExpensesList;

    public List<TanExpenses> getTangibleExpensesList() {
        return mTangibleExpensesList;
    }

    public void setTangibleExpensesList(List<TanExpenses> mTangibleExpensesList) {
        this.mTangibleExpensesList = mTangibleExpensesList;
    }
}
