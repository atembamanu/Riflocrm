package com.techsavanna.technology.riflocrm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.adapters.ContactsAdapter;
import com.techsavanna.technology.riflocrm.apis.Api;
import com.techsavanna.technology.riflocrm.models.ContactsData;
import com.techsavanna.technology.riflocrm.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contacts extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contacts.this, AddContact.class);
                startActivity(intent);
            }
        });


        progressDialog = new ProgressDialog(Contacts.this);
        progressDialog.setMessage("Fetching new Contacts....");
        progressDialog.show();


        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);
        Call<List<ContactsData>> call = service.getContacts();
        call.enqueue(new Callback<List<ContactsData>>() {
            @Override
            public void onResponse(Call<List<ContactsData>> call, Response<List<ContactsData>> response) {
                progressDialog.dismiss();
                generateContactsList(response.body());
                //System.out.println("working data "+ response.body());
            }

            @Override
            public void onFailure(Call<List<ContactsData>> call, Throwable t) {

            }
        });

    }

    private void generateContactsList(List<ContactsData> contactList) {


        recyclerView = findViewById(R.id.contact_list);
        contactsAdapter = new ContactsAdapter(this,contactList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Contacts.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(contactsAdapter);
    }

}
