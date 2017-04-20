package expmanager.idea.spark.in.expensemanager.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import expmanager.idea.spark.in.expensemanager.R;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class ExpenseChildViewHolder extends ChildViewHolder {

    private CustomFonts childName;
    private TextView childQuantity;
    private TextView childCost;
    private LinearLayout llExpenseChild;

    public ExpenseChildViewHolder(View itemView) {
        super(itemView);
        llExpenseChild = (LinearLayout) itemView.findViewById(R.id.ll_expense_child);
        childName = (CustomFonts) itemView.findViewById(R.id.name);
        childQuantity = (TextView) itemView.findViewById(R.id.quantity);
        childCost = (TextView) itemView.findViewById(R.id.cost);
    }

    public void setDetails(Context context, String name, String quantity, String cost, boolean isApproved) {
        childName.setText(name);
        childQuantity.setText(quantity);
        childCost.setText(cost);
        if(isApproved){
            llExpenseChild.setBackgroundColor(context.getResources().getColor(R.color.blue));
        }else{
            llExpenseChild.setBackgroundColor(context.getResources().getColor(R.color.grey05));
        }
    }
}