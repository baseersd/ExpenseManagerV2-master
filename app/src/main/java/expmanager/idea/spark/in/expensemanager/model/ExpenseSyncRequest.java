package expmanager.idea.spark.in.expensemanager.model;

import java.util.List;

/**
 * Created by Baseer on 4/5/2017.
 */

public class ExpenseSyncRequest {

    private Invoice invoice;
    private List<Expense> expenseList;

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
