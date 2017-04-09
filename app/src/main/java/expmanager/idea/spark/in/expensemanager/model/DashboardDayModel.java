package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by RamanaRedddy on 4/9/17.
 */

public class DashboardDayModel {

    private int income;
    private int tangible;
    private int intangible;
    private String date;

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getTangible() {
        return tangible;
    }

    public void setTangible(int tangible) {
        this.tangible = tangible;
    }

    public int getIntangible() {
        return intangible;
    }

    public void setIntangible(int intangible) {
        this.intangible = intangible;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
