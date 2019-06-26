package com.techsavanna.technology.riflocrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.models.LeadsData;

import java.util.List;

public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.CustomViewHolder> {




    private Context mCtx;
    private List<LeadsData> dataList;

    public LeadsAdapter(Context mCtx, List<LeadsData> dataList) {
        this.mCtx = mCtx;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LeadsAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.lead_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadsAdapter.CustomViewHolder holder, int position) {

        holder.contactName.setText(dataList.get(position).getLeadname());
        holder.contactCity.setText(dataList.get(position).getCity());
        holder.contactCountry.setText(dataList.get(position).getCountry());
        holder.contactPhone.setText(dataList.get(position).getMobile());
        holder.contactStatus.setText(dataList.get(position).getLeadstatus());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactPhone, contactCity, contactCountry, contactStatus;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.contactName);
            contactPhone = itemView.findViewById(R.id.contactPhone);
            contactCity = itemView.findViewById(R.id.contactCity);
            contactCountry = itemView.findViewById(R.id.contactCountry);
            contactStatus = itemView.findViewById(R.id.contactLeadStatus);
        }
    }
}


