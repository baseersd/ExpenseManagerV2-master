package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by Baseer on 4/5/2017.
 */

public class ExpenseSyncResponse {

    private ErrorStatus status;

    public ErrorStatus getStatus() {
        return status;
    }

    public void setStatus(ErrorStatus status) {
        this.status = status;
    }
}
