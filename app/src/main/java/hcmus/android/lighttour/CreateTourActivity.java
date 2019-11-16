package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import hcmus.android.lighttour.APIService.CreateToursService;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTourActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    CreateToursService CreateToursService;
    EditText edtName;
    TextView txtStartDate;
    TextView txtEndDate;
    EditText edtAdults;
    EditText edtChildren;
    EditText edtMinCost;
    EditText edtMaxCost;
    //ImageView imgAvatar;
    //CheckBox checkPrivate;
    Button btnCreateTour;
    ImageButton btnStartDate;
    ImageButton btnEndDate;
    //Ánh xạ các view và khởi tạo Service
    public void init(){
        edtName = findViewById(R.id.edit_tour_name);
        txtStartDate = findViewById(R.id.startDate);
        txtEndDate = findViewById(R.id.endDate);
        edtAdults = findViewById(R.id.edit_adults);
        edtChildren = findViewById(R.id.edit_children);
        edtMinCost = findViewById(R.id.edit_minCost);
        edtMaxCost = findViewById(R.id.edit_maxCost);

        //imgAvatar =findViewById(R.id.img_avatar);
        //checkPrivate = findViewById(R.id.check_Private);
        btnCreateTour = findViewById(R.id.btn_createTour);
        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
        CreateToursService = ApiUtils.getCreateToursService();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tour);
        init();

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_create_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create tour");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //Khi người dùng bấm nút Create Tour
        btnCreateTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide keyboard
                View myView = CreateTourActivity.this.getCurrentFocus();
                if (myView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                //Ngăn người dùng thay đổi dữ liệu
                DisableInputField();
                String name = validate(edtName.getText().toString());
                Long startDate = Long.parseLong(validate(txtStartDate.getText().toString()));
                Long endDate = Long.parseLong(validate(txtEndDate.getText().toString()));
                int adults = Integer.parseInt(validate(edtAdults.getText().toString()));
                int childs = Integer.parseInt(validate(edtChildren.getText().toString()));
                int minCost = Integer.parseInt(validate(edtMinCost.getText().toString()));
                int maxCost = Integer.parseInt(validate(edtMaxCost.getText().toString()));

                Intent intent;
                intent = new Intent(CreateTourActivity.this, SelectPlacesActivity.class);
                startActivity(intent);

               // sendCreateTour(name,startDate,endDate,adults,childs,minCost,maxCost);

            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtStartDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtEndDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }






    //Ngăn việc thay đổi dữ liệu
    private void DisableInputField() {
        edtName.setEnabled(false);
        edtAdults.setEnabled(false);
        edtChildren.setEnabled(false);
        edtMinCost.setEnabled(false);
        edtMaxCost.setEnabled(false);
        //CheckPrivate.setEnabled(false);
        btnStartDate.setEnabled(false);
        btnEndDate.setEnabled(false);
        btnCreateTour.setEnabled(false);
    }
    private void EnableInputField() {
        edtName.setEnabled(true);
        edtAdults.setEnabled(true);
        edtChildren.setEnabled(true);
        edtMinCost.setEnabled(true);
        edtMaxCost.setEnabled(true);
        //CheckPrivate.setEnabled(true);
        btnStartDate.setEnabled(true);
        btnEndDate.setEnabled(true);
        btnCreateTour.setEnabled(true);
    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }
}
