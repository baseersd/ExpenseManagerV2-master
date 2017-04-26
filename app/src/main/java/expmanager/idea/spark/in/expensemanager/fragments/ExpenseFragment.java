package expmanager.idea.spark.in.expensemanager.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.TodayExpenseAdapter;
import expmanager.idea.spark.in.expensemanager.common.AppConstants;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.ApproveRejectInvoiceRequest;
import expmanager.idea.spark.in.expensemanager.model.Expense;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseHistoryResponse;
import expmanager.idea.spark.in.expensemanager.model.ExpenseItem;
import expmanager.idea.spark.in.expensemanager.model.ExpenseSyncRequest;
import expmanager.idea.spark.in.expensemanager.model.ForecastResponse;
import expmanager.idea.spark.in.expensemanager.model.ForecastResponseObj;
import expmanager.idea.spark.in.expensemanager.model.WeekExpenseForecastModel;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.service.CatlogService;
import expmanager.idea.spark.in.expensemanager.utils.ExpenseTitleViewHolder;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramana.Reddy on 2/24/2017.
 */

public class ExpenseFragment extends Fragment implements View.OnClickListener, ExpenseTitleViewHolder.OnApprovePress {

    public TodayExpenseAdapter adapter;
    public TodayExpenseAdapter weekAdapter;
    private ImageView addexpbtn;
    private TextView imgAddocrExpense;
    private TextView imgArrow;
    private TextView imgExpenseList;
    private TextView imgReports;
    private TextView imgLogs;
    private RelativeLayout main_layout;
    private DatabaseHandler myDbHelper;
    private TextView mEmptyTodayExpense, mEmptyWeekExpense;
    private ExpenseHistoryResponse mExpenseHistoryTodaysResponse;
    private ExpenseHistoryResponse mExpenseHistoryWeeklyResponse;
    private ForecastResponseObj mForecastResponse;
    private RecyclerView mRecyclerViewToday;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerViewWeek;
    private LinearLayoutManager mLayoutManagerWeek;
    private boolean isWeeklyExpense = false;
    private int flag;
    private static Dialog mDialog;
    private  Typeface typeface;
    private Button cancelDialog;
    private ImageView invoiceImage;
    private LinearLayout llForecast;
    private TextView tvCurrent;
    private TextView tvPrev;
    private TextView tvLastPrev;
    private TextView tvNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense1,
                container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();

        final int convertWidth = (int) (width*0.65);

        myDbHelper = new DatabaseHandler(getContext());

        typeface = Typeface.createFromAsset(getContext().getAssets(),
                "fontawesome.ttf");

        imgAddocrExpense = (TextView) rootView.findViewById(R.id.img_ocr_expense);
        imgAddocrExpense.setTypeface(typeface);
        imgAddocrExpense.setOnClickListener(this);

        imgExpenseList = (TextView) rootView.findViewById(R.id.tv_expense_list);
        imgExpenseList.setTypeface(typeface);
        imgReports = (TextView) rootView.findViewById(R.id.tv_reports);
        imgReports.setTypeface(typeface);

        llForecast = (LinearLayout) rootView.findViewById(R.id.ll_forecast);

        tvCurrent = (TextView) rootView.findViewById(R.id.tvCurrentexp);
        tvPrev = (TextView) rootView.findViewById(R.id.tvPreviousweek);
        tvLastPrev = (TextView) rootView.findViewById(R.id.tvLastPrevWeekExp);
        tvNext = (TextView) rootView.findViewById(R.id.tvNextWeekExp);

