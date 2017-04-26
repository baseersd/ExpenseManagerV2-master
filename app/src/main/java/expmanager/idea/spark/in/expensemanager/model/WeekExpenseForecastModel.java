package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Baseer on 4/27/2017.
 */

public class WeekExpenseForecastModel implements Serializable{

    @SerializedName("current_week")
    private double currentWeekExpense;

    @SerializedName("prev_week")
    private double prevWeekExpense;

    @SerializedName("last_prev")
    private double lastPrevWeekExpense;

    public double getCurrentWeekExpense() {
        return currentWeekExpense;
    }

    public void setCurrentWeekExpense(double currentWeekExpense) {
        this.currentWeekExpense = currentWeekExpense;
    }

    public double getPrevWeekExpense() {
        return prevWeekExpense;
    }

    public void setPrevWeekExpense(double prevWeekExpense) {
        this.prevWeekExpense = prevWeekExpense;
    }

    public double getLastPrevWeekExpense() {
        return lastPrevWeekExpense;
    }

    public void setLastPrevWeekExpense(double lastPrevWeekExpense) {
        this.lastPrevWeekExpense = lastPrevWeekExpense;
    }
}
