package com.ygaps.travelapp.AppUtils;

public class TokenBody {
    private String fcmToken;
    private String deviceId;
    private Integer platform;
    private String appVersion;

    public TokenBody(String fcmToken, String deviceId, Integer platform, String appVersion) {
        this.fcmToken = fcmToken;
        this.deviceId = deviceId;
        this.platform = platform;
        this.appVersion = appVersion;
    }
}
