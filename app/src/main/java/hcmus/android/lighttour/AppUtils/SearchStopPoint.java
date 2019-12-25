package hcmus.android.lighttour.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcmus.android.lighttour.Response.StopPoint;

public class SearchStopPoint {
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("stopPoints")
    @Expose
    private List<StopPoint> stopPoints = null;
    public List<StopPoint> getStopPoints() {
        return stopPoints;
    }
    public int size(){
        return stopPoints.size();
    }
    public void setStopPoints(List<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }


}
