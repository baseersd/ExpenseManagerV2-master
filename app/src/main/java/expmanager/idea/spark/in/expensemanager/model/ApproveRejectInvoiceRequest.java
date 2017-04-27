package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Baseer on 4/26/2017.
 */

public class ApproveRejectInvoiceRequest implements Serializable{

    @SerializedName("id")
    private String id;

    @SerializedName("is_approved")
    private boolean isApproved;

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
