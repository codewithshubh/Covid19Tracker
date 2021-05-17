package com.codewithshubh.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;

import static com.codewithshubh.covid19tracker.Constants.DISTRICT_ACTIVE;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_CONFIRMED;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_CONFIRMED_NEW;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_DEATH;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_DEATH_NEW;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_NAME;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_RECOVERED;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_RECOVERED_NEW;

public class EachDistrictDataActivity extends AppCompatActivity {

    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new,
            tv_recovered, tv_recovered_new, tv_death, tv_death_new;

    private String str_districtName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new;

    private PieChart pieChart;

    private MainActivity activity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_district_data);

        //Fetching data which is passed from previous activity into this activity
        GetIntent();

        //Setting up the title of actionbar as State name
        getSupportActionBar().setTitle(str_districtName);

        //back menu icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise all textviews
        Init();

        //Load data
        LoadDistrictData();
    }

    private void LoadDistrictData() {
        //Show dialog
        activity.ShowDialog(this);

        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+ NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+ NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

                //setting piechart
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), Color.parseColor("#007afe")));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_recovered), Color.parseColor("#08a045")));
                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(str_death), Color.parseColor("#F6404F")));

                pieChart.startAnimation();

                activity.DismissDialog();
            }
        },1000);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_districtName = intent.getStringExtra(DISTRICT_NAME);
        str_confirmed = intent.getStringExtra(DISTRICT_CONFIRMED);
        str_confirmed_new = intent.getStringExtra(DISTRICT_CONFIRMED_NEW);
        str_active = intent.getStringExtra(DISTRICT_ACTIVE);
        str_death = intent.getStringExtra(DISTRICT_DEATH);
        str_death_new = intent.getStringExtra(DISTRICT_DEATH_NEW);
        str_recovered = intent.getStringExtra(DISTRICT_RECOVERED);
        str_recovered_new = intent.getStringExtra(DISTRICT_RECOVERED_NEW);
    }

    private void Init() {
        tv_confirmed = findViewById(R.id.activity_each_district_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_each_district_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_each_district_active_textView);
        tv_active_new = findViewById(R.id.activity_each_district_active_new_textView);
        tv_recovered = findViewById(R.id.activity_each_district_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_each_district_recovered_new_textView);
        tv_death = findViewById(R.id.activity_each_district_death_textView);
        tv_death_new = findViewById(R.id.activity_each_district_death_new_textView);
        pieChart = findViewById(R.id.activity_each_district_piechart);
    }
}