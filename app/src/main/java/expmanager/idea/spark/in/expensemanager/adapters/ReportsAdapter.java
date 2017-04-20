package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.ReportResponse;

/**
 * Created by Baseer on 4/6/2017.
 */

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private Context mContext;
    private List<ReportResponse> mItemList;


    public ReportsAdapter(Context context, List<ReportResponse> list){
        this.mContext = context;
        this.mItemList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.reports_list_item,parent,false);
        ViewHolder vh = new ViewHolder(v);

        // Return the ViewHolder
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtSno.setText(""+position+1);
        holder.txtCategoryName.setText(mItemList.get(position).getCategoryName());
        holder.txtProductName.setText(mItemList.get(position).getProductName());
        holder.txtSum.setText(""+mItemList.get(position).getSum());
        //holder.bind(mItemList.get(position),mCallback, position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSno;
        public TextView txtCategoryName;
        public TextView txtProductName;
        public TextView txtSum;

        public ViewHolder(View v) {
            super(v);
            // Get the widget reference from the custom layout
            txtSno = (TextView)v.findViewById(R.id.txt_s_no);
            txtCategoryName = (TextView) v.findViewById(R.id.tv_category_name);
            txtSum = (TextView) v.findViewById(R.id.tv_sum);
            txtProductName = (TextView) v.findViewById(R.id.tv_product_name);
        }
//        public void bind(final Item item, final OnItemClickListener listener, final int position) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    if(item.getType() == Item.ITEM_CATEGORY){
//                        if(olderSelectedViewCategory != null) {
//                            olderSelectedViewCategory.setSelected(false);
//                        }
//                        olderSelectedViewCategory = v;
//                    }else{
//                        if(olderSelectedViewProduct != null) {
//                            olderSelectedViewProduct.setSelected(false);
//                        }
//                        olderSelectedViewProduct = v;
//                    }
//                    v.setSelected(true);
//                    listener.onItemClick(item, position);
//                }
//            });
//        }
    }
}
