package expmanager.idea.spark.in.expensemanager.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Invoice implements Serializable{

    @SerializedName("id")
    private String invNo;

    @SerializedName("date")
    private String date;

    @SerializedName("description")
    private String description;

    @SerializedName("bill_number")
    private String bill_number;

    @SerializedName("image_path")
    private String invImgPath;

    @SerializedName("payment_mode")
    private String payment_mode;

    @SerializedName("created_at")
    private String invCreatedAt;

    @SerializedName("created_by")
    private int invCreateBy;

    @SerializedName("amount")
    private double amount;

    @SerializedName("discount")
    private double  discount;

    @SerializedName("is_approved")
    private boolean expIsApproved;

    public void setInvAmt(double invAmt) {
        this.amount = invAmt;
    }

    public void setInvDesc(String invDesc) {
        this.description = invDesc;
    }

    public Invoice(){
    }
    public Invoice(String invNo, String invDate, String invDesc, String invImgPath, String invPayMode, String invBillNumber, int invCreateBy, String invCreatedAt, double invAmt, double invDisc) {
        super();
        this.invNo = invNo;
        this.date=invDate;
        this.description= invDesc;
        this.invImgPath=invImgPath;
        this.payment_mode=invPayMode;
        this.bill_number = invBillNumber;
        this.invCreateBy=invCreateBy;
        this.invCreatedAt=invCreatedAt;
        this.amount=invAmt;
        this.discount=invDisc;
    }

    public String getInvNo() {
        return invNo;
    }

    public String getInvDate() {
        return date;
    }

    public String getInvDesc() {
        return description;
    }

    public String getInvImgPath() {
        return invImgPath;
    }

    public String getInvPayMode() {
        return payment_mode;
    }

    public int getInvCreateBy() {
        return invCreateBy;
    }

    public String getInvCreatedAt() {
        return invCreatedAt;
    }

    public double getInvAmt() {
        return amount;
    }

    public double getInvDisc() {
        return discount;
    }

    public String getInvBillNumber() {
        return bill_number;
    }

    public void setInvBillNumber(String invBillNumber) {
        this.bill_number = invBillNumber;
    }


    public boolean isExpIsApproved() {
        return expIsApproved;
    }

    public void setExpIsApproved(boolean expIsApproved) {
        this.expIsApproved = expIsApproved;
    }
}
