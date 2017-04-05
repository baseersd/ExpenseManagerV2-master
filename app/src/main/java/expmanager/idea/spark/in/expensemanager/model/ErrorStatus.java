package expmanager.idea.spark.in.expensemanager.model;

/**
 * Created by Baseer on 4/5/2017.
 */

public class ErrorStatus {
    public enum ErrorStatusCodes{
        STATUS_OK,
        STATUS_ERROR,
    }

    private int statusCode;
    private String message;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