        imgLogs = (TextView) rootView.findViewById(R.id.tv_logs);
        imgLogs.setTypeface(typeface);
        imgLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llForecast.getVisibility() == View.VISIBLE){
                    llForecast.setVisibility(View.INVISIBLE);
                }else{
                    llForecast.setVisibility(View.VISIBLE);
                }
            }
        });
        //drawableInitialasation(rootView);

        addexpbtn = (ImageView) rootView.findViewById(R.id.img_add_expense);
        addexpbtn.setOnClickListener(this);

        imgArrow = (TextView) rootView.findViewById(R.id.img_arrow);
        imgArrow.setTypeface(typeface);

        main_layout = (RelativeLayout) rootView.findViewById(R.id.parent_main_layout);

        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 0) {
                    imgArrow.setText(getString(R.string.fa_arrow_right));
                    main_layout.getLayoutParams().width = convertWidth;
                    main_layout.requestLayout();
                    flag = 1;

                } else {
                    imgArrow.setText(getString(R.string.fa_arrow_left));
                    main_layout.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    main_layout.requestLayout();
                    flag = 0;

                }

            }
        });

        mRecyclerViewToday = (RecyclerView) rootView.findViewById(R.id.recycler_view_todays);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mEmptyTodayExpense = (TextView) rootView.findViewById(R.id.tv_empty_list_today);
        mEmptyWeekExpense = (TextView) rootView.findViewById(R.id.tv_empty_list_week_expense);

        mRecyclerViewWeek = (RecyclerView) rootView.findViewById(R.id.recycler_view_week);
        mLayoutManagerWeek = new LinearLayoutManager(getActivity());

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        RecyclerView.ItemAnimator animator = mRecyclerViewToday.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        mDialog = Utils.showProgressBar(getActivity(),getString(R.string.fetching_expenses));
        initTodayExpenses();

        initCurrentWeekExpenses();

        getTangibleExpenses();

        //startCatlogService();
        return rootView;
    }

    private void initTodayExpenses(){
        if(mExpenseHistoryTodaysResponse == null || mExpenseHistoryTodaysResponse.getExpenseHistoryList().size() == 0){
            mEmptyTodayExpense.setVisibility(View.VISIBLE);
            mRecyclerViewToday.setVisibility(View.GONE);
            return;
        }

        List<ExpenseGroup> todaysExpenseList = makeExpenseList(mExpenseHistoryTodaysResponse.getExpenseHistoryList());
        adapter = new TodayExpenseAdapter(getContext(),todaysExpenseList,ExpenseFragment.this);
        mRecyclerViewToday.setLayoutManager(mLayoutManager);
        mRecyclerViewToday.setAdapter(adapter);
        mRecyclerViewToday.setVisibility(View.VISIBLE);
        mEmptyTodayExpense.setVisibility(View.GONE);
    }

    private void initCurrentWeekExpenses(){

        if(mExpenseHistoryWeeklyResponse == null || mExpenseHistoryWeeklyResponse.getExpenseHistoryList().size() == 0){
            mEmptyWeekExpense.setVisibility(View.VISIBLE);
            return;
        }
        List<ExpenseGroup> weeksExpenseList = makeExpenseList(mExpenseHistoryWeeklyResponse.getExpenseHistoryList());
        weekAdapter = new TodayExpenseAdapter(getContext(),weeksExpenseList,ExpenseFragment.this);
        mRecyclerViewWeek.setLayoutManager(mLayoutManagerWeek);
        mRecyclerViewWeek.setAdapter(weekAdapter);
        mEmptyWeekExpense.setVisibility(View.GONE);
    }

    private void initForecast(){
        WeekExpenseForecastModel weekExpense = mForecastResponse.getForecastResponse().getForecast();
        tvCurrent.setText(""+weekExpense.getCurrentWeekExpense());
        tvPrev.setText(""+weekExpense.getPrevWeekExpense());
        tvLastPrev.setText(""+weekExpense.getLastPrevWeekExpense());

        tvNext.setText(""+new DecimalFormat("##.##").format(
                (weekExpense.getCurrentWeekExpense() + weekExpense.getPrevWeekExpense()
                        + weekExpense.getLastPrevWeekExpense())/(3.0)));
    }
    private void getTangibleExpenses(){
        SessionManager sessionManager = new SessionManager(getActivity());
        showProgressBar(getString(R.string.fetch_tangible_expenses));
        RetrofitApi.getApi().GetBroadcast(sessionManager.getAuthToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(),response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"forecastResponse\" :"+response.body().string()+"}";
                        mForecastResponse = gson.fromJson(jsonString, ForecastResponseObj.class);
                        initForecast();
                        Log.i(getClass().getName(),response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        Utils.dismissProgressBar(mDialog);
                    }
                }
                Utils.dismissProgressBar(mDialog);
                startCatlogService();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
                startCatlogService();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String todaydate = Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_YYYY_MM_DD);
        getExpenseHistoryForDates(todaydate, todaydate);
    }

    //    private void drawableInitialasation(View rootView) {
