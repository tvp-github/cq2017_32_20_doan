package hcmus.android.lighttour.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcmus.android.lighttour.Response.PointStars;
import hcmus.android.lighttour.Response.StopPoint;

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
