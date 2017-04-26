package expmanager.idea.spark.in.expensemanager.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import expmanager.idea.spark.in.expensemanager.common.AppConstants;
import expmanager.idea.spark.in.expensemanager.model.ApproveRejectInvoiceRequest;
import expmanager.idea.spark.in.expensemanager.model.Expense;
import expmanager.idea.spark.in.expensemanager.model.ExpenseGroup;
import expmanager.idea.spark.in.expensemanager.model.ExpenseHistoryResponse;
import expmanager.idea.spark.in.expensemanager.model.ExpenseItem;
import expmanager.idea.spark.in.expensemanager.model.ExpenseSyncRequest;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.CustomFonts;
import expmanager.idea.spark.in.expensemanager.utils.ExpenseTitleViewHolder;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramana.Reddy on 3/9/2017.
 */

public class ExpenseHistoryViewPagerFragment extends Fragment implements ExpenseTitleViewHolder.OnApprovePress {

    public TodayExpenseAdapter adapter;
    private ImageView imgAddExpense;
    private TextView txtTitleWeek;
    private ExpenseHistoryResponse mExpenseHistoryWeeklyResponse;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TodayExpenseAdapter weekAdapter;
    private static Dialog mDialog;
    private Button cancelDialog;
    private ImageView invoiceImage;

    public ExpenseHistoryViewPagerFragment() {


    }

    public static ExpenseHistoryViewPagerFragment newInstance(String startDate, String endDate) {
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

        txtTitleWeek.setText("Week " + getStartDate() + "to " + getEndDate());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
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


        getExpenseHistoryForDates(getStartDate(), getEndDate());


        return rootView;
    }

    public static List<ExpenseItem> makeExpanseItems() {
        ExpenseItem item1 = new ExpenseItem("Olive oil", "2 lt", "$10.00", true);
        ExpenseItem item2 = new ExpenseItem("Sugar", "5 kg", "$20.00", true);
        ExpenseItem item3 = new ExpenseItem("Carrots", "5 kg", "$10.00", true);


        return Arrays.asList(item1, item2, item3);
    }

    public static List<ExpenseGroup> makeExpansesList() {
        return Arrays.asList(makeExpanseGroup(),
                makeExpanseGroup());
    }

    public static ExpenseGroup makeExpanseGroup() {
        return new ExpenseGroup(null, "Grocery Today", makeExpanseItems(), "3 items", "$40.00", false);
    }

    private void getExpenseHistoryForDates(final String from, final String to) {
        SessionManager sessionManager = new SessionManager(getActivity());
        // isWeeklyExpense = !from.equals(to);

        RetrofitApi.getApi().GetExpenseHistory(sessionManager.getAuthToken(), from, to).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(), response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"expenseHistoryList\" :" + response.body().string() + "}";
                        //                if(isWeeklyExpense){
                        mExpenseHistoryWeeklyResponse = gson.fromJson(jsonString, ExpenseHistoryResponse.class);
                        Log.d(getClass().getName(), mExpenseHistoryWeeklyResponse.toString());
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
                Toast.makeText(getContext(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCurrentWeekExpenses() {

        if (mExpenseHistoryWeeklyResponse == null || mExpenseHistoryWeeklyResponse.getExpenseHistoryList().size() == 0) {
            // mEmptyWeekExpense.setVisibility(View.VISIBLE);
            return;
        }
        List<ExpenseGroup> weeksExpenseList = makeExpenseList(mExpenseHistoryWeeklyResponse.getExpenseHistoryList());
        weekAdapter = new TodayExpenseAdapter(getActivity(), weeksExpenseList, ExpenseHistoryViewPagerFragment.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weekAdapter);
        //mEmptyWeekExpense.setVisibility(View.GONE);

    }

    private List<ExpenseGroup> makeExpenseList(List<ExpenseSyncRequest> expensesList) {
        List<ExpenseGroup> expenseGroupList = new ArrayList<>();

        for (ExpenseSyncRequest expenseObj : expensesList) {
            ExpenseGroup expenseGroup = makeExpenseGroup(expenseObj);
            expenseGroupList.add(expenseGroup);
        }
        return expenseGroupList;
    }

    public ExpenseGroup makeExpenseGroup(ExpenseSyncRequest expenseObj) {
        List<Expense> expenseList = expenseObj.getExpenseList();
        return new ExpenseGroup(expenseObj, expenseObj.getInvoice().getInvDesc(), makeExpenseItems(expenseList, expenseObj.getInvoice().isExpIsApproved()),
                "" + expenseList.size() + "item(s)", "$" + expenseObj.getInvoice().getInvAmt(), expenseObj.getInvoice().isExpIsApproved());
    }

    public List<ExpenseItem> makeExpenseItems(List<Expense> expensesList, boolean isExpenseApproved) {
        List<ExpenseItem> expenseList = new ArrayList<>();
        for (Expense expense : expensesList) {
            String productCost = getString(R.string.currencysymbol) + expense.getExpAmt();
            ExpenseItem expenseItem = new ExpenseItem(expense.getExpProductName(),
                    String.valueOf(expense.getExpUnit()), productCost, isExpenseApproved);
            expenseList.add(expenseItem);
        }
        return expenseList;
    }

    @Override
    public void onApproveBtnClick(ExpenseSyncRequest syncRequest) {
        Toast.makeText(getContext(), "Approved button Clicked", Toast.LENGTH_SHORT).show();
        syncRequest.getInvoice().setExpIsApproved(true);
        updateInvoiceService(syncRequest);
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
        if (syncRequest.getInvoice().getInvImgPath() == null) {
            return;
        }

        showInvoiceImageDialog(syncRequest);
    }

    private void showInvoiceImageDialog(ExpenseSyncRequest syncRequest) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_invoice_dailog_layout, null);
        alertDialog.setView(dialogView);
        final AlertDialog dialog = alertDialog.create();


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancelDialog = (Button) dialogView.findViewById(R.id.canceltandialog);

        invoiceImage = (ImageView) dialogView.findViewById(R.id.imgView_invoice);

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

    public void updateInvoiceService(ExpenseSyncRequest expenseSyncRequest) {
        SessionManager sessionManager = new SessionManager(getContext());

        RetrofitApi.getApi().UpdateInvoice(sessionManager.getAuthToken(), expenseSyncRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Expense Updated", Toast.LENGTH_SHORT).show();
                    String todaydate = Utils.getDateTimeforFormat(AppConstants.DATE_FORMAT_YYYY_MM_DD);
                    getExpenseHistoryForDates(todaydate, todaydate);
                } else {
                    Toast.makeText(getContext(), "Expense Not Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Expense Not Updated", Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });

    }

    public void approveRejectInvoice(ApproveRejectInvoiceRequest requestObj) {
        SessionManager sessionManager = new SessionManager(getContext());

        RetrofitApi.getApi().ApproveRejectInvoice(sessionManager.getAuthToken(), requestObj).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Expense Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Expense Not Updated", Toast.LENGTH_SHORT).show();
                }
                Utils.dismissProgressBar(mDialog);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Expense Not Updated", Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });

    }
}
