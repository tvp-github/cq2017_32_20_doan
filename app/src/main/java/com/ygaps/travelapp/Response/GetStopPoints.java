package com.ygaps.travelapp.Response;

import java.util.List;

public class GetStopPoints {
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
