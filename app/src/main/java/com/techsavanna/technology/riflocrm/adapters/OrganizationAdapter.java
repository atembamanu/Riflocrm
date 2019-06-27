package com.techsavanna.technology.riflocrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.models.OrganizationData;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.CustomViewHolder> {

    private Context mCtx;
    private List<OrganizationData> dataList;

    public OrganizationAdapter(Context mCtx, List<OrganizationData> dataList) {
        this.mCtx = mCtx;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public OrganizationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.organization_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationAdapter.CustomViewHolder holder, int position) {
        holder.organizationName.setText(dataList.get(position).getOrganization());
        holder.organizationCity.setText(dataList.get(position).getCity());
        holder.organizationCountry.setText(dataList.get(position).getCountry());
        holder.organizationPhone1.setText(dataList.get(position).getPhone1());
        holder.organizationPhone2.setText(dataList.get(position).getPhone2());
        holder.organizationPhone2.setText(dataList.get(position).getPhone2());
        holder.organizationPhone2.setText(dataList.get(position).getPhone2());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView organizationName, organizationCity, organizationCountry, organizationPhone1, organizationPhone2;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            organizationName = itemView.findViewById(R.id.organizationName);
            organizationCity = itemView.findViewById(R.id.organizationCity);
            organizationCountry = itemView.findViewById(R.id.organizationCountry);
            organizationPhone1 = itemView.findViewById(R.id.organizationPhone1);
            organizationPhone2 = itemView.findViewById(R.id.organizationPhone2);
        }
    }
}
