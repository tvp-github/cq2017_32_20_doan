package hcmus.android.lighttour.Response;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class StopPoint implements Serializable {
    public long getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(long arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public long getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(long leaveAt) {
        this.leaveAt = leaveAt;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    @SerializedName("arrivalAt")
    @Expose
    private long arrivalAt;
    @SerializedName("leaveAt")
    @Expose
    private long leaveAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("minCost")
    @Expose
    private String minCost;
    @SerializedName("maxCost")
    @Expose
    private String maxCost;
    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;
        if(obj != null && obj instanceof StopPoint){
            StopPoint stopPoint = (StopPoint) obj;
            if(lat.equals(stopPoint.lat)&&_long.equals(stopPoint._long))
                result = true;
        }
        return result;
    }

    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("landingTimesOfUser")
    @Expose
    private String landingTimesOfUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getMinCost() {
        return minCost;
    }

    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getLandingTimesOfUser() {
        return landingTimesOfUser;
    }

    public void setLandingTimesOfUser(String landingTimesOfUser) {
        this.landingTimesOfUser = landingTimesOfUser;
    }

    public StopPoint(Integer id, String name, String address, Integer provinceId, String contact, String lat, String _long, String minCost, String maxCost, Integer serviceTypeId, Object avatar, String landingTimesOfUser) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.contact = contact;
        this.lat = lat;
        this._long = _long;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.serviceTypeId = serviceTypeId;
        this.avatar = avatar;
        this.landingTimesOfUser = landingTimesOfUser;
    }

    public StopPoint(LatLng latLng) {

        this.id = null;
        this.name = "";
        this.address = "";
        this.provinceId = 0;
        this.contact = "";
        this.lat = ""+latLng.latitude;
        this._long = ""+latLng.longitude;
        this.minCost = "";
        this.maxCost = "";
        this.serviceTypeId = 0;
        this.avatar = null;
        this.landingTimesOfUser = "";
    }
}