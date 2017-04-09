package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by RamanaRedddy on 4/8/17.
 */

public class DashboardModel {


    private String month;
    private double sale;
    private double tangible;
    private double intangible;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public double getTangible() {
        return tangible;
    }

    public void setTangible(double tangible) {
        this.tangible = tangible;
    }

    public double getIntangible() {
        return intangible;
    }

    public void setIntangible(double intangible) {
        this.intangible = intangible;
    }
}
