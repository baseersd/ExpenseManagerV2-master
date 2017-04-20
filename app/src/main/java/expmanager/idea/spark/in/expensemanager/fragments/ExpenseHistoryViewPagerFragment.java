package expmanager.idea.spark.in.expensemanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.TodayExpenseAdapter;
import expmanager.idea.spark.in.expensemanager.model.Expense;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseHistoryResponse;
import expmanager.idea.spark.in.expensemanager.model.ExpenseItem;
import expmanager.idea.spark.in.expensemanager.model.ExpenseSyncRequest;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.CustomFonts;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramana.Reddy on 3/9/2017.
 */

public class ExpenseHistoryViewPagerFragment extends Fragment {

    public TodayExpenseAdapter adapter;
    private ImageView imgAddExpense;
    private TextView txtTitleWeek;
    private ExpenseHistoryResponse mExpenseHistoryWeeklyResponse;
    private RecyclerView recyclerView ;
    private LinearLayoutManager layoutManager;
    private TodayExpenseAdapter weekAdapter;

    public ExpenseHistoryViewPagerFragment(){


    }

    public static ExpenseHistoryViewPagerFragment newInstance(String startDate,String endDate) {
        ExpenseHistoryViewPagerFragment f = new ExpenseHistoryViewPagerFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("startDate", startDate);
        args.putString("endDate", endDate);
        f.setArguments(args);

        return f;
    }

    public String getStartDate() {
        return getArguments().getString("startDate", "");
    }

    public String getEndDate() {
        return getArguments().getString("endDate", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense_history_viewpager,
                container, false);

        imgAddExpense = (ImageView) rootView.findViewById(R.id.img_add_expense);
        txtTitleWeek = (CustomFonts) rootView.findViewById(R.id.title_week);


        txtTitleWeek.setText("Week "+getStartDate()+"to "+getEndDate());

         recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
         layoutManager = new LinearLayoutManager(getActivity());




        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

//        adapter = new TodayExpenseAdapter(makeExpansesList());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);


        getExpenseHistoryForDates(getStartDate(),getEndDate());


        return rootView;
    }

    public static List<ExpenseItem> makeExpanseItems() {
        ExpenseItem item1 = new ExpenseItem("Olive oil", "2 lt","$10.00");
        ExpenseItem item2 = new ExpenseItem("Sugar", "5 kg","$20.00");
        ExpenseItem item3 = new ExpenseItem("Carrots", "5 kg","$10.00");


        return Arrays.asList(item1, item2, item3);
    }

    public static List<ExpenseGroup> makeExpansesList() {
        return Arrays.asList(makeExpanseGroup(),
                makeExpanseGroup());
    }
    public static ExpenseGroup makeExpanseGroup() {
        return new ExpenseGroup("Grocery Today",makeExpanseItems(),"3 items","$40.00");
    }

    private void getExpenseHistoryForDates(final String from, final String to){
        SessionManager sessionManager = new SessionManager(getActivity());
       // isWeeklyExpense = !from.equals(to);

        RetrofitApi.getApi().GetExpenseHistory(sessionManager.getAuthToken(), from,to).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(),response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"expenseHistoryList\" :"+response.body().string()+"}";
        //                if(isWeeklyExpense){
                            mExpenseHistoryWeeklyResponse = gson.fromJson(jsonString, ExpenseHistoryResponse.class);
                            Log.d(getClass().getName(),mExpenseHistoryWeeklyResponse.toString());
                            initCurrentWeekExpenses();
//                        }else{
//                            mExpenseHistoryTodaysResponse = gson.fromJson(jsonString, ExpenseHistoryResponse.class);
//                            Log.d(getClass().getName(),mExpenseHistoryTodaysResponse.toString());
//                            initTodayExpenses();
//                            getExpenseHistoryForDates(Utils.getStartDateofCurrentWeek(),Utils.getEndDateofCurrentWeek());
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCurrentWeekExpenses(){

        if(mExpenseHistoryWeeklyResponse == null){
           // mEmptyWeekExpense.setVisibility(View.VISIBLE);
            return;
        }
        List<ExpenseGroup> weeksExpenseList = makeExpenseList(mExpenseHistoryWeeklyResponse.getExpenseHistoryList());
        weekAdapter = new TodayExpenseAdapter(weeksExpenseList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weekAdapter);
        //mEmptyWeekExpense.setVisibility(View.GONE);
    }

    private List<ExpenseGroup> makeExpenseList(List<ExpenseSyncRequest> expensesList){
        List<ExpenseGroup> expenseGroupList = new ArrayList<>();

        for(ExpenseSyncRequest expenseObj: expensesList){
            ExpenseGroup expenseGroup = makeExpenseGroup(expenseObj);
            expenseGroupList.add(expenseGroup);
        }
        return expenseGroupList;
    }
    public ExpenseGroup makeExpenseGroup(ExpenseSyncRequest expenseObj){
        List<Expense> expenseList = expenseObj.getExpenseList();
        return new ExpenseGroup(expenseObj.getInvoice().getInvDesc(),makeExpenseItems(expenseList),
                ""+expenseList.size()+"item(s)","$"+expenseObj.getInvoice().getInvAmt());
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
}
