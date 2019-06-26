package com.techsavanna.technology.riflocrm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.models.ContactsData;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.CustomViewHolder> {



    private Context mCtx;
    private List<ContactsData> dataList;

    public ContactsAdapter(Context mCtx, List<ContactsData> dataList) {
        this.mCtx = mCtx;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ContactsAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.contact_list, parent, false);
        return new ContactsAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.CustomViewHolder holder, int position) {
        holder.contactName1.setText(dataList.get(position).getName());
        holder.contactPhone1.setText(dataList.get(position).getMobile());
        holder.contactEmail1.setText(dataList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView contactName1, contactPhone1, contactEmail1;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName1=itemView.findViewById(R.id.contactName1);
            contactPhone1=itemView.findViewById(R.id.contactPhone1);
            contactEmail1=itemView.findViewById(R.id.contactEmail1);
        }
    }
}
