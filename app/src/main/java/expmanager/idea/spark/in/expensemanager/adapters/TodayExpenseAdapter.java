package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseItem;
import expmanager.idea.spark.in.expensemanager.utils.ExpenseChildViewHolder;
import expmanager.idea.spark.in.expensemanager.utils.ExpenseTitleViewHolder;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class TodayExpenseAdapter extends ExpandableRecyclerViewAdapter<ExpenseTitleViewHolder, ExpenseChildViewHolder> {

    private Context mContext;
    private ExpenseTitleViewHolder.OnApprovePress mCallback;
    public TodayExpenseAdapter(Context context, List<? extends ExpandableGroup> groups, ExpenseTitleViewHolder.OnApprovePress callback) {
        super(groups);
        this.mContext = context;
        this.mCallback = callback;

    }

    @Override
    public ExpenseTitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_group, parent, false);
        return new ExpenseTitleViewHolder(view, mCallback);
    }

    @Override
    public ExpenseChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_child, parent, false);
        return new ExpenseChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ExpenseChildViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final ExpenseItem expenseItem = ((ExpenseItem) group.getItems().get(childIndex));
        holder.setDetails(mContext,expenseItem.getName(),expenseItem.getQuantity(),expenseItem.getCost(),expenseItem.isApproved());
    }

    @Override
    public void onBindGroupViewHolder(ExpenseTitleViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        ExpenseGroup expenseGroup = (ExpenseGroup) group;

        holder.setDetails(mContext, expenseGroup,expenseGroup.getTitle(),expenseGroup.getCount(),expenseGroup.getTotalCost(), expenseGroup.isApproved());
    }
}
