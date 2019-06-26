package com.techsavanna.technology.riflocrm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.fragments.OrganizationDialog;

public class AddContact extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton  addOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addOrganization = findViewById(R.id.addOrganization);
        addOrganization.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.addOrganization:
                openAddOrganizationDialog();
                break;
        }

    }

    private void openAddOrganizationDialog() {
        OrganizationDialog organizationDialog = new OrganizationDialog();
        organizationDialog.show(getSupportFragmentManager(),"Organization Dialog");

    }
}
