package com.ygaps.travelapp.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendSpeedBody {
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("long")
    @Expose
    private double _long;
    @SerializedName("tourId")
    @Expose
    private int tourId;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("notificationType")
    @Expose
    private int notificationType;
    @SerializedName("speed")
    @Expose
    private int speed;

    public SendSpeedBody(double lat, double _long, int tourId, int userId, int notificationType, int speed) {
        this.lat = lat;
        this._long = _long;
        this.tourId = tourId;
        this.userId = userId;
        this.notificationType = notificationType;
        this.speed = speed;
    }
}
