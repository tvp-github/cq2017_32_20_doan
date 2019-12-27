package hcmus.android.lighttour.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditInfo {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
