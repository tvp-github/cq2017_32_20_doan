package hcmus.android.lighttour.AppUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Review;

public class ListReview {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("reviewList")
    @Expose
    private List<Review> reviews = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}