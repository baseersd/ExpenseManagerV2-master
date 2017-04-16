package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Baseer on 4/15/2017.
 */

public class ExpenseHistoryResponse implements Serializable {

    @SerializedName("expenseHistoryList")
    private List<ExpenseSyncRequest> expenseHistoryList;

    public List<ExpenseSyncRequest> getExpenseHistoryList() {
        return expenseHistoryList;
    }

    public void setExpenseHistoryList(List<ExpenseSyncRequest> expenseHistoryList) {
        this.expenseHistoryList = expenseHistoryList;
    }
}
