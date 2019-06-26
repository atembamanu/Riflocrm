package com.techsavanna.technology.riflocrm.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.techsavanna.technology.riflocrm.MainActivity;
import com.techsavanna.technology.riflocrm.R;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    //Initializing the textviews
    TextView textviewLead, textviewContacts,textViewCampaigns, textViewOrganizations,
            textViewQuotes, textViewProducts, textViewOpportunities, textViewWarrants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        //Bindind the onclick listener to the Textviews

        textviewLead = findViewById(R.id.textViewLeads);
        textviewLead.setOnClickListener(this);
        textviewContacts = findViewById(R.id.textViewContact);
        textviewContacts.setOnClickListener(this);
        textViewCampaigns = findViewById(R.id.textViewCampaign);
        textViewCampaigns.setOnClickListener(this);
        textViewOrganizations = findViewById(R.id.textViewOrganization);
        textViewOrganizations.setOnClickListener(this);
        textViewQuotes = findViewById(R.id.textViewQuotation);
        textViewQuotes.setOnClickListener(this);
        textViewProducts = findViewById(R.id.textViewProduction);
        textViewProducts.setOnClickListener(this);
        textViewOpportunities = findViewById(R.id.textViewOpportunity);
        textViewOpportunities.setOnClickListener(this);
        textViewWarrants = findViewById(R.id.textViewWarrant);
        textViewWarrants.setOnClickListener(this);

    }

    //overiding the onclick class for all possible widgets which require onclick listener event

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.textViewLeads:
                manageLeads();
                break;
            case R.id.textViewContact:
                manageContacts();
                break;
            case R.id.textViewCampaign:
                manageCampaigns();
                break;
            case R.id.textViewOrganization:
                manageOrganizations();
                break;
            case R.id.textViewQuotation:
                manageQuotations();
                break;
            case R.id.textViewProduction:
                manageProducts();
                break;
            case R.id.textViewOpportunity:
                manageOpportunities();
                break;
            case R.id.textViewWarrant:
                manageWarrants();
                break;
                
                
                
        }
    }

    //Private methods to handle after onclick operations

    private void manageWarrants() {
    }

    private void manageOpportunities() {
    }

    private void manageProducts() {
    }

    private void manageQuotations() {
    }

    private void manageOrganizations() {
    }

    private void manageCampaigns() {
    }

    private void manageContacts() {
        Intent intent = new Intent(Dashboard.this, Contacts.class);
        startActivity(intent);
        
    }

    private void manageLeads() {
        Intent intent = new Intent(Dashboard.this, Leads.class);
        startActivity(intent);
    }
}
