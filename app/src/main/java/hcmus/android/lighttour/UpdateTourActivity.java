package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import hcmus.android.lighttour.Response.Tour;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateTourActivity extends AppCompatActivity {
    EditText edtTourName;
    EditText edtAdults;
    EditText edtChildren;
    EditText edtMinCost;
    EditText edtMaxCost;
    CheckBox ckbPrivate;
    TextView txtStartDate;
    TextView txtEndDate;
    Button btnSubmit;
    ImageButton btnStartDate;
    ImageButton btnEndDate;
    private void init(){
        btnSubmit = findViewById(R.id.btn_createTour);
        btnSubmit.setText("Update");
        txtStartDate = findViewById(R.id.startDate);
        txtEndDate = findViewById(R.id.endDate);
        edtTourName = findViewById(R.id.edit_tour_name);
        edtAdults = findViewById(R.id.edit_adults);
        edtChildren = findViewById(R.id.edit_children);
        edtMinCost = findViewById(R.id.edit_minCost);
        edtMaxCost = findViewById(R.id.edit_maxCost);
        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tour);
        init();
        //Toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_create_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Tour");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendarStart = (Calendar) txtStartDate.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendarStart.set(Calendar.YEAR, i);
                        calendarStart.set(Calendar.MONTH, i1);
                        calendarStart.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtStartDate.setText(simpleDateFormat.format(calendarStart.getTime()));
                        txtStartDate.setTag(calendarStart);
                    }
                }, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendarEnd = (Calendar) txtEndDate.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendarEnd.set(Calendar.YEAR, i);
                        calendarEnd.set(Calendar.MONTH, i1);
                        calendarEnd.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtEndDate.setText(simpleDateFormat.format(calendarEnd.getTime()));
                        txtEndDate.setTag(calendarEnd);
                    }
                }, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        String strTour = getIntent().getExtras().getString("tour");
        Tour tour = new Gson().fromJson(strTour,Tour.class);
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        calendarStart.setTimeInMillis(Long.parseLong(tour.getStartDate()));
        txtStartDate.setText(simpleDateFormat.format(calendarStart.getTime()));
        txtStartDate.setTag(calendarStart);

        calendarEnd.setTimeInMillis(Long.parseLong(tour.getEndDate()));
        txtEndDate.setText(simpleDateFormat.format(calendarEnd.getTime()));
        txtEndDate.setTag(calendarEnd);

        edtTourName.setText(tour.getName());
        edtAdults.setText(tour.getAdults().toString());
        edtChildren.setText(tour.getChilds().toString());
        edtMinCost.setText(tour.getMinCost());
        edtMaxCost.setText(tour.getMaxCost());
        Log.d("FFF", "onCreate: "+tour.getIsPrivate());
//        ckbPrivate.setChecked(tour.getIsPrivate());
    }
}
