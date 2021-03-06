package expmanager.idea.spark.in.expensemanager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import expmanager.idea.spark.in.expensemanager.model.LoginResponse;
import expmanager.idea.spark.in.expensemanager.model.SignUpRequest;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.NetworkUtils;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import expmanager.idea.spark.in.expensemanager.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RamanaRedddy on 2/22/17.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnBack,btnSignUp;
    private EditText email,password,confirmPassword;//userName
    private ProgressBar progressBar;
    private TextView mTvShowPassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        btnBack = (Button) findViewById(R.id.btn_back);
        //userName = (EditText) findViewById(R.id.user_name_sign_up);
        email = (EditText) findViewById(R.id.email_sign_up);
        password = (EditText) findViewById(R.id.password_sign_up);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        confirmPassword = (EditText)findViewById(R.id.confirm_password_sign_up);
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "fontawesome.ttf");
        mTvShowPassword = (TextView)findViewById(R.id.tv_show_password);
        mTvShowPassword.setTypeface(typeface);
        mTvShowPassword.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_back:
                  finish();

                break;

            case R.id.tv_show_password:
                isPasswordVisible = !isPasswordVisible;
                if(isPasswordVisible){
                    mTvShowPassword.setText(getString(R.string.fa_hidepwd));
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    mTvShowPassword.setText(getString(R.string.fa_showpwd));
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                password.setSelection(password.length());
                break;

            case R.id.btn_sign_up:

                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!NetworkUtils.getInstance().isNetworkAvailable(SignUpActivity.this)) {

                    Toast.makeText(SignUpActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }


                if((!email.getText().toString().isEmpty())&&(!password.getText().toString().isEmpty())){

                    progressBar.setVisibility(View.VISIBLE);

                    SignUpRequest signUpRequest = new SignUpRequest(email.getText().toString(),password.getText().toString(), Utils.getDeviceId(SignUpActivity.this));
                    RetrofitApi.getApi().SignUpExpenseManager(signUpRequest).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            progressBar.setVisibility(View.GONE);

                            if (response.isSuccessful()) {

                                Gson gson = new Gson();
                                try {
                                    LoginResponse loginResponse = gson.fromJson(response.body().string(), LoginResponse.class);
                                    SessionManager sessionManager = new SessionManager(SignUpActivity.this);
                                    sessionManager.createLoginSession(loginResponse.getToken(),loginResponse.getUser().getUsername(),
                                            loginResponse.getUser().getEmail(),loginResponse.getUser().getCompany_id(), loginResponse.getUser().is_admin());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            } else {

                                try {
                                    String errorBody = response.errorBody().string();
                                    JSONObject jsonObject = new JSONObject(errorBody);
                                    Toast.makeText(SignUpActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                                {
//                                    "status": "failed",
//                                        "message": "Validation failed: Email has already been taken, Email has already been taken, Username has already been taken"
//                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(SignUpActivity.this,"Oops something went wrong"+t.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });



                }else {
                    Toast.makeText(this,"Enter all the fields",Toast.LENGTH_SHORT).show();
                }

                break;

        }


    }
}
