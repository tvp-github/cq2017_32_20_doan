package hcmus.android.lighttour.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNotification {
    @SerializedName("notiList")
    @Expose
    private List<Notification> notiList = null;

    public List<Notification> getNotis() {
        return notiList;
    }

    public void setNotis(List<Notification> notis) {
        this.notiList = notis;
    }
}
