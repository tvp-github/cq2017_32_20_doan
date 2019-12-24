package hcmus.android.lighttour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinner;
    Button logout;
    public void init(){
        spinner = findViewById(R.id.switchLang);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        init();

        //Khởi tạo toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_list_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Switch Language
        final String[] gen = {"Vietnamese", "English", "Japanese", "Thai"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,gen);
        spinner.setAdapter(arrayAdapter);

        //Log out
        logout = findViewById(R.id.btn_logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("token").apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}
