package com.techsavanna.technology.riflocrm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.apis.Api;
import com.techsavanna.technology.riflocrm.fragments.OrganizationDialog;
import com.techsavanna.technology.riflocrm.models.AddContacts;
import com.techsavanna.technology.riflocrm.network.RetrofitClientInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContact extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton  addOrganization;
    private  TextInputEditText editTextUser,editTextUser2 , editTextUserPhone , editTextUserEmail, editTextUserTitle;
    private  Spinner company;
    private AppCompatTextView btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //binding the views to java variables.

        //Buttons
        addOrganization = findViewById(R.id.addOrganization);
        btn_save = findViewById(R.id.btn_save);

        //EditTextViews
        editTextUser = findViewById(R.id.editTextUser);
        editTextUser2 = findViewById(R.id.editTextUser2);
        editTextUserPhone = findViewById(R.id.editTextUserPhone);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        editTextUserTitle = findViewById(R.id.editTextUserTitle);


        //setting onclickListener on the buttons
        addOrganization.setOnClickListener(this);
        btn_save.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.addOrganization:
                openAddOrganizationDialog();
                break;

            case R.id.btn_save:
                insertContact();
                break;
        }

    }

    private void insertContact() {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);


        AddContacts addContacts = new AddContacts();
        addContacts.setFirstname(editTextUser.getText().toString().trim());
        addContacts.setLastname(editTextUser2.getText().toString().trim());
        addContacts.setEmail(editTextUserEmail.getText().toString().trim());
        addContacts.setTitle(editTextUserTitle.getText().toString().trim());
        addContacts.setPhone(editTextUserPhone.getText().toString().trim());

        Call<AddContacts> call = service.insertContact(addContacts.getFirstname(),
                addContacts.getLastname(), addContacts.getEmail(), addContacts.getPhone(), addContacts.getTitle());

        call.enqueue(new Callback<AddContacts>() {
            @Override
            public void onResponse(Call<AddContacts> call, Response<AddContacts> response) {
                response.body();
                //Toast.makeText(AddContact.this, "response ->"+response.body().toString(), Toast.LENGTH_LONG).show();
                System.out.println("response:.."+response.toString());
                editTextUser.setText("");
                editTextUser2.setText("");
                editTextUserEmail.setText("");
                editTextUserPhone.setText("");
                editTextUserTitle.setText("");
            }

            @Override
            public void onFailure(Call<AddContacts> call, Throwable t) {
                Log.i("Hello",""+t);
                Toast.makeText(AddContact.this, "Throwable"+t, Toast.LENGTH_LONG).show();


            }
        });


    }


    private void openAddOrganizationDialog() {
        OrganizationDialog organizationDialog = new OrganizationDialog();
        organizationDialog.show(getSupportFragmentManager(),"Organization Dialog");

    }
}
