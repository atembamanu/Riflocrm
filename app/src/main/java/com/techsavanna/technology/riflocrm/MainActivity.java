package com.techsavanna.technology.riflocrm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techsavanna.technology.riflocrm.activities.Dashboard;
import com.techsavanna.technology.riflocrm.network.RetrofitClient;
import com.techsavanna.technology.riflocrm.models.LoginResponse;
import com.techsavanna.technology.riflocrm.storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private EditText editTextUsername,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        editTextUsername=findViewById(R.id.editTextUsername);
        editTextPassword=findViewById(R.id.editTextPassword);



    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String username = Objects.requireNonNull(editTextUsername.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 5) {
            editTextPassword.setError("Password should be atleast 5 character long");
            editTextPassword.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                assert loginResponse != null;
                if (!loginResponse.isError()) {
                    finish();

                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(loginResponse.getUser());
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
