package com.codewithshubh.covid19tracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithshubh.covid19tracker.EachDistrictDataActivity;
import com.codewithshubh.covid19tracker.EachStateDataActivity;
import com.codewithshubh.covid19tracker.Models.DistrictWiseModel;
import com.codewithshubh.covid19tracker.Models.StateWiseModel;
import com.codewithshubh.covid19tracker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codewithshubh.covid19tracker.Constants.DISTRICT_ACTIVE;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_CONFIRMED;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_CONFIRMED_NEW;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_DEATH;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_DEATH_NEW;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_NAME;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_RECOVERED;
import static com.codewithshubh.covid19tracker.Constants.DISTRICT_RECOVERED_NEW;
import static com.codewithshubh.covid19tracker.Constants.STATE_ACTIVE;
import static com.codewithshubh.covid19tracker.Constants.STATE_CONFIRMED;
import static com.codewithshubh.covid19tracker.Constants.STATE_CONFIRMED_NEW;
import static com.codewithshubh.covid19tracker.Constants.STATE_DEATH;
import static com.codewithshubh.covid19tracker.Constants.STATE_DEATH_NEW;
import static com.codewithshubh.covid19tracker.Constants.STATE_LAST_UPDATE;
import static com.codewithshubh.covid19tracker.Constants.STATE_NAME;
import static com.codewithshubh.covid19tracker.Constants.STATE_RECOVERED;
import static com.codewithshubh.covid19tracker.Constants.STATE_RECOVERED_NEW;

public class DistrictWiseAdapter extends RecyclerView.Adapter<DistrictWiseAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<DistrictWiseModel> districtWiseModelArrayList;

    private String searchText="";
    private SpannableStringBuilder sb;

    public DistrictWiseAdapter(Context mContext, ArrayList<DistrictWiseModel> districtWiseModelArrayList) {
        this.mContext = mContext;
        this.districtWiseModelArrayList = districtWiseModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        DistrictWiseModel currentItem = districtWiseModelArrayList.get(position);
        String districtName = currentItem.getDistrict();
        String districtTotal = currentItem.getConfirmed();
        holder.tv_districtTotalCases.setText(NumberFormat.getInstance().format(Integer.parseInt(districtTotal)));
        //holder.tv_districtName.setText(districtName);
        if(searchText.length()>0){
            //color your text here
            int index = districtName.indexOf(searchText);
            sb = new SpannableStringBuilder(districtName);
            Pattern word = Pattern.compile(searchText.toLowerCase());
            Matcher match = word.matcher(districtName.toLowerCase());
            while(match.find()){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(52, 195, 235)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                //index = stateName.indexOf(searchText,index+1);

            }
            holder.tv_districtName.setText(sb);

        }else{
            holder.tv_districtName.setText(districtName);
        }

        holder.lin_district_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistrictWiseModel clickedItem = districtWiseModelArrayList.get(position);
                Intent perDistrictIntent = new Intent(mContext, EachDistrictDataActivity.class);
                perDistrictIntent.putExtra(DISTRICT_NAME, clickedItem.getDistrict());
                perDistrictIntent.putExtra(DISTRICT_CONFIRMED, clickedItem.getConfirmed());
                perDistrictIntent.putExtra(DISTRICT_CONFIRMED_NEW, clickedItem.getNewConfirmed());
                perDistrictIntent.putExtra(DISTRICT_ACTIVE, clickedItem.getActive());
                perDistrictIntent.putExtra(DISTRICT_DEATH, clickedItem.getDeceased());
                perDistrictIntent.putExtra(DISTRICT_DEATH_NEW, clickedItem.getNewDeceased());
                perDistrictIntent.putExtra(DISTRICT_RECOVERED, clickedItem.getRecovered());
                perDistrictIntent.putExtra(DISTRICT_RECOVERED_NEW, clickedItem.getNewRecovered());
                mContext.startActivity(perDistrictIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return districtWiseModelArrayList==null ? 0 : districtWiseModelArrayList.size();
    }

    public void filterList(ArrayList<DistrictWiseModel> filteredList, String search) {
        districtWiseModelArrayList = filteredList;
        this.searchText = search;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_districtName, tv_districtTotalCases;
        LinearLayout lin_district_wise;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_districtName = itemView.findViewById(R.id.statewise_layout_name_textview);
            tv_districtTotalCases = itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            lin_district_wise = itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }

}