package expmanager.idea.spark.in.expensemanager.model;

import java.util.List;

/**
 * Created by Baseer on 4/5/2017.
 */

public class ExpenseSyncRequest {

    private Invoice invoice;
    private List<Expense> expenses;

    public ExpenseSyncRequest(Invoice invoice, List<Expense> expenseList){
        this.invoice = invoice;
        this.expenses = expenseList;
    }
    public List<Expense> getExpenseList() {
        return expenses;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenses = expenseList;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
