package com.ygaps.travelapp.AppUtils;

public class SendFeedbackBody {
    private int serviceId;
    private String feedback;
    private int point;

    public SendFeedbackBody(int serviceId, String feedback, int point) {
        this.serviceId = serviceId;
        this.feedback = feedback;
        this.point = point;
    }
}
