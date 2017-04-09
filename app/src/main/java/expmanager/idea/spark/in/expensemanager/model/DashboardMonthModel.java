package expmanager.idea.spark.in.expensemanager.model;

import java.util.List;

/**
 * Created by RamanaRedddy on 4/9/17.
 */

public class DashboardMonthModel {

    private String month;
    private double tangible;
    private double intangible;
    private double sale;
    private List<DashboardDayModel> dashboardDayModels = null;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public List<DashboardDayModel> getDashboardDayModels() {
        return dashboardDayModels;
    }

    public void setDashboardDayModels(List<DashboardDayModel> dashboardDayModels) {
        this.dashboardDayModels = dashboardDayModels;
    }
}
