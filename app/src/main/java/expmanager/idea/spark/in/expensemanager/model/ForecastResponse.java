package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Baseer on 4/27/2017.
 */

public class ForecastResponse implements Serializable{

    @SerializedName("week_expense")
    private WeekExpenseForecastModel forecast;

    @SerializedName("intangible")
    private TanExpenses[] inTangibleExp;

    public WeekExpenseForecastModel getForecast() {
        return forecast;
    }

    public void setForecast(WeekExpenseForecastModel forecast) {
        this.forecast = forecast;
    }

    public TanExpenses[] getInTangibleExp() {
        return inTangibleExp;
    }

    public void setInTangibleExp(TanExpenses[] inTangibleExp) {
        this.inTangibleExp = inTangibleExp;
    }
}
