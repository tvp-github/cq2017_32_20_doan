package hcmus.android.lighttour.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInviteBody {
    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("isAccepted")
    @Expose
    private Boolean isAccepted;

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public ResponseInviteBody(String tourId, Boolean isAccepted) {
        this.tourId = tourId;
        this.isAccepted = isAccepted;
    }
}
