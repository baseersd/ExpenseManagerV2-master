package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.common.RuntimeData;
import expmanager.idea.spark.in.expensemanager.model.CategoryItem;
import expmanager.idea.spark.in.expensemanager.model.ScanInvoiceModel;

/**
 * Created by Creative IT Works on 31-Mar-17.
 */

public class ListAdapter extends BaseAdapter {
   private  ArrayList<ScanInvoiceModel> scanInvoiceModels;
    private Context mContext;
    private  List<String> categoryList;
    //constructor
    public ListAdapter(Context mContext, ArrayList<ScanInvoiceModel> scanInvoiceModels) {
        this.mContext = mContext;
        this.scanInvoiceModels = scanInvoiceModels;

        categoryList = new ArrayList<>();
        for(CategoryItem categoryItem: RuntimeData.getCatelogList()) {
            // Initialize a new Adapter for RecyclerView

            categoryList.add(categoryItem.getCategory().getCategoryName());

        }


    }

    public int getCount() {
        return scanInvoiceModels.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listadapter, viewGroup, false);

        EditText expTitle = (EditText) row.findViewById(R.id.name);
        EditText expAmt = (EditText) row.findViewById(R.id.price);
        EditText qty= (EditText) row.findViewById(R.id.quantity);
        Spinner category= (Spinner) row.findViewById(R.id.category);
        TextView ids = (TextView) row.findViewById(R.id.ids);
        ImageView action=(ImageView)row.findViewById(R.id.action);

        expTitle.setText(scanInvoiceModels.get(position).getProductName());
        ids.setText(String.valueOf(position+1));
        try {
            expAmt.setText(scanInvoiceModels.get(position).getPrice());
        }catch (Exception e)
        {

        }

        scanInvoiceModels.get(position).setCategory(categoryList.get(0));

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanInvoiceModels.remove(position);
                notifyDataSetChanged();
            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                scanInvoiceModels.get(position).setCategory(categoryList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        return row;
    }

}
