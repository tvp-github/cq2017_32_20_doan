package com.ygaps.travelapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTours {

    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("minCost")
    @Expose
    private Integer minCost;
    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;
    @SerializedName("startDate")
    @Expose
    private Integer startDate;
    @SerializedName("endDate")
    @Expose
    private Integer endDate;
    @SerializedName("adults")
    @Expose
    private Integer adults;
    @SerializedName("childs")
    @Expose
    private Integer childs;
    @SerializedName("sourceLat")
    @Expose
    private Integer sourceLat;
    @SerializedName("sourceLong")
    @Expose
    private Integer sourceLong;
    @SerializedName("desLat")
    @Expose
    private Integer desLat;
    @SerializedName("desLong")
    @Expose
    private Integer desLong;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinCost() {
        return minCost;
    }

    public void setMinCost(Integer minCost) {
        this.minCost = minCost;
    }

    public Integer getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Integer maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }

    public Integer getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(Integer sourceLat) {
        this.sourceLat = sourceLat;
    }

    public Integer getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(Integer sourceLong) {
        this.sourceLong = sourceLong;
    }

    public Integer getDesLat() {
        return desLat;
    }

    public void setDesLat(Integer desLat) {
        this.desLat = desLat;
    }

    public Integer getDesLong() {
        return desLong;
    }

    public void setDesLong(Integer desLong) {
        this.desLong = desLong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "CreateTours{" +
                "hostId='" + hostId + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", minCost=" + minCost +
                ", maxCost=" + maxCost +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", adults=" + adults +
                ", childs=" + childs +
                ", sourceLat=" + sourceLat +
                ", sourceLong=" + sourceLong +
                ", desLat=" + desLat +
                ", desLong=" + desLong +
                ", id=" + id +
                ", isPrivate=" + isPrivate +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
