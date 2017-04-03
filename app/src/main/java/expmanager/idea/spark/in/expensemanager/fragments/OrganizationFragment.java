package expmanager.idea.spark.in.expensemanager.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import expmanager.idea.spark.in.expensemanager.LoginActivity;
import expmanager.idea.spark.in.expensemanager.MainActivity;
import expmanager.idea.spark.in.expensemanager.R;
import expmanager.idea.spark.in.expensemanager.SignUpActivity;
import expmanager.idea.spark.in.expensemanager.model.CreateOrganisationRequest;
import expmanager.idea.spark.in.expensemanager.model.CreateOrganisationResponse;
import expmanager.idea.spark.in.expensemanager.model.LoginResponse;
import expmanager.idea.spark.in.expensemanager.model.SignUpRequest;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.FontManager;
import expmanager.idea.spark.in.expensemanager.utils.NetworkUtils;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Haresh.Veldurty on 2/21/2017.
 */

public class OrganizationFragment extends Fragment {

    private ImageView setupexp,imgCompanyLogo;
    private EditText etOrganizationName,etAddressLine1,etAddressLine2,etActivationCode,etSuburb,etCityId,etAbn,etAcn;
    private ProgressBar progressBar;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String base64Image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public OrganizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.organization_layout,
                container, false);
        setupexp = (ImageView) rootView.findViewById(R.id.setupexp);


        etOrganizationName = (EditText) rootView.findViewById(R.id.et_organization);
        etActivationCode = (EditText) rootView.findViewById(R.id.et_activation_code);
        etAddressLine1 = (EditText) rootView.findViewById(R.id.et_address_line1);
        etAddressLine2 = (EditText) rootView.findViewById(R.id.et_address_line2);
        etSuburb = (EditText) rootView.findViewById(R.id.et_suburb);
        etCityId = (EditText) rootView.findViewById(R.id.et_city_id);
        etAbn = (EditText) rootView.findViewById(R.id.et_abn);
        etAcn = (EditText) rootView.findViewById(R.id.et_acn);

        imgCompanyLogo = (ImageView) rootView.findViewById(R.id.img_company_logo);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        imgCompanyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

//        TextView  textView = (TextView) rootView.findViewById(R.id.textView5);
//
//        textView.setTypeface(FontManager.getTypeface(getActivity(),FontManager.FONTAWESOME));



        setupexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!NetworkUtils.getInstance().isNetworkAvailable(getActivity())) {

                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((!etOrganizationName.getText().toString().isEmpty())&&(!etActivationCode.getText().toString().isEmpty())) {

                    progressBar.setVisibility(View.VISIBLE);

                    CreateOrganisationRequest createOrganisationRequest = new CreateOrganisationRequest();
                    createOrganisationRequest.setCompanyName(etOrganizationName.getText().toString());
                    createOrganisationRequest.setDeviceId(Utils.getDeviceId(getActivity()));
                    createOrganisationRequest.setSubscriptionCode(etActivationCode.getText().toString());
                    createOrganisationRequest.setAddressLine1(etAddressLine1.getText().toString());
                    createOrganisationRequest.setAddressLine2(etAddressLine2.getText().toString());
                    createOrganisationRequest.setAbn(etAbn.getText().toString());
                    createOrganisationRequest.setAcn(etAcn.getText().toString());
                    createOrganisationRequest.setSuburb(etSuburb.getText().toString());
                    createOrganisationRequest.setCityId(etCityId.getText().toString());
                    createOrganisationRequest.setImageUrl("");

                    SessionManager sessionManager = new SessionManager(getActivity());
                    RetrofitApi.getApi().CreateOrganisation(sessionManager.getAuthToken(), createOrganisationRequest).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {


                                Gson gson = new Gson();
                                try {
                                    CreateOrganisationResponse createOrganisationResponse = gson.fromJson(response.body().string(), CreateOrganisationResponse.class);
//                                    SessionManager sessionManager = new SessionManager(getActivity());
//                                    sessionManager.createLoginSession(loginResponse.getToken());

                                    TangibleExpenseFragment fragmenttangibleexp = new TangibleExpenseFragment();
                                    getFragmentManager().beginTransaction().replace(R.id.content_frame, fragmenttangibleexp).commit();

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


                } else {
                    Toast.makeText(getActivity(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte [] ba = bao.toByteArray();
            base64Image = Base64.encodeToString(ba,Base64.DEFAULT);

        }
    }
}
