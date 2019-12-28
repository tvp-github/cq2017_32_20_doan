package com.ygaps.travelapp.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.ygaps.travelapp.Response.Feedback;

public class ListFeedback {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("feedbackList")
    @Expose
    private List<Feedback> feedbacks = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

}