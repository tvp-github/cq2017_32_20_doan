package hcmus.android.lighttour.AppUtils;

import java.util.Date;

public class EditProfileBody {
    private  String fullName;
    private  String email;
    private  String phone;
    private int gender;
    private String dob;

    public EditProfileBody(String fullName, String email, String phone, int gender, String dob) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
    }
}


