package expmanager.idea.spark.in.expensemanager.common;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.model.CategoryItem;
import expmanager.idea.spark.in.expensemanager.model.TanExpenses;
import expmanager.idea.spark.in.expensemanager.model.TangibleExpensesList;


public class RuntimeData {

    private static TangibleExpensesList mTagibleExpenseList;
    private static List<CategoryItem> mCatelogList;

    public static List<TanExpenses> getTagibleExpenseList() {
        return mTagibleExpenseList.getTangibleExpensesList();
    }

    public static void setTagibleExpenseList(TangibleExpensesList tagibleExpenseList) {
        mTagibleExpenseList = tagibleExpenseList;
    }

    public static List<CategoryItem> getCatelogList() {
        return mCatelogList;
    }

    public static void setCatelogList(List<CategoryItem> mCatelogList) {
        RuntimeData.mCatelogList = mCatelogList;
    }
}
