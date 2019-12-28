package com.ygaps.travelapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOnRoad {
    private List<OnRoadNoti> notiList;

    public List<OnRoadNoti> getNotiList() {
        return notiList;
    }

    public void setNotiList(List<OnRoadNoti> notiList) {
        this.notiList = notiList;
    }

    public class OnRoadNoti {
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;
        @SerializedName("note")
        @Expose
        private String note;
        @SerializedName("notificationType")
        @Expose
        private Integer notificationType;
        @SerializedName("speed")
        @Expose
        private Integer speed;
        @SerializedName("createdByTourId")
        @Expose
        private String createdByTourId;

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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Integer getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(Integer notificationType) {
            this.notificationType = notificationType;
        }

        public Integer getSpeed() {
            return speed;
        }

        public void setSpeed(Integer speed) {
            this.speed = speed;
        }

        public String getCreatedByTourId() {
            return createdByTourId;
        }

        public void setCreatedByTourId(String createdByTourId) {
            this.createdByTourId = createdByTourId;
        }
    }
}
