package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.Item;

/**
 * Created by Baseer on 4/6/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItemList;
    private OnItemClickListener mCallback;

    public interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }

    public CategoriesAdapter(Context context, List<Item> list, OnItemClickListener callback){
        this.mContext = context;
        this.mItemList = list;
        this.mCallback = callback;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_list_item,parent,false);
        ViewHolder vh = new ViewHolder(v);

        // Return the ViewHolder
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mItemList.get(position).getItemName());
        Picasso.with(mContext)
                .load(mItemList.get(position).getItemURL())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImageView);
        holder.bind(mItemList.get(position),mCallback, position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLLCategory;
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            // Get the widget reference from the custom layout
            mLLCategory = (LinearLayout)v.findViewById(R.id.ll_category_item);
            mImageView = (ImageView) v.findViewById(R.id.imv_category_icon);
            mTextView = (TextView) v.findViewById(R.id.tv_category_name);
        }
        public void bind(final Item item, final OnItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });
        }
    }
}
