package expmanager.idea.spark.in.expensemanager.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import expmanager.idea.spark.in.expensemanager.MainActivity;
import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.adapters.MyTanExpAdapter;
import expmanager.idea.spark.in.expensemanager.common.RuntimeData;
import expmanager.idea.spark.in.expensemanager.database.DatabaseHandler;
import expmanager.idea.spark.in.expensemanager.model.AddTangibleExpenseRequest;
import expmanager.idea.spark.in.expensemanager.model.TanExpenses;
import expmanager.idea.spark.in.expensemanager.model.TangibleExpensesList;
import expmanager.idea.spark.in.expensemanager.model.UpdateTangibleExpenseRequest;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.CustomFonts;
import expmanager.idea.spark.in.expensemanager.utils.NetworkUtils;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Haresh.Veldurty on 3/9/2017.
 */

public class AdminTangibleExpenses extends Fragment implements EditTangibleListener {
    Button addtanexpense, canceltandialog, addtanexptoDb;
    EditText priceval;
    AutoCompleteTextView categoryval;
    Spinner whenval;
    DatabaseHandler db;
    ListView tanexplist;
    List<TanExpenses> list, invoice_list;
    public static MyTanExpAdapter adapt;
    private ProgressBar progressBar;
    private TextView imgArrow;
    RelativeLayout main_layout;
    int flag;
    private AlertDialog mDialog;
    ImageView setupstaff;
    private TextView txtSetupsaff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public AdminTangibleExpenses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin_tanexpense_layout,
                container, false);
        db = new DatabaseHandler(getActivity());

        setupstaff = (ImageView) rootView.findViewById(R.id.setupstaff);
        txtSetupsaff = (CustomFonts) rootView.findViewById(R.id.txt_setup_staff);

        setupstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddStaffFragment fragmentstaff = new AdminAddStaffFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentstaff).commit();
            }
        });


        Display display = getActivity().getWindowManager().getDefaultDisplay();

        int width = display.getWidth();

        final int convertWidth = (int) (width * 0.65);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),
                "fontawesome.ttf");
        imgArrow = (TextView) rootView.findViewById(R.id.img_arrow);
        imgArrow.setTypeface(typeface);

        main_layout = (RelativeLayout) rootView.findViewById(R.id.main_layout);

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


        addtanexpense = (Button) rootView.findViewById(R.id.addtanexpense);
        tanexplist = (ListView) rootView.findViewById(R.id.tanexplist);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);


        addtanexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddtagibleExpDialog1();
            }
        });


