package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Baseer on 4/27/2017.
 */

public class ForecastResponseObj implements Serializable{

    @SerializedName("forecastResponse")
    private ForecastResponse forecastResponse;

    public ForecastResponse getForecastResponse() {
        return forecastResponse;
    }

    public void setForecastResponse(ForecastResponse forecastResponse) {
        this.forecastResponse = forecastResponse;
    }
}
