package expmanager.idea.spark.in.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import expmanager.idea.spark.in.expensemanager.model.LoginRequest;
import expmanager.idea.spark.in.expensemanager.model.LoginRequestUsePin;
import expmanager.idea.spark.in.expensemanager.model.LoginResponse;
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
 * Created by Ramana.Reddy on 2/22/2017.
 */

public class UsePinActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView tvPin;
    private Button signUp;
    private Button usePassword;
    private Button forgotPin;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_pin_activity);

        signUp = (Button) findViewById(R.id.sign_up);
        tvPin = (CustomFonts) findViewById(R.id.tv_enter_pin);
        usePassword = (Button) findViewById(R.id.use_password);
        forgotPin = (Button) findViewById(R.id.forgot_pin);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        KeyboardInitUI();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UsePinActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        usePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UsePinActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        forgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UsePinActivity.this, ForgotPinActivity.class);
                startActivity(i);
                finish();

            }
        });

        tvPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//               int size = charSequence.length();
//                if(size!=4){
//                    return;
//                }
//                if(charSequence.toString().equalsIgnoreCase("1234")){
//
//                    Intent intent = new Intent(UsePinActivity.this, AdminActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else if (charSequence.toString().equalsIgnoreCase("5678")){
//
//                    Intent intent = new Intent(UsePinActivity.this, StaffActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    private void KeyboardInitUI() {

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btn1 :
                tvPin.setText(tvPin.getText()+"1");

            break;
            case R.id.btn2 :
                tvPin.setText(tvPin.getText()+"2");

                break;
            case R.id.btn3 :
                tvPin.setText(tvPin.getText()+"3");

                break;
            case R.id.btn4 :
                tvPin.setText(tvPin.getText()+"4");

                break;
            case R.id.btn5 :
                tvPin.setText(tvPin.getText()+"5");

                break;

            case R.id.btn6 :
                tvPin.setText(tvPin.getText()+"6");

                break;
            case R.id.btn7 :
                tvPin.setText(tvPin.getText()+"7");

                break;

            case R.id.btn8 :
                tvPin.setText(tvPin.getText()+"8");

                break;
            case R.id.btn9 :
                tvPin.setText(tvPin.getText()+"9");

                break;

            case R.id.btn0 :
                tvPin.setText(tvPin.getText()+"0");

                break;

            case R.id.btn_ok :
//                if(tvPin.getText().toString().equalsIgnoreCase("1234")){
//
//                    Intent intent = new Intent(UsePinActivity.this, AdminActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else if (tvPin.getText().toString().equalsIgnoreCase("5678")){
//
//                    Intent intent = new Intent(UsePinActivity.this, StaffActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

                serviceCallForLogin();

                break;

            case R.id.btn_clear :
                int size = tvPin.getText().toString().length();
                if(size>0) {
                    tvPin.setText(tvPin.getText().toString().substring(0, size - 1));
                }
                break;

            default :

        }
    }

    private void serviceCallForLogin() {

        if (!NetworkUtils.getInstance().isNetworkAvailable(UsePinActivity.this)) {

            Toast.makeText(UsePinActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        final SessionManager sessionManager = new SessionManager(UsePinActivity.this);
        String email = sessionManager.getEmailId();

        if (email.isEmpty()) {

            Toast.makeText(UsePinActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((!email.isEmpty()) && (!tvPin.getText().toString().isEmpty())) {
            Utils.hideKeyboard(UsePinActivity.this);
            progressBar.setVisibility(View.VISIBLE);
            LoginRequestUsePin loginRequest = new LoginRequestUsePin(email, tvPin.getText().toString(), Utils.getDeviceId(UsePinActivity.this));
            RetrofitApi.getApi().loginExpenseManagerUsingPin(loginRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        Gson gson = new Gson();
                        try {
                            LoginResponse loginResponse = gson.fromJson(response.body().string(), LoginResponse.class);

                            sessionManager.createLoginSession(loginResponse.getToken(),loginResponse.getUser().getUsername(),
                                    loginResponse.getUser().getEmail(),loginResponse.getUser().getCompany_id());

                            if((loginResponse.getUser().getCompany_id()!=null)&&(!loginResponse.getUser().getCompany_id().isEmpty())) {

                                Intent intent = new Intent(UsePinActivity.this, AdminActivity.class);
                                startActivity(intent);

                            }else {

                                Intent i = new Intent(UsePinActivity.this, MainActivity.class);
                                startActivity(i);

                            }

                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                        try {
                            String errorBody = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBody);
                            Toast.makeText(UsePinActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(UsePinActivity.this, "Oops something went wrong", Toast.LENGTH_SHORT).show();

                }
            });

        } else {

            Toast.makeText(UsePinActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
        }



    }
}
