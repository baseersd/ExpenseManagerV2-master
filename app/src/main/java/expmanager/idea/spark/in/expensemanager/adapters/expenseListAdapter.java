package expmanager.idea.spark.in.expensemanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.Expense;


public class expenseListAdapter extends BaseAdapter {

    ArrayList<Expense> listItem;
    DatabaseHandler mDbhelper;
    Context mContext;
    ListView parent;
    private OnCustomItemClick mCallback;
    private String mUnitCountString;
    private String mAmountString;

    public interface OnCustomItemClick{
        public void onItemClick(View view, ArrayList<Expense> itemList, int position, long id);
    }
    //constructor
    public expenseListAdapter(Context mContext, ArrayList<Expense> listItem,ListView lv, OnCustomItemClick callback) {
        this.mContext = mContext;
        this.listItem = listItem;
        mDbhelper = new DatabaseHandler(mContext);
        this.parent = lv;
        this.mCallback = callback;
    }

    public int getCount() {
        return listItem.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View arg1, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.lstexpitems, viewGroup, false);

        TextView lblcat = (TextView) row.findViewById(R.id.lblcat);
        TextView lbldesc = (TextView) row.findViewById(R.id.lbldesc);
        final EditText lblunit = (EditText) row.findViewById(R.id.lblunit);
        lblunit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUnitCountString = s.toString();
            }
        });
        EditText lblamt = (EditText) row.findViewById(R.id.lblamt);
        lblamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAmountString = s.toString();
                mAmountString = mAmountString.replace("$","");
            }
        });
        TextView btnexpedit = (TextView)row.findViewById(R.id.btnexpedit);
        btnexpedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((ListView) parent).performItemClick(v, position, listItem.get(position),listItem.get(position).getExpid());
                listItem.get(position).setExpUnit(Integer.parseInt(mUnitCountString));
                listItem.get(position).setExpAmt(Double.parseDouble(mAmountString));
                mCallback.onItemClick(v,listItem,position,listItem.get(position).getExpid());
            }
        });
        TextView btnexpdel = (TextView) row.findViewById(R.id.btnexpdelete);
        btnexpdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position,listItem.get(position).getExpid());
                //mCallback.onItemClick(v,listItem.get(position),position,listItem.get(position).getExpid());
            }
        });

        mDbhelper.openConnection();
        //lblcat.setText(mDbhelper.getCatName(listItem.get(position).getExpCatId()));
        lblcat.setText(listItem.get(position).getCategory_name());
        mDbhelper.closeConnection();
        lbldesc.setText(listItem.get(position).getExpProductName());
        lblunit.setText("" + listItem.get(position).getExpUnit());
        lblamt.setText("$" + String.format("%.2f", listItem.get(position).getExpAmt()));

        row.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#b2beb5"));

        return row;
    }
}

