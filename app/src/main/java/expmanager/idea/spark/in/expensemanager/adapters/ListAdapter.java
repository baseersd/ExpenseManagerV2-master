package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        EditText expTitle = (EditText) row.findViewById(R.id.name);
        EditText expAmt = (EditText) row.findViewById(R.id.price);
        EditText qty= (EditText) row.findViewById(R.id.quantity);
        Spinner category= (Spinner) row.findViewById(R.id.category);
        TextView ids = (TextView) row.findViewById(R.id.ids);
        ImageView action=(ImageView)row.findViewById(R.id.action);

        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);

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
