package expmanager.idea.spark.in.expensemanager.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramana.Reddy on 3/13/2017.
 */

public class CreateOrganisationRequest {


    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("company_name")
    @Expose
    private String companyName;

    @SerializedName("subscription_code")
    @Expose
    private String SubscriptionCode;

    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;

    @SerializedName("suburb")
    @Expose
    private String suburb;
    @SerializedName("city_id")
    @Expose
    private String cityId;


    @SerializedName("abn")
    @Expose
    private String abn;
    @SerializedName("acn")
    @Expose
    private String acn;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public CreateOrganisationRequest(){

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSubscriptionCode() {
        return SubscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        SubscriptionCode = subscriptionCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getAcn() {
        return acn;
    }

    public void setAcn(String acn) {
        this.acn = acn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
