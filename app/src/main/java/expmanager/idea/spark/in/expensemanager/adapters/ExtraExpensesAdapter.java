package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.Item;
import expmanager.idea.spark.in.expensemanager.model.TanExpenses;

/**
 * Created by Baseer on 5/3/2017.
 */

public class ExtraExpensesAdapter extends RecyclerView.Adapter<ExtraExpensesAdapter.ViewHolder>{

    private Context mContext;
    private List<TanExpenses> mExtraExpenseList;
    private Typeface typeface;

    public ExtraExpensesAdapter(Context context, List<TanExpenses> extraExpenseList){
        this.mContext = context;
        this.mExtraExpenseList = extraExpenseList;
        typeface = Typeface.createFromAsset(mContext.getAssets(),
                "fontawesome.ttf");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.extra_expenses_item_layout,parent,false);
        ExtraExpensesAdapter.ViewHolder vh = new ExtraExpensesAdapter.ViewHolder(v);

        // Return the ViewHolder
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.setText((position+1)+". "+mExtraExpenseList.get(position).getCategory());
        holder.inc_dec.setText(mContext.getString(R.string.fa_arrow_up));
        holder.inc_dec.setTypeface(typeface);
        holder.amount.setText("$"+mExtraExpenseList.get(position).getPriceDouble());

    }

    @Override
    public int getItemCount() {
        return mExtraExpenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public TextView inc_dec;
        public TextView amount;

        public ViewHolder(View v) {
            super(v);
            // Get the widget reference from the custom layout
            item = (TextView)v.findViewById(R.id.tv_item);
            inc_dec = (TextView) v.findViewById(R.id.tv_inc_dec);
            amount = (TextView) v.findViewById(R.id.tv_item_amount);
        }
    }
}
