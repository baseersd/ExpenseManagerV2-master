package expmanager.idea.spark.in.expensemanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.TodayExpenseAdapter;
import expmanager.idea.spark.in.expensemanager.common.AppConstants;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.Expense;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseItem;
import expmanager.idea.spark.in.expensemanager.model.Invoice;
import expmanager.idea.spark.in.expensemanager.utils.Utils;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class ExpenseFragment extends Fragment implements View.OnClickListener {

    public TodayExpenseAdapter adapter;
    public TodayExpenseAdapter weekAdapter;
    private ImageView imgAddocrExpense, addexpbtn;
    private ImageView imgArrow;
    private RelativeLayout main_layout;
    private DatabaseHandler myDbHelper;
    private TextView mEmptyTodayExpense, mEmptyWeekExpense;

    int flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense1,
                container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();

        final int convertWidth = (int) (width*0.65);

        myDbHelper = new DatabaseHandler(getContext());

        imgAddocrExpense = (ImageView) rootView.findViewById(R.id.img_ocr_expense);
        imgAddocrExpense.setOnClickListener(this);

        //drawableInitialasation(rootView);

        addexpbtn = (ImageView) rootView.findViewById(R.id.img_add_expense);
        addexpbtn.setOnClickListener(this);

        imgArrow = (ImageView) rootView.findViewById(R.id.img_arrow);

        main_layout = (RelativeLayout) rootView.findViewById(R.id.parent_main_layout);

        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 0) {
                    imgArrow.setImageResource(R.drawable.right_arrow);
                    main_layout.getLayoutParams().width = convertWidth;
                    main_layout.requestLayout();
                    flag = 1;

                } else {
                    imgArrow.setImageResource(R.drawable.left_arrow);
                    main_layout.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    main_layout.requestLayout();
                    flag = 0;

                }

            }
        });

        RecyclerView recyclerViewToday = (RecyclerView) rootView.findViewById(R.id.recycler_view_todays);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mEmptyTodayExpense = (TextView) rootView.findViewById(R.id.tv_empty_list_today);
        mEmptyWeekExpense = (TextView) rootView.findViewById(R.id.tv_empty_list_week_expense);

        RecyclerView recyclerViewWeek = (RecyclerView) rootView.findViewById(R.id.recycler_view_week);
        LinearLayoutManager layoutManagerWeek = new LinearLayoutManager(getActivity());

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerViewToday.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        List<ExpenseGroup> todaysExpenseList = makeExpansesList();
        adapter = new TodayExpenseAdapter(todaysExpenseList);
        recyclerViewToday.setLayoutManager(layoutManager);
        recyclerViewToday.setAdapter(adapter);
        mEmptyTodayExpense.setVisibility(todaysExpenseList.size() == 0? View.VISIBLE : View.GONE);


        List<ExpenseGroup> weeksExpenseList = makeWeekExpensesList(Utils.getCurrentWeekofYear());
        weekAdapter = new TodayExpenseAdapter(weeksExpenseList);
        recyclerViewWeek.setLayoutManager(layoutManagerWeek);
        recyclerViewWeek.setAdapter(weekAdapter);
        mEmptyWeekExpense.setVisibility(weeksExpenseList.size() == 0? View.VISIBLE : View.GONE);

        return rootView;
    }

//    private void drawableInitialasation(View rootView) {
//
//
//        TextView textView = (TextView) rootView.findViewById(R.id.doller_img);
//
//       textView.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
//    }

    public List<ExpenseItem> makeExpanseItems() {
        ExpenseItem item1 = new ExpenseItem("Olive oil", "2 lt", "$10.00");
        ExpenseItem item2 = new ExpenseItem("Sugar", "5 kg", "$20.00");
        ExpenseItem item3 = new ExpenseItem("Carrots", "5 kg", "$10.00");

        getExpenseListforDate(Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY));
        return Arrays.asList(item1, item2, item3);
    }

    public List<ExpenseItem> makeExpenseItems(List<Expense> expensesList){
        List<ExpenseItem> expenseList = new ArrayList<>();
        for(Expense expense : expensesList){
            String productCost = getString(R.string.currencysymbol) + expense.getExpAmt();
            ExpenseItem expenseItem = new ExpenseItem(expense.getExpProductName(),
                    String.valueOf(expense.getExpUnit()),productCost);
            expenseList.add(expenseItem);
        }
        return expenseList;
    }

    private List<ExpenseGroup> getExpenseListforDate(String date){
        List<ExpenseGroup> expenseList = new ArrayList<>();
        myDbHelper.openConnection();
        try {
            List<String> categoriesForToday = myDbHelper.getExpenseNameforDate("01-04-2017");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            myDbHelper.closeConnection();
        }
        return expenseList;
    }
    private List<ExpenseGroup> makeExpansesList() {

        List<ExpenseGroup> expenseGroupList = new ArrayList<>();
        myDbHelper.openConnection();
        List<Invoice> invoicesListForToday = null;
        try {
            /*categoriesForToday = myDbHelper.getExpenseNameforDate(
                    Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY));*/
            invoicesListForToday = myDbHelper.getAllInvoicesForToday();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            myDbHelper.closeConnection();
        }

        for(Invoice invoice: invoicesListForToday){
            ExpenseGroup expenseGroup = makeExpenseGroup(invoice.getInvDesc(),
                    invoice.getInvBillNumber(), invoice.getInvAmt());
            expenseGroupList.add(expenseGroup);
        }

        return expenseGroupList;
    }

    private List<ExpenseGroup> makeWeekExpensesList(int weekIndex){
        List<ExpenseGroup> expenseGroupList = new ArrayList<>();
        myDbHelper.openConnection();
        List<Invoice> invoicesListForWeek = null;
        try {
            /*categoriesForToday = myDbHelper.getExpenseNameforDate(
                    Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_DD_MM_YYYY));*/
            invoicesListForWeek = myDbHelper.getAllInvoicesForWeek(weekIndex);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            myDbHelper.closeConnection();
        }

        for(Invoice invoice: invoicesListForWeek){
            ExpenseGroup expenseGroup = makeExpenseGroup(invoice.getInvDesc(),
                    invoice.getInvBillNumber(), invoice.getInvAmt());
            expenseGroupList.add(expenseGroup);
        }

        return expenseGroupList;
    }

    public ExpenseGroup makeExpanseGroup() {
        return new ExpenseGroup("Grocery Today", makeExpanseItems(), "3 items", "$40.00");
    }

    public ExpenseGroup makeExpenseGroup(String expenseName, String invoiceId, double invoiceAmount){

        StringBuilder expenseCost = new StringBuilder();
        expenseCost.append(getString(R.string.currencysymbol));
        expenseCost.append(invoiceAmount);
        myDbHelper.openConnection();
        List<Expense> expenseProduct = null;
        try {
            expenseProduct = myDbHelper.getExpensesforToday(invoiceId);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            myDbHelper.closeConnection();
        }

        StringBuilder itemsString = new StringBuilder();
        itemsString.append(expenseProduct.size());
        itemsString.append(" item(s)");
        return new ExpenseGroup(expenseName, makeExpenseItems(expenseProduct), itemsString.toString(), expenseCost.toString());
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.img_ocr_expense) {

            AddExpenseFragment fragmentorg = new AddExpenseFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragmentorg).commit();

        } else if(view.getId() == R.id.img_add_expense){

           // AddExpenseManualFragment fragmentorg = new AddExpenseManualFragment();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragmentorg).commit();
            InvoiceEntryFragment fragmentorg = new InvoiceEntryFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.admin_content_frame, fragmentorg).commit();
        }
    }

}
