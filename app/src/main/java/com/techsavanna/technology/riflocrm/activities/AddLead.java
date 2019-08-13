package com.techsavanna.technology.riflocrm.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.techsavanna.technology.riflocrm.BuildConfig;
import com.techsavanna.technology.riflocrm.R;
import com.techsavanna.technology.riflocrm.apis.Api;
import com.techsavanna.technology.riflocrm.models.AddLeads;
import com.techsavanna.technology.riflocrm.network.RetrofitClientInstance;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class AddLead extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editTextUser, editTextUser2, editTextUserPhone,
            editTextUserEmail, editTextExactLocation, editTextCountry, editTextCity;

    private String URL = "http://192.168.44.127/riflocrm/RiflocrmApp/api/getLeadStatus.php";
    private ArrayList<String> mystatus;
    private Spinner spinner;

    //start LocationUpdates
    AppCompatButton buttonGoogleMaps;

    //save the New Lead and
    //stop the LocationUpdates
    AppCompatTextView btn_save;
    private String selectedStatus;


    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;



    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui


    //My Tag
    private static final String TAG = "GoogleMaps";

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btn_save = findViewById(R.id.btn_save);
        buttonGoogleMaps = findViewById(R.id.btntGoogle);

        editTextUser = findViewById(R.id.editTextUser11);
        editTextUser2 = findViewById(R.id.editTextUser21);
        editTextUserPhone = findViewById(R.id.editTextPhone1);
        editTextUserEmail = findViewById(R.id.editTextUserEmail1);
        editTextExactLocation = findViewById(R.id.editTextExactLocation);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextCity = findViewById(R.id.editTextCity);

        spinner = findViewById(R.id.statusId);

        btn_save.setOnClickListener(this);
        buttonGoogleMaps.setOnClickListener(this);



        // initialize the necessary libraries
        init();

        // restore the values from saved instance state
        //restoreValuesFromBundle(savedInstanceState);

        mystatus=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.statusId);
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatus=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        //mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
               // mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                //mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            editTextExactLocation.setText(
                    mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude()
            );


            // giving a blink animation on TextView
            editTextExactLocation.setAlpha(0);
            editTextExactLocation.animate().alpha(1).setDuration(300);

            // location last updated time
           // txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);
        }

        toggleButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        //outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
            buttonGoogleMaps.setEnabled(false);
            //btnStopUpdates.setEnabled(true);
        } else {
            buttonGoogleMaps.setEnabled(true);
            //btnStopUpdates.setEnabled(false);
        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Locating...", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(AddLead.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(AddLead.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    public void startLocationButtonClick() {
        editTextExactLocation.setEnabled(false);
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }



    private void loadSpinnerData(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                //Log.d("strrrrr", ">>" + response);
                JSONArray responses = new JSONArray(response);

                for (int i=0;i<responses.length();i++){
                    JSONObject responseObject = responses.getJSONObject(i);

                    String organization1 = responseObject.getString("leadstatus");


                    //System.out.println("techies "+organization1);

                    mystatus.add(organization1);
                }

                spinner.setAdapter(new ArrayAdapter<>(AddLead.this, R.layout.myspinner, mystatus));
            }catch (JSONException e){
                e.printStackTrace();

            }
        }
    },
            new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });
    int socketTimeout = 30000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    stringRequest.setRetryPolicy(policy);
    RequestQueue requestQueue = Volley.newRequestQueue(AddLead.this);
    requestQueue.add(stringRequest);
}

    private void insertLeads() {

        Api service = RetrofitClientInstance.getRetrofitInstance().create(Api.class);

        final AddLeads addLeads = new AddLeads();
        addLeads.setFirstname(editTextUser.getText().toString().trim());
        addLeads.setLastname(editTextUser2.getText().toString().trim());
        addLeads.setEmail(editTextUserEmail.getText().toString().trim());
        addLeads.setPhone(editTextUserPhone.getText().toString().trim());
        addLeads.setCity(editTextCity.getText().toString().trim());
        addLeads.setCountry(editTextCountry.getText().toString().trim());
        addLeads.setMap(editTextExactLocation.getText().toString().trim());
        addLeads.setStatus((selectedStatus));
        //addLeads.setStatus(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
        //addLeads.setMap(mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());



        Call<AddLeads> call = service.insertLead(addLeads.getFirstname(), addLeads.getLastname(),
                addLeads.getEmail(), addLeads.getPhone(),
                addLeads.getCity(), addLeads.getCountry(), addLeads.getMap(), addLeads.getStatus());

        call.enqueue(new Callback<AddLeads>() {
            @Override
            public void onResponse(@NotNull Call<AddLeads> call,
                                   @NotNull retrofit2.Response<AddLeads> response) {
                response.body();
                System.out.println("testing aplrt "+response.body().toString());
                System.out.println("world "+addLeads.getFirstname());

                System.out.println("world3 "+addLeads.getMap());



                //test the model data
                Log.d("Testing Data", ">> "+
                        addLeads.getFirstname() +" > "+
                        addLeads.getLastname()+" > "+
                        addLeads.getEmail()+" > "+
                        addLeads.getPhone()+" > "+
                        addLeads.getCity()+" > "+
                        addLeads.getCountry()+" > "+
                        addLeads.getMap()+" > "+
                        addLeads.getStatus());

                //Reset the edit texts

                editTextUser.setText("");
                editTextUser2.setText("");
                editTextUserEmail.setText("");
                editTextUserPhone.setText("");
                editTextCity.setText("");
                editTextCity.setText("");
                editTextCountry.setText("");



                Intent intent = new Intent(AddLead.this, Leads.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<AddLeads> call, Throwable t) {
                Log.i("Hello",""+t);
                //Toast.makeText(AddLead.this, "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });



    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_save:
                insertLeads();
                break;
            case R.id.btntGoogle:
                startLocationButtonClick();
                break;


        }
    }

}

