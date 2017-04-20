package expmanager.idea.spark.in.expensemanager.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class ExpenseGroup extends ExpandableGroup<ExpenseItem> {

    private String name;
    private String count;
    private String totalCost;
    private boolean isApproved;
    private ExpenseSyncRequest mExpObj;

    public ExpenseGroup(ExpenseSyncRequest expenseObj, String title, List<ExpenseItem> items, String count,String totalCost, boolean isApproved) {
        super(title, items);
        this.count = count;
        this.totalCost = totalCost;
        this.isApproved = isApproved;
        mExpObj = expenseObj;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public ExpenseSyncRequest getExpObj() {
        return mExpObj;
    }

    public void setExpObj(ExpenseSyncRequest mExpObj) {
        this.mExpObj = mExpObj;
    }
    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ExpanseGroup)) return false;
//
//        ExpanseGroup genre = (ExpanseGroup) o;
//
//        return getIconResId() == genre.getIconResId();
//
//    }
//
//    @Override
//    public int hashCode() {
//        return getIconResId();
//    }
}
