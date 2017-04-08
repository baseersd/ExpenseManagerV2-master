package expmanager.idea.spark.in.expensemanager.model;



public class Invoice {
    private String invNo, date, description, bill_number, invImgPath, payment_mode, invCreatedAt;
    private int invCreateBy;
    private double amount, discount;

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

}
