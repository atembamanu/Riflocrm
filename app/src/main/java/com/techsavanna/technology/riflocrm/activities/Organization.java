package com.techsavanna.technology.riflocrm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.adapters.OrganizationAdapter;
import com.techsavanna.technology.riflocrm.apis.Api;
import com.techsavanna.technology.riflocrm.models.OrganizationData;
import com.techsavanna.technology.riflocrm.network.RetrofitClient;
import com.techsavanna.technology.riflocrm.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Organization extends AppCompatActivity {

    private OrganizationAdapter organizationAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Organization.this, AddOrganization.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(Organization.this);
        progressDialog.setMessage("Refreshing Organizations...");
        progressDialog.show();

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<List<OrganizationData>> call = service.getOrganizations();

        call.enqueue(new Callback<List<OrganizationData>>() {
            @Override
            public void onResponse(Call<List<OrganizationData>> call, Response<List<OrganizationData>> response) {
                progressDialog.dismiss();
                getOrganizations(response.body());
            }

            @Override
            public void onFailure(Call<List<OrganizationData>> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(Organization.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getOrganizations(List<OrganizationData> organizationList) {

        recyclerView = findViewById(R.id.organization_list);
        organizationAdapter = new OrganizationAdapter(this, organizationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Organization.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(organizationAdapter);

    }

}
