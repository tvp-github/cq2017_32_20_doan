package com.ygaps.travelapp.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.ygaps.travelapp.Response.PointStars;
import com.ygaps.travelapp.Response.StopPoint;

public class ListPointStars {
    public List<PointStars> getPointStars() {
        return pointStats;
    }
    public int size(){
        return pointStats.size();
    }
    public void setPointStarss(List<StopPoint> pointStarss) {
        this.pointStats = pointStats;
    }
    @SerializedName("pointStats")
    @Expose
    private List<PointStars> pointStats;
}
