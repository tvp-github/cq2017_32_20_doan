package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NotificationActivity extends AppCompatActivity {


    ImageButton btnHistory;
    ImageButton btnSetting;
    ImageButton btnExplore;
    ImageButton btnHome;
    LinearLayout lnl_noti;

    //enable backpress
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list);

        //Gọi khởi tạo toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_notification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notification");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnExplore =findViewById(R.id.btnExplore);
        btnHome = findViewById(R.id.btnHome);
        btnSetting = findViewById(R.id.btnSettings);
        btnHistory = findViewById(R.id.btnHistory);
        lnl_noti = findViewById(R.id.lnl_noti);
        lnl_noti.setBackgroundResource(R.drawable.bg_review);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang explore điểm dừng
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
        //Chuyển màn hình sang explore điểm dừng
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển màn hình sang history
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, HistoryActivity.class));
            }

        });

    }
}
