
package hcmus.android.lighttour.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchUsers {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("users")
    @Expose
    private List<UserInfo> users = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

}