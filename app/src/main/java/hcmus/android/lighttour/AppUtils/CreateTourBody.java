package hcmus.android.lighttour.AppUtils;

import retrofit2.http.Field;
import retrofit2.http.Header;

public class CreateTourBody {
    private String name;
    private long startDate;
    private long endDate;
    private float sourceLat;
    private float sourceLong;
    private float desLat;
    private float desLong;
    private boolean isPrivate;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private String avatar;

    public CreateTourBody(String name, long startDate, long endDate, float sourceLat, float sourceLong, float desLat, float desLong, boolean isPrivate, int adults, int childs, int minCost, int maxCost, String avatar) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.avatar = avatar;
    }
}
