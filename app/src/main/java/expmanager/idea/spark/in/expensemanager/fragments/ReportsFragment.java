package expmanager.idea.spark.in.expensemanager.fragments;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.model.CreateOrganisationResponse;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.NetworkUtils;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramana.Reddy on 4/13/2017.
 */

public class ReportsFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private TextView txtDateFrom,txtDateTo;
    private ImageView imgCalendarFrom,imgCalendarTo,imgRefresh;
    private RecyclerView mRecyclerView;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner;
    private int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.reports_layout,
                container, false);

        txtDateFrom = (TextView) rootView.findViewById(R.id.txt_date);
        txtDateTo = (TextView) rootView.findViewById(R.id.txt_to_date);
        imgCalendarFrom = (ImageView) rootView.findViewById(R.id.img_calendar);
        imgCalendarTo = (ImageView) rootView.findViewById(R.id.img_to_calendar);
        imgRefresh = (ImageView) rootView.findViewById(R.id.img_refresh);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_reports);

        imgCalendarFrom.setOnClickListener(this);
        imgCalendarTo.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);

//        ArrayAdapter<CharSequence> adapterper = ArrayAdapter.createFromResource(getActivity(),
//                R.array.perWhen, R.layout.simple_spinner_item);
//        adapterper.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        whenval.setAdapter(adapterper);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

         datePickerDialog = new DatePickerDialog(
                getContext(), this, year, month, day);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.img_calendar:

                i=0;
                datePickerDialog.show();

                break;
            case R.id.img_to_calendar:

                i=1;
                datePickerDialog.show();

                break;
            case R.id.img_refresh:

                serviceCall();
                break;

        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {


        if(i==0){
            txtDateFrom.setText(day+"/"+month+"/"+year);
        }else {
            txtDateTo.setText(day+"/"+month+"/"+year);
        }
    }

    private void serviceCall(){

        if (!NetworkUtils.getInstance().isNetworkAvailable(getActivity())) {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionManager sessionManager = new SessionManager(getActivity());
        RetrofitApi.getApi().GetReports(sessionManager.getAuthToken(), txtDateFrom.getText().toString(),txtDateTo.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                if (response.isSuccessful()) {

                    Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();


//                    Gson gson = new Gson();
//                    try {
//                        CreateOrganisationResponse createOrganisationResponse = gson.fromJson(response.body().string(), CreateOrganisationResponse.class);
////                                    SessionManager sessionManager = new SessionManager(getActivity());
////                                    sessionManager.createLoginSession(loginResponse.getToken());
//
//                        TangibleExpenseFragment fragmenttangibleexp = new TangibleExpenseFragment();
//                        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmenttangibleexp).commit();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                } else {

                    Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

               // progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), "Oops something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
