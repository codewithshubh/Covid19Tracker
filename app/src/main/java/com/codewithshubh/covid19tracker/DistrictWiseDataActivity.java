package com.codewithshubh.covid19tracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codewithshubh.covid19tracker.Adapters.DistrictWiseAdapter;
import com.codewithshubh.covid19tracker.Adapters.StateWiseAdapter;
import com.codewithshubh.covid19tracker.MainActivity;
import com.codewithshubh.covid19tracker.Models.DistrictWiseModel;
import com.codewithshubh.covid19tracker.Models.StateWiseModel;
import com.codewithshubh.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.codewithshubh.covid19tracker.Constants.STATE_NAME;

public class DistrictWiseDataActivity extends AppCompatActivity {
    private RecyclerView rv_district_wise;
    private DistrictWiseAdapter districtWiseAdapter;
    private ArrayList<DistrictWiseModel> districtWiseModelArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText et_search;

    private String str_state_name, str_district, str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new;

    private MainActivity activity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise_data);

        GetIntent();

        //setting up the title to actionbar
        getSupportActionBar().setTitle("Region/District");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialise all views
        Init();

        //Fetch Statewise data
        FetchDistrictWiseData();

        //Setting swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchDistrictWiseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Search
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });


    }

    private void Filter(String s) {
        ArrayList<DistrictWiseModel> filteredList = new ArrayList<>();
        for (DistrictWiseModel item : districtWiseModelArrayList) {
            if (item.getDistrict().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }
        districtWiseAdapter.filterList(filteredList, s);
    }

    private void FetchDistrictWiseData() {

        //Show progress dialog
        activity.ShowDialog(this);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://api.covid19india.org/v2/state_district_wise.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int flag=0;
                            districtWiseModelArrayList.clear();
                            for (int i=1;i<response.length();i++){
                                JSONObject jsonObjectState = response.getJSONObject(i);

                                if (str_state_name.toLowerCase().equals(jsonObjectState.getString("state").toLowerCase())){
                                    JSONArray jsonArrayDistrict = jsonObjectState.getJSONArray("districtData");

                                    for (int j=0; j<jsonArrayDistrict.length(); j++){
                                        JSONObject jsonObjectDistrict = jsonArrayDistrict.getJSONObject(j);
                                        str_district = jsonObjectDistrict.getString("district");
                                        str_confirmed = jsonObjectDistrict.getString("confirmed");
                                        str_active = jsonObjectDistrict.getString("active");
                                        str_death = jsonObjectDistrict.getString("deceased");
                                        str_recovered = jsonObjectDistrict.getString("recovered");

                                        JSONObject jsonObjectDistNew = jsonObjectDistrict.getJSONObject("delta");
                                        str_confirmed_new = jsonObjectDistNew.getString("confirmed");
                                        str_recovered_new = jsonObjectDistNew.getString("recovered");
                                        str_death_new = jsonObjectDistNew.getString("deceased");

                                        //Creating an object of our statewise model class and passing the values in the constructor
                                        DistrictWiseModel districtWiseModel = new DistrictWiseModel(str_district, str_confirmed,
                                                str_active, str_recovered, str_death, str_confirmed_new, str_recovered_new,
                                                str_death_new);
                                        //adding data to our arraylist
                                        districtWiseModelArrayList.add(districtWiseModel);
                                    }
                                    flag=1;
                                }
                                if (flag==1)
                                    break;
                            }
                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    districtWiseAdapter.notifyDataSetChanged();
                                    activity.DismissDialog();
                                }
                            }, 1000);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    private void Init() {
        rv_district_wise = findViewById(R.id.activity_district_wise_recyclerview);
        swipeRefreshLayout = findViewById(R.id.activity_district_wise_swipe_refresh_layout);
        et_search = findViewById(R.id.activity_district_wise_search_editText);

        rv_district_wise.setHasFixedSize(true);
        rv_district_wise.setLayoutManager(new LinearLayoutManager(this));

        districtWiseModelArrayList = new ArrayList<>();
        districtWiseAdapter = new DistrictWiseAdapter(DistrictWiseDataActivity.this, districtWiseModelArrayList);
        rv_district_wise.setAdapter(districtWiseAdapter);
    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_state_name = intent.getStringExtra(STATE_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}