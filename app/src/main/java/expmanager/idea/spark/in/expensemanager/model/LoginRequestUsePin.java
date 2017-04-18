package expmanager.idea.spark.in.expensemanager.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramana.Reddy on 3/13/2017.
 */

public class LoginRequestUsePin {


    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("email")
    @Expose
    private String email;


    public LoginRequestUsePin(String email, String pin, String deviceId){

        this.deviceId = deviceId;
        this.pin = pin;
        this.email = email;

    }

}
