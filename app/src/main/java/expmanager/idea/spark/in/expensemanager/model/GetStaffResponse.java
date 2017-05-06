package expmanager.idea.spark.in.expensemanager.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Baseer on 5/6/2017.
 */

public class GetStaffResponse implements Serializable{

    @SerializedName("stafflist")
    private List<AddStaffRequest> mStaffList;

    public List<AddStaffRequest> getStaffList() {
        return mStaffList;
    }

    public void setStaffList(List<AddStaffRequest> mStaffList) {
        this.mStaffList = mStaffList;
    }
}
