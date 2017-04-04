package expmanager.idea.spark.in.expensemanager.model;



public class Invoice {
    private String invNo, invDate, invDesc,  invImgPath, invPayMode, invCreatedAt;
    private int invBillNumber,invCreateBy;

    public void setInvAmt(double invAmt) {
        this.invAmt = invAmt;
    }

    public void setInvDesc(String invDesc) {
        this.invDesc = invDesc;
    }

    private double invAmt, invDisc;

    public Invoice(){
    }
    public Invoice(String invNo, String invDate, String invDesc, String invImgPath, String invPayMode, int invBillNumber, int invCreateBy, String invCreatedAt, double invAmt, double invDisc) {
        super();
        this.invNo = invNo;
        this.invDate=invDate;
        this.invDesc= invDesc;
        this.invImgPath=invImgPath;
        this.invPayMode=invPayMode;
        this.invBillNumber = invBillNumber;
        this.invCreateBy=invCreateBy;
        this.invCreatedAt=invCreatedAt;
        this.invAmt=invAmt;
        this.invDisc=invDisc;
    }

    public String getInvNo() {
        return invNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public String getInvDesc() {
        return invDesc;
    }

    public String getInvImgPath() {
        return invImgPath;
    }

    public String getInvPayMode() {
        return invPayMode;
    }

    public int getInvCreateBy() {
        return invCreateBy;
    }

    public String getInvCreatedAt() {
        return invCreatedAt;
    }

    public double getInvAmt() {
        return invAmt;
    }

    public double getInvDisc() {
        return invDisc;
    }

    public int getInvBillNumber() {
        return invBillNumber;
    }

    public void setInvBillNumber(int invBillNumber) {
        this.invBillNumber = invBillNumber;
    }

}
