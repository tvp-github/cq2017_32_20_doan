package hcmus.android.lighttour.AppUtils;

public class SendReviewBody {
    private int tourId;
    private int point;
    private String review;


    public SendReviewBody(int tourId, int point, String review) {
        this.tourId = tourId;
        this.review= review;
        this.point = point;
    }
}
