package hcmus.android.lighttour.AppUtils;

public class RequestStoppointBody {
    Boolean hasOneCoordinate;
    OneCoord coordList;

    public RequestStoppointBody(Boolean hasOneCoordinate, OneCoord oneCoord) {
        this.hasOneCoordinate = hasOneCoordinate;
        this.coordList = oneCoord;
    }
}