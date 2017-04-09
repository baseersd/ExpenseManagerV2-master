package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by RamanaRedddy on 4/8/17.
 */

public class DashboardModel {


    private String month;
    private String income;
    private String tangible;
    private String intangible;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTangible() {
        return tangible;
    }

    public void setTangible(String tangible) {
        this.tangible = tangible;
    }

    public String getIntangible() {
        return intangible;
    }

    public void setIntangible(String intangible) {
        this.intangible = intangible;
    }
}
