package expmanager.idea.spark.in.expensemanager.common;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.model.TanExpenses;
import expmanager.idea.spark.in.expensemanager.model.TangibleExpensesList;


public class RuntimeData {

public static TangibleExpensesList mTagibleExpenseList;

    public List<TanExpenses> getTagibleExpenseList() {
        return mTagibleExpenseList.getTangibleExpensesList();
    }

    public void setTagibleExpenseList(TangibleExpensesList mTagibleExpenseList) {
        this.mTagibleExpenseList = mTagibleExpenseList;
    }
}
