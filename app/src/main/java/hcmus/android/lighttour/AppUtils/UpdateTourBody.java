package hcmus.android.lighttour.AppUtils;

public class UpdateTourBody {
    private int id;
    private String name;
    private long startDate;
    private long endDate;
    private int adults;
    private int childs;
    private int minCost;
    private int maxCost;
    private boolean isPrivate;
    private int status;

    public UpdateTourBody(int id, String name, long startDate, long endDate, int adults, int childs, int minCost, int maxCost, boolean isPrivate, int status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.isPrivate = isPrivate;
        this.status = status;
    }
}
