package com.ygaps.travelapp.AppUtils;

import java.util.List;

import com.ygaps.travelapp.Response.StopPoint;

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