//
//
//        TextView textView = (TextView) rootView.findViewById(R.id.doller_img);
//
//       textView.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));
//    }

    public List<ExpenseItem> makeExpenseItems(List<Expense> expensesList, boolean isExpenseApproved){
        List<ExpenseItem> expenseList = new ArrayList<>();
        for(Expense expense : expensesList){
            String productCost = getString(R.string.currencysymbol) + expense.getExpAmt();
            ExpenseItem expenseItem = new ExpenseItem(expense.getExpProductName(),
                    String.valueOf(expense.getExpUnit()),productCost, isExpenseApproved);
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
        return new ExpenseGroup(expenseObj,expenseObj.getInvoice().getInvDesc(),makeExpenseItems(expenseList,expenseObj.getInvoice().isExpIsApproved()),
                ""+expenseList.size()+"item(s)","$"+expenseObj.getInvoice().getInvAmt(),expenseObj.getInvoice().isExpIsApproved());
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

    private void getExpenseHistoryForDates(final String from, final String to){
        SessionManager sessionManager = new SessionManager(getActivity());
        isWeeklyExpense = !from.equals(to);

        showProgressBar(getString(R.string.fetching_expenses_for_today));
        RetrofitApi.getApi().GetExpenseHistory(sessionManager.getAuthToken(), from,to).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(),response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"expenseHistoryList\" :"+response.body().string()+"}";
                        if(isWeeklyExpense){
                            mExpenseHistoryWeeklyResponse = gson.fromJson(jsonString, ExpenseHistoryResponse.class);
                            Log.d(getClass().getName(),mExpenseHistoryWeeklyResponse.toString());
                            initCurrentWeekExpenses();
                        }else{
                            mExpenseHistoryTodaysResponse = gson.fromJson(jsonString, ExpenseHistoryResponse.class);
                            Log.d(getClass().getName(),mExpenseHistoryTodaysResponse.toString());
                            initTodayExpenses();
                            getExpenseHistoryForDates(Utils.getStartDateofCurrentWeek(),Utils.getEndDateofCurrentWeek());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Utils.dismissProgressBar(mDialog);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Oops something went wrong",Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });
    }

    @Override
    public void onApproveBtnClick(ExpenseSyncRequest expenseObject) {
        Toast.makeText(getContext(),"Approved button Clicked",Toast.LENGTH_SHORT).show();
        expenseObject.getInvoice().setExpIsApproved(true);
        updateInvoiceService(expenseObject);
    }

    @Override
    public void onRejectBtnClick(ExpenseSyncRequest syncRequest) {
        ApproveRejectInvoiceRequest requestObj = new ApproveRejectInvoiceRequest();
        requestObj.setApproved(false);
        requestObj.setId(syncRequest.getInvoice().getInvNo());
        approveRejectInvoice(requestObj);
    }

    @Override
    public void onViewInvoiceBtnClick(ExpenseSyncRequest syncRequest) {
        if(syncRequest.getInvoice().getInvImgPath() == null){
            return;
        }

        showInvoiceImageDialog(syncRequest);
    }

    private void showInvoiceImageDialog(ExpenseSyncRequest syncRequest){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_invoice_dailog_layout, null);
        alertDialog.setView(dialogView);
        final AlertDialog dialog = alertDialog.create();


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancelDialog = (Button) dialogView.findViewById(R.id.canceltandialog);

        invoiceImage = (ImageView)dialogView.findViewById(R.id.imgView_invoice);

        String base64Data = syncRequest.getInvoice().getInvImgPath();
        byte[] byteArrayData = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayData, 0, byteArrayData.length);
        invoiceImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500,
                500, false));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().setAttributes(lp);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().width = (int) (Utils.getDeviceMetrics(getActivity()).widthPixels * 0.55);
        dialog.show();
    }

    public void updateInvoiceService(ExpenseSyncRequest expenseSyncRequest){
        SessionManager sessionManager = new SessionManager(getContext());

        RetrofitApi.getApi().UpdateInvoice(sessionManager.getAuthToken(),expenseSyncRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(),"Expense Updated",Toast.LENGTH_SHORT).show();
                    String todaydate = Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_YYYY_MM_DD);
                    getExpenseHistoryForDates(todaydate, todaydate);
                } else {
                    Toast.makeText(getContext(),"Expense Not Updated",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Expense Not Updated",Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });

    }

    public void approveRejectInvoice(ApproveRejectInvoiceRequest requestObj){
        SessionManager sessionManager = new SessionManager(getContext());

        RetrofitApi.getApi().ApproveRejectInvoice(sessionManager.getAuthToken(),requestObj).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(),"Expense Updated",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),"Expense Not Updated",Toast.LENGTH_SHORT).show();
                }
                Utils.dismissProgressBar(mDialog);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Expense Not Updated",Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });

    }

    private void showProgressBar(String message){
        Utils.dismissProgressBar(mDialog);
        mDialog = Utils.showProgressBar(getActivity(),message);
    }

    private void startCatlogService(){
        mDialog = Utils.showProgressBar(getActivity(),getString(R.string.fetching_categories));
        Intent intent = new Intent(getActivity(), CatlogService.class);
        getActivity().startService(intent);
    }

    public static void hideProgressbar(){
        Utils.dismissProgressBar(mDialog);
    }
}
