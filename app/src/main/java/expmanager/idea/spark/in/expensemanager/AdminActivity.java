package expmanager.idea.spark.in.expensemanager;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import expmanager.idea.spark.in.expensemanager.fragments.AdminAddStaffFragment;
import expmanager.idea.spark.in.expensemanager.fragments.AdminProfileFragment;
import expmanager.idea.spark.in.expensemanager.fragments.AdminTangibleExpenses;
import expmanager.idea.spark.in.expensemanager.fragments.DashBoardFragment;
import expmanager.idea.spark.in.expensemanager.fragments.ExpenseFragment;
import expmanager.idea.spark.in.expensemanager.fragments.ExpenseHistoryFragment;
import expmanager.idea.spark.in.expensemanager.fragments.InvoiceEntryFragment;
import expmanager.idea.spark.in.expensemanager.fragments.ReportsFragment;
import expmanager.idea.spark.in.expensemanager.fragments.SalesFragment;
import expmanager.idea.spark.in.expensemanager.fragments.fragExpenseEntry;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;


/**
 * Created by kveldurty on 2/21/17.
 */


public class AdminActivity extends AppCompatActivity implements View.OnClickListener,
        InvoiceEntryFragment.OnFragmentInteractionListener, fragExpenseEntry.OnFragmentInteractionListener {
    private TextView tvTanexpense,tvStaff, tvSales;
    private TextView tvExpense, tvHistory, tvDashboard, tvProfile;
    private static TextView tvReports;
    private static View oldSelectedView = null;
    private Typeface typeface;
    private static Context mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        initializeControls();
        /*ExpenseFragment fragmentorg = new ExpenseFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragmentorg).commit();
*/
        mActivity = this;
        tvExpense.callOnClick();
    }

    private void initializeControls() {

        typeface = Typeface.createFromAsset(this.getAssets(),
                "fontawesome.ttf");

        tvExpense = (TextView) findViewById(R.id.btnexpense);
        tvExpense.setTypeface(typeface);
        tvExpense.setOnClickListener(this);

        tvTanexpense = (TextView) findViewById(R.id.btntanexpense);
        tvTanexpense.setTypeface(typeface);
        tvTanexpense.setOnClickListener(this);

        tvHistory = (TextView) findViewById(R.id.btnhistory);
        tvHistory.setTypeface(typeface);
        tvHistory.setOnClickListener(this);

        tvDashboard = (TextView) findViewById(R.id.btndashboard);
        tvDashboard.setTypeface(typeface);
        tvDashboard.setOnClickListener(this);

        tvStaff = (TextView) findViewById(R.id.btnstaff);
        tvStaff.setTypeface(typeface);
        tvStaff.setOnClickListener(this);

        tvProfile = (TextView) findViewById(R.id.btnprofile);
        tvProfile.setTypeface(typeface);
        tvProfile.setOnClickListener(this);

        tvSales = (TextView) findViewById(R.id.btnsales);
        tvSales.setTypeface(typeface);
        tvSales.setOnClickListener(this);

        tvReports = (TextView) findViewById(R.id.btnreports);
        tvReports.setTypeface(typeface);
        tvReports.setOnClickListener(this);

        SessionManager sessionManager = new SessionManager(AdminActivity.this);

        if(!sessionManager.isApproved()){

            tvReports.setVisibility(View.GONE);
            tvSales.setVisibility(View.GONE);
            tvStaff.setVisibility(View.GONE);

        }else {

            tvReports.setVisibility(View.VISIBLE);
            tvSales.setVisibility(View.VISIBLE);
            tvStaff.setVisibility(View.VISIBLE);

        }

    }

    public static  void updateViewHighlight(int viewId){
        View view = null;
        switch (viewId){
            case R.id.btnreports:
                view = tvReports;
                break;
        }
        if(oldSelectedView != null){
            oldSelectedView.setBackgroundColor(mActivity.getResources().getColor(android.R.color.transparent));
        }
        view.setBackgroundColor(mActivity.getResources().getColor(R.color.blue1));
        oldSelectedView = view;
    }

    @Override
    public void onClick(View v) {
        if(oldSelectedView != null){
            oldSelectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        v.setBackgroundColor(getResources().getColor(R.color.blue1));
        oldSelectedView = v;

        switch (v.getId()) {
            case R.id.btnexpense:
                ExpenseFragment fragmentorg = new ExpenseFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragmentorg).commit();

                break;
            case R.id.btnprofile:
                AdminProfileFragment fragprofile = new AdminProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragprofile).commit();
                break;

            case R.id.btndashboard:
                DashBoardFragment fragdashboard = new DashBoardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragdashboard).commit();
                break;
            case R.id.btntanexpense:
                AdminTangibleExpenses fragtanexp = new AdminTangibleExpenses();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragtanexp).commit();
                break;
            case R.id.btnstaff:
                AdminAddStaffFragment fragstaff= new AdminAddStaffFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragstaff).commit();
                break;

            case R.id.btnhistory:
                ExpenseHistoryFragment fragExpenseHistory = new ExpenseHistoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragExpenseHistory).commit();

                break;

            case R.id.btnsales:
                SalesFragment fragsales = new SalesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragsales).commit();

                break;

            case R.id.btnreports:

                ReportsFragment reportsFragment = new ReportsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, reportsFragment).commit();

                break;


        }

    }

    @Override
    public void openExpenseEntry(String invid, String invDate, double invAmt, int weekIndex, String filePath) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragExpenseEntrys = fragExpenseEntry.newInstance(weekIndex, invAmt, invid, invDate,filePath);
        ft.replace(R.id.admin_content_frame, fragExpenseEntrys);
        ft.commit();
    }
}
