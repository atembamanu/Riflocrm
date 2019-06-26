package com.techsavanna.technology.riflocrm.apis;

import com.techsavanna.technology.riflocrm.models.ContactsData;
import com.techsavanna.technology.riflocrm.models.LeadsData;
import com.techsavanna.technology.riflocrm.models.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    //url mapping for user login
    @FormUrlEncoded
    @POST("userLogin.php")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );


    //url mapping for leads details
   @GET("getLeads.php")
    Call<List<LeadsData>> getLeads();

   //url mapping for Contacts details
   @GET("getContacts.php")
    Call<List<ContactsData>> getContacts();



}
