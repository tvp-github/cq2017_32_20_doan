package com.ygaps.travelapp.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteBody {
    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("invitedUserId")
    @Expose
    private String invitedUserId;
    @SerializedName("isInvited")
    @Expose
    private boolean isInvited;

    public InviteBody(String tourId, String invitedUserId, boolean isInvited) {
        this.tourId = tourId;
        this.invitedUserId = invitedUserId;
        this.isInvited = isInvited;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(String invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }
}
