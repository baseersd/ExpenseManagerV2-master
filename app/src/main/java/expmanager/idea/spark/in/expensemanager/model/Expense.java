package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

public class Expense {

    @SerializedName("id")
    private String expInvId;

    @SerializedName("invoice")
    private String expDate;

    @SerializedName("category_id")
    private int expCatId;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("product_id")
    private int expProdId;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("created_at")
    private String expCreatedAt;

    @SerializedName("is_approved")
    private boolean expIsApproved;

    @SerializedName("is_recurssive")
    private boolean expIsRecursive;

    @SerializedName("created_by")
    private int expCreateBy;

    @SerializedName("unit")
    private int expUnit;

    @SerializedName("isSaved")
    private int isSaved;

    @SerializedName("weekIndex")
    private int expWeekIndex;

    @SerializedName("expid")
    private int expid;

    @SerializedName("amount")
    private double expAmt;

    @SerializedName("company_invoice_id")
    private int expCompanyInvoiceId;

    public Expense(String expDate, int expProdId, String expProductName, int expCatId, String expCategoryName,
                   String expInvId, int expUnit,int expIsApproved, int expIsRecursive, double expAmt,
                   int expCreateBy,String expCreatedAt, int isSaved, int expWeekIndex, int expid) {
        super();
        this.expDate = expDate;
        this.expProdId = expProdId;
        this.product_name = expProductName;
        this.expCatId= expCatId;
        this.expInvId=expInvId;
        this.expUnit = expUnit;
        this.expIsApproved=Boolean.valueOf(""+expIsApproved);
        this.expIsRecursive=Boolean.valueOf(""+expIsRecursive);
        this.expAmt=expAmt;
        this.expCreateBy=expCreateBy;
        this.expCreatedAt=expCreatedAt;
        this.isSaved=isSaved;
        this.expWeekIndex =expWeekIndex;
        this.expid = expid;
        this.category_name = expCategoryName;
    }


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public int getExpid() {
        return expid;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getExpProductId() {
        return expProdId;
    }

    public String getExpProductName() {
        return product_name;
    }

    public int getExpCatId() {
        return expCatId;
    }

    public int getExpCreateBy() {
        return expCreateBy;
    }

    public int getExpUnit() {
        return expUnit;
    }

    public String getExpCreatedAt() {
        return expCreatedAt;
    }

    public double getExpAmt() {
        return expAmt;
    }

    public String getExpInvId() {
        return expInvId;
    }

    public boolean getExpIsApproved() {
        return expIsApproved;
    }

    public boolean getExpIsRecursive() {
        return expIsRecursive;
    }

    public int getIsSaved() {
        return isSaved;
    }

    public int getExpWeekIndex() {
        return expWeekIndex;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setExpProductName(String expProductName) {
        this.product_name = expProductName;
    }

    public void setExpCreatedAt(String expCreatedAt) {
        this.expCreatedAt = expCreatedAt;
    }

    public void setExpCatId(int expCatId) {
        this.expCatId = expCatId;
    }

    public void setExpProdId(int expProdId) {
        this.expProdId = expProdId;
    }

    public void setExpInvId(String expInvId) {
        this.expInvId = expInvId;
    }

    public void setExpIsApproved(boolean expIsApproved) {
        this.expIsApproved = expIsApproved;
    }

    public void setExpIsRecursive(boolean expIsRecursive) {
        this.expIsRecursive = expIsRecursive;
    }

    public void setExpCreateBy(int expCreateBy) {
        this.expCreateBy = expCreateBy;
    }

    public void setExpUnit(int expUnit) {
        this.expUnit = expUnit;
    }

    public void setIsSaved(int isSaved) {
        this.isSaved = isSaved;
    }

    public void setExpWeekIndex(int expWeekIndex) {
        this.expWeekIndex = expWeekIndex;
    }

    public void setExpid(int expid) {
        this.expid = expid;
    }

    public void setExpAmt(double expAmt) {
        this.expAmt = expAmt;
    }

}
