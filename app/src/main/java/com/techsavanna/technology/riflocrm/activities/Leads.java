package com.techsavanna.technology.riflocrm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.adapters.LeadsAdapter;
import com.techsavanna.technology.riflocrm.apis.Api;
import com.techsavanna.technology.riflocrm.models.LeadsData;
import com.techsavanna.technology.riflocrm.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leads extends AppCompatActivity {

    private LeadsAdapter leadsAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addlead = new Intent(Leads.this, AddLead.class);
                startActivity(addlead);
            }
        });



        progressDialog = new ProgressDialog(Leads.this);
        progressDialog.setMessage("Fetching new Leads....");
        progressDialog.show();

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<List<LeadsData>> call = service.getLeads();
        call.enqueue(new Callback<List<LeadsData>>() {
            @Override
            public void onResponse(Call<List<LeadsData>> call, Response<List<LeadsData>> response) {
                progressDialog.dismiss();
                generateLeadList(response.body());
                //System.out.println("working data "+ response.body());
            }

            @Override
            public void onFailure(Call<List<LeadsData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Leads.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                //System.out.println("Error "+ t);




            }
        });

    }

    private void generateLeadList(List<LeadsData> leadList) {

        recyclerView = findViewById(R.id.leads_list);
        leadsAdapter = new LeadsAdapter(this,leadList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Leads.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(leadsAdapter);


    }
}
