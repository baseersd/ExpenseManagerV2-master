package expmanager.idea.spark.in.expensemanager.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseSyncRequest;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class ExpenseTitleViewHolder extends GroupViewHolder {

    private LinearLayout llExpenseGroup;
    private TextView childTitle;
    private TextView childItems;
    private TextView childCost;
    private TextView approve;
    private ImageView arrow;
    private OnApprovePress mCallback;
    private ExpenseGroup mGroupObj;

    public interface OnApprovePress{
        public void onApproveBtnClick(ExpenseSyncRequest syncRequest);
    }
    public ExpenseTitleViewHolder(View itemView, OnApprovePress callback) {
        super(itemView);
        mCallback = callback;
        llExpenseGroup = (LinearLayout)itemView.findViewById(R.id.ll_expense_group);
        childTitle = (TextView) itemView.findViewById(R.id.title);
        childItems = (TextView) itemView.findViewById(R.id.items);
        childCost = (TextView) itemView.findViewById(R.id.cost);
        approve = (TextView) itemView.findViewById(R.id.approve);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.onApproveBtnClick(mGroupObj.getExpObj());
                }
            }
        });
        arrow = (ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
    }

    public void setDetails(Context context, ExpenseGroup groupObj, String name, String quantity, String cost, boolean isApproved) {
        childTitle.setText(name);
        childItems.setText(quantity);
        childCost.setText(cost);
        mGroupObj = groupObj;
        if(isApproved){
            approve.setVisibility(View.INVISIBLE);
            llExpenseGroup.setBackgroundColor(context.getResources().getColor(R.color.blue));
        }else{
            approve.setVisibility(View.VISIBLE);
            llExpenseGroup.setBackgroundColor(context.getResources().getColor(R.color.grey05));
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    public ExpenseGroup getGroupObj() {
        return mGroupObj;
    }

    public void setGroupObj(ExpenseGroup mGroupObj) {
        this.mGroupObj = mGroupObj;
    }
}

