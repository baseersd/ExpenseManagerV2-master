package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import expmanager.idea.spark.in.expensemanager.R;

/**
 * Created by Creative IT Works on 31-Mar-17.
 */

public class ListAdapter extends BaseAdapter {
    ArrayList<String> name;
    ArrayList<String> amount;
    Context mContext;

    //constructor
    public ListAdapter(Context mContext, ArrayList<String> name, ArrayList<String> amount) {
        this.mContext = mContext;
        this.name = name;
        this.amount = amount;

        Log.i("SSSSSSSSSSS","SSSSSSS"+name.size()+amount.size());
    }

    public int getCount() {
        return name.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listadapter, viewGroup, false);

        TextView expTitle = (TextView) row.findViewById(R.id.name);
        TextView expAmt = (TextView) row.findViewById(R.id.price);
        TextView ids = (TextView) row.findViewById(R.id.ids);


        expTitle.setText(name.get(position));
        ids.setText(String.valueOf(position+1));
        try {
            expAmt.setText(amount.get(position));
        }catch (Exception e)
        {

        }



        return row;
    }

}
