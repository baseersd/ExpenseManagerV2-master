package expmanager.idea.spark.in.expensemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import expmanager.idea.spark.in.expensemanager.common.RuntimeData;
import expmanager.idea.spark.in.expensemanager.model.ProductListResponse;
import expmanager.idea.spark.in.expensemanager.network.RetrofitApi;
import expmanager.idea.spark.in.expensemanager.utils.NetworkUtils;
import expmanager.idea.spark.in.expensemanager.utils.SessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Baseer on 4/23/2017.
 */

public class CatlogService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getCategoryProductList();
    }

    private void getCategoryProductList(){
        if (!NetworkUtils.getInstance().isNetworkAvailable(CatlogService.this)) {

            Toast.makeText(CatlogService.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        SessionManager sessionManager = new SessionManager(CatlogService.this);
        RetrofitApi.getApi().GetProducts(sessionManager.getAuthToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(getClass().getName(),response.message());
                if (response.isSuccessful()) {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    try {
                        String jsonString = "{\"productList\" :"+response.body().string()+"}";
                        ProductListResponse productListResponse = gson.fromJson(jsonString, ProductListResponse.class);
                        RuntimeData.setCatelogList(productListResponse.getProductList());
                        Log.d(getClass().getName(),productListResponse.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CatlogService.this,"Oops something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
