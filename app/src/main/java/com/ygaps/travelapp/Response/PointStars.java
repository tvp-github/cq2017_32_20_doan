package com.ygaps.travelapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointStars {
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
