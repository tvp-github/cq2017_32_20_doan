package hcmus.android.lighttour.AppUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcmus.android.lighttour.Response.StopPoint;

public class AddStopPointsBody {
    String tourId;
    List<StopPoint> stopPoints;
    List<Integer> deleteIds;

    public AddStopPointsBody(String tourId, List<StopPoint> stopPoints, List<Integer> deleteIds) {
        this.tourId = tourId;
        this.stopPoints = stopPoints;
        this.deleteIds = deleteIds;
    }
}
