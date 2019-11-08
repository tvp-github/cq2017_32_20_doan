package hcmus.android.lighttour.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterError {
    String location;
    String param;
    String msg;
    String value;

    public RegisterError(JSONObject jsonObject) throws JSONException {
        location = jsonObject.getString("location");
        param = jsonObject.getString("param");
        msg = jsonObject.getString("msg");
        value = jsonObject.getString("value");
    }

    public String getLocation() {
        return location;
    }

    public String getParam() {
        return param;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }
}
