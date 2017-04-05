package expmanager.idea.spark.in.expensemanager.model;


public class Expense {
    private String expDate, expProdName, expCreatedAt;
    private int expCatId, expProdId, expInvId,expIsApproved, expIsRecursive,expCreateBy,expUnit,isSaved, expWeekIndex,expid;
    private double expAmt;

    public Expense(String expDate, int expProdId, String expProductName, int expCatId, int expInvId, int expUnit,
                   int expIsApproved, int expIsRecursive, double expAmt, int expCreateBy,
                   String expCreatedAt, int isSaved, int expWeekIndex, int expid) {
        super();
        this.expDate = expDate;
        this.expProdId = expProdId;
        this.expProdName = expProductName;
        this.expCatId= expCatId;
        this.expInvId=expInvId;
        this.expUnit = expUnit;
        this.expIsApproved=expIsApproved;
        this.expIsRecursive=expIsRecursive;
        this.expAmt=expAmt;
        this.expCreateBy=expCreateBy;
        this.expCreatedAt=expCreatedAt;
        this.isSaved=isSaved;
        this.expWeekIndex =expWeekIndex;
        this.expid = expid;
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
        return expProdName;
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

    public int getExpInvId() {
        return expInvId;
    }

    public int getExpIsApproved() {
        return expIsApproved;
    }

    public int getExpIsRecursive() {
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
        this.expProdName = expProductName;
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

    public void setExpInvId(int expInvId) {
        this.expInvId = expInvId;
    }

    public void setExpIsApproved(int expIsApproved) {
        this.expIsApproved = expIsApproved;
    }

    public void setExpIsRecursive(int expIsRecursive) {
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
