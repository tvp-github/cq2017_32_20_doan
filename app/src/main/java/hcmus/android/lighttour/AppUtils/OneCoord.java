package hcmus.android.lighttour.AppUtils;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OneCoord {

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("long")
    @Expose
    private double _long;

    public OneCoord(double lat, double _long) {
        this.lat = lat;
        this._long = _long;
    }

    @Override
    public String toString() {
        return "{" +
                "\"lat\"=" + lat +
                ", \"long\"=" + _long +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public OneCoord setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLong() {
        return _long;
    }

    public OneCoord setLong(double _long) {
        this._long = _long;
        return this;
    }
}