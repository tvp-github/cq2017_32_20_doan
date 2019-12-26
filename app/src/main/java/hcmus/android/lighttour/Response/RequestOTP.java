package hcmus.android.lighttour.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestOTP implements Serializable {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expiredOn")
    @Expose
    private Long expiredOn;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExperedOn() {
        return expiredOn;
    }

    public void setExperedOn(Long experedOn) {
        this.expiredOn = experedOn;
    }
}