//        addtanexpense.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                openAddtagibleExpDialog();
//                return true;
//            }
//        });
        getTangibleExpenses();
        return rootView;
    }

    private void initTangibleList() {
        //list = db.getAllTanExpenses();
        if (RuntimeData.getTagibleExpenseList() == null) {
            return;
        }

        List<TanExpenses> list = RuntimeData.getTagibleExpenseList();
        if (list != null) {
            adapt = new MyTanExpAdapter(getActivity(), R.layout.list_tanexp_item, list, this);
            tanexplist.setAdapter(adapt);
        }
    }


    private void openAddtagibleExpDialog1() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addtanexpensedailog_layout, null);


        alertDialog.setView(dialogView);
        final AlertDialog dialog = alertDialog.create();


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        canceltandialog = (Button) dialogView.findViewById(R.id.canceltandialog);
        addtanexptoDb = (Button) dialogView.findViewById(R.id.addtanexptoDb);

        categoryval = (AutoCompleteTextView) dialogView.findViewById(R.id.categoryval);
        whenval = (Spinner) dialogView.findViewById(R.id.whenval);
        priceval = (EditText) dialogView.findViewById(R.id.priceval);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().setAttributes(lp);

        ArrayAdapter<CharSequence> adapterper = ArrayAdapter.createFromResource(getActivity(),
                R.array.perWhen, R.layout.simple_spinner_item);
        adapterper.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        whenval.setAdapter(adapterper);

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < RuntimeData.getCatelogList().size(); i++) {

            arrayList.add(RuntimeData.getCatelogList().get(i).getCategory().getCategoryName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, arrayList);

        categoryval.setAdapter(adapter);

        categoryval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                categoryval.setText((String) adapterView.getItemAtPosition(i));
            }
        });


        canceltandialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        addtanexptoDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!NetworkUtils.getInstance().isNetworkAvailable(getActivity())) {

                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!categoryval.getText().toString().isEmpty() && !whenval.getSelectedItem().toString().isEmpty() && !priceval.getText().toString().isEmpty()) {
                    final TanExpenses insertall = new TanExpenses(categoryval.getText().toString(), whenval.getSelectedItem().toString(), priceval.getText().toString());
                    dialog.cancel();

                    progressBar.setVisibility(View.VISIBLE);


                    AddTangibleExpenseRequest addTangibleExpenseRequest = new AddTangibleExpenseRequest(insertall.getCategory(), insertall.getWhen(), insertall.getPrice());
                    SessionManager sessionManager = new SessionManager(getActivity());
                    RetrofitApi.getApi().AddTangibleExpense(sessionManager.getAuthToken(), addTangibleExpenseRequest).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {

                                Gson gson = new Gson();

                                try {
                                    TanExpenses tanExpenses = gson.fromJson(response.body().string(), TanExpenses.class);
                                    db.addTanExpenses(insertall);
                                    List<TanExpenses> list = new ArrayList<TanExpenses>();
                                    list.add(tanExpenses);
                                    adapt = new MyTanExpAdapter(getActivity(), R.layout.list_tanexp_item, list, AdminTangibleExpenses.this);
                                    tanexplist.setAdapter(adapt);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            } else {

                                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });


                }


            }
        });


        dialog.getWindow().getAttributes().width = (int) (Utils.getDeviceMetrics(getActivity()).widthPixels * 0.55);
        dialog.show();


    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof MainActivity) {

            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeTangibleExpensesTextColor();

            setupstaff.setVisibility(View.VISIBLE);
            txtSetupsaff.setVisibility(View.VISIBLE);
            imgArrow.setVisibility(View.GONE);

        } else {

            setupstaff.setVisibility(View.GONE);
            txtSetupsaff.setVisibility(View.GONE);
            imgArrow.setVisibility(View.VISIBLE);

        }
    }

    private void EditAddtagibleExpDialog(final TanExpenses tanExpenses) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addtanexpensedailog_layout, null);

        alertDialog.setView(dialogView);
        final AlertDialog dialog = alertDialog.create();


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        canceltandialog = (Button) dialogView.findViewById(R.id.canceltandialog);
        addtanexptoDb = (Button) dialogView.findViewById(R.id.addtanexptoDb);

        categoryval = (AutoCompleteTextView) dialogView.findViewById(R.id.categoryval);
        whenval = (Spinner) dialogView.findViewById(R.id.whenval);
        priceval = (EditText) dialogView.findViewById(R.id.priceval);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().setAttributes(lp);

        ArrayAdapter<CharSequence> adapterper = ArrayAdapter.createFromResource(getActivity(),
                R.array.perWhen, R.layout.simple_spinner_item);
        adapterper.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        whenval.setAdapter(adapterper);

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < RuntimeData.getCatelogList().size(); i++) {

            arrayList.add(RuntimeData.getCatelogList().get(i).getCategory().getCategoryName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, arrayList);

        categoryval.setAdapter(adapter);

        categoryval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                categoryval.setText((String) adapterView.getItemAtPosition(i));
            }
        });


        canceltandialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().width = (int) (Utils.getDeviceMetrics(getActivity()).widthPixels * 0.55);
        dialog.show();

        categoryval.setText(tanExpenses.getCategory());

        whenval.setSelection(arrayList.indexOf(tanExpenses.getWhen()));

        priceval.setText(tanExpenses.getPrice());

        addtanexptoDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetworkUtils.getInstance().isNetworkAvailable(getActivity())) {

                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!categoryval.getText().toString().isEmpty() && !whenval.getSelectedItem().toString().isEmpty() && !priceval.getText().toString().isEmpty()) {
                    final TanExpenses insertall = new TanExpenses(categoryval.getText().toString(), whenval.getSelectedItem().toString(), priceval.getText().toString());
                    dialog.cancel();

                    progressBar.setVisibility(View.VISIBLE);


                    AddTangibleExpenseRequest addTangibleExpenseRequest = new AddTangibleExpenseRequest(insertall.getCategory(), insertall.getWhen(), insertall.getPrice());
                    UpdateTangibleExpenseRequest updateTangibleExpenseRequest = new UpdateTangibleExpenseRequest(tanExpenses.getId(), addTangibleExpenseRequest);
                    SessionManager sessionManager = new SessionManager(getActivity());
                    RetrofitApi.getApi().UpdateTangibleExpense(sessionManager.getAuthToken(), updateTangibleExpenseRequest).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {

                                Gson gson = new Gson();
                                try {


                                    TanExpenses tanExpenses = gson.fromJson(response.body().string(), TanExpenses.class);
//                                    if (RuntimeData.getTagibleExpenseList() == null) {
//                                        return;
//                                    }else {
//
//                                        List<TanExpenses> list = RuntimeData.getTagibleExpenseList();
//                                        list.add(tanExpenses);
//                                        RuntimeData.setTagibleExpenseList(list);
//                                    }

                                    adapt.add(tanExpenses);
                                    adapt.notifyDataSetChanged();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            } else {

                                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });


                }


            }
        });

    }

    private void getTangibleExpenses() {
        SessionManager sessionManager = new SessionManager(getActivity());

        mDialog = Utils.showProgressBar(getActivity(), getString(R.string.fetch_tangible_expenses));
        RetrofitApi.getApi().GetTangibleExpense(sessionManager.getAuthToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(), response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"tangibleExpensesList\" :" + response.body().string() + "}";
                        Log.i(getClass().getName(), jsonString);
                        RuntimeData.setTagibleExpenseList(gson.fromJson(jsonString, TangibleExpensesList.class));
                        initTangibleList();
                        Utils.dismissProgressBar(mDialog);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Utils.dismissProgressBar(mDialog);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                Utils.dismissProgressBar(mDialog);
            }
        });
    }

    @Override
    public void editTangibleClickListener(TanExpenses tanExpenses) {

        EditAddtagibleExpDialog(tanExpenses);

    }

}