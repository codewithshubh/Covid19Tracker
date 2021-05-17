package com.codewithshubh.covid19tracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithshubh.covid19tracker.EachStateDataActivity;
import com.codewithshubh.covid19tracker.Models.StateWiseModel;
import com.codewithshubh.covid19tracker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codewithshubh.covid19tracker.Constants.STATE_ACTIVE;
import static com.codewithshubh.covid19tracker.Constants.STATE_CONFIRMED;
import static com.codewithshubh.covid19tracker.Constants.STATE_CONFIRMED_NEW;
import static com.codewithshubh.covid19tracker.Constants.STATE_DEATH;
import static com.codewithshubh.covid19tracker.Constants.STATE_DEATH_NEW;
import static com.codewithshubh.covid19tracker.Constants.STATE_LAST_UPDATE;
import static com.codewithshubh.covid19tracker.Constants.STATE_NAME;
import static com.codewithshubh.covid19tracker.Constants.STATE_RECOVERED;
import static com.codewithshubh.covid19tracker.Constants.STATE_RECOVERED_NEW;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StateWiseModel> stateWiseModelArrayList;
    private String searchText = "";
    private SpannableStringBuilder sb;
    public StateWiseAdapter(Context mContext, ArrayList<StateWiseModel> arrayList) {
        this.mContext = mContext;
        this.stateWiseModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        StateWiseModel currentItem = stateWiseModelArrayList.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format(Integer.parseInt(stateTotal)));
        //holder.tv_stateName.setText(stateName);
        if(searchText.length()>0){
            //color your text here
            sb = new SpannableStringBuilder(stateName);
            Pattern word = Pattern.compile(searchText.toLowerCase());
            Matcher match = word.matcher(stateName.toLowerCase());
            while(match.find()){
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(52, 195, 235)); //specify color here
                sb.setSpan(fcs, match.start(), match.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            holder.tv_stateName.setText(sb);

        }else{
            holder.tv_stateName.setText(stateName);
        }

         holder.lin_state_wise.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 StateWiseModel clickedItem = stateWiseModelArrayList.get(position);
                 Intent perStateIntent = new Intent(mContext, EachStateDataActivity.class);
                 perStateIntent.putExtra(STATE_NAME, clickedItem.getState());
                 perStateIntent.putExtra(STATE_CONFIRMED, clickedItem.getConfirmed());
                 perStateIntent.putExtra(STATE_CONFIRMED_NEW, clickedItem.getConfirmed_new());
                 perStateIntent.putExtra(STATE_ACTIVE, clickedItem.getActive());
                 perStateIntent.putExtra(STATE_DEATH, clickedItem.getDeath());
                 perStateIntent.putExtra(STATE_DEATH_NEW, clickedItem.getDeath_new());
                 perStateIntent.putExtra(STATE_RECOVERED, clickedItem.getRecovered());
                 perStateIntent.putExtra(STATE_RECOVERED_NEW, clickedItem.getRecovered_new());
                 perStateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getLastupdate());

                 mContext.startActivity(perStateIntent);
             }
         });
    }

    public void filterList(ArrayList<StateWiseModel> filteredList, String text) {
        stateWiseModelArrayList = filteredList;
        this.searchText = text;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stateWiseModelArrayList==null ? 0 : stateWiseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stateName, tv_stateTotalCases;
        LinearLayout lin_state_wise;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stateName = itemView.findViewById(R.id.statewise_layout_name_textview);
            tv_stateTotalCases = itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            lin_state_wise = itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }
}
