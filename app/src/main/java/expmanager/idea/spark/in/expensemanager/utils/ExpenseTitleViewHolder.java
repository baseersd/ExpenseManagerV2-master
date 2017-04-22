package expmanager.idea.spark.in.expensemanager.utils;

import android.content.Context;
import android.graphics.Typeface;
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
    private TextView reject;
    private TextView viewInvoice;
    private TextView arrow;
    private OnApprovePress mCallback;
    private ExpenseGroup mGroupObj;
    private boolean isExpanded = true;

    public interface OnApprovePress{
        public void onApproveBtnClick(ExpenseSyncRequest syncRequest);
        public void onViewInvoiceBtnClick(ExpenseSyncRequest syncRequest);
    }
    public ExpenseTitleViewHolder(Context context, View itemView, OnApprovePress callback) {
        super(itemView);
        mCallback = callback;
        Typeface fontAwesomeFont = Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf");
        llExpenseGroup = (LinearLayout)itemView.findViewById(R.id.ll_expense_group);
        childTitle = (TextView) itemView.findViewById(R.id.title);
        childItems = (TextView) itemView.findViewById(R.id.items);
        childCost = (TextView) itemView.findViewById(R.id.cost);
        approve = (TextView) itemView.findViewById(R.id.approve);
        approve.setTypeface(fontAwesomeFont);
        reject = (TextView) itemView.findViewById(R.id.reject);
        reject.setTypeface(fontAwesomeFont);
        viewInvoice = (TextView) itemView.findViewById(R.id.view_invoice);
        viewInvoice.setTypeface(fontAwesomeFont);
        viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.onViewInvoiceBtnClick(mGroupObj.getExpObj());
                }
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.onApproveBtnClick(mGroupObj.getExpObj());
                }
            }
        });
        arrow = (TextView) itemView.findViewById(R.id.list_item_genre_arrow);
        arrow.setTypeface(fontAwesomeFont);
    }

    public void setDetails(Context context, ExpenseGroup groupObj, String name, String quantity, String cost, boolean isApproved) {
        childTitle.setText(name);
        childItems.setText(quantity);
        childCost.setText(cost);
        mGroupObj = groupObj;
        if(isApproved){
            approve.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
            llExpenseGroup.setBackgroundColor(context.getResources().getColor(R.color.blue));
        }else{
            approve.setVisibility(View.VISIBLE);
            reject.setVisibility(View.VISIBLE);
            llExpenseGroup.setBackgroundColor(context.getResources().getColor(R.color.grey05));
        }

        /*if(mGroupObj.getExpObj().getInvoice().getInvImgPath() != null){
            viewInvoice.setVisibility(View.VISIBLE);
        }else{
            viewInvoice.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void expand() {
        //animateExpand();
        isExpanded = true;
        arrow.setText(R.string.fa_arrow_down);
    }

    @Override
    public void collapse() {
        //animateCollapse();
        isExpanded = false;
        arrow.setText(R.string.fa_arrow_up);
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

