package hcmus.android.lighttour.AppUtils;

import java.util.List;

import hcmus.android.lighttour.Response.StopPoint;

public class MyResponse {
    public List<StopPoint> getStopPoints() {
        return stopPoints;
    }
    public int size(){
        return stopPoints.size();
    }
    public void setStopPoints(List<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }

    private List<StopPoint> stopPoints;
}
