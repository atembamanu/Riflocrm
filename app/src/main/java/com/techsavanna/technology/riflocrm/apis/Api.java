package com.techsavanna.technology.riflocrm.apis;

import com.google.gson.JsonArray;
import com.techsavanna.technology.riflocrm.models.AddContacts;
import com.techsavanna.technology.riflocrm.models.AddLeads;
import com.techsavanna.technology.riflocrm.models.ContactsData;
import com.techsavanna.technology.riflocrm.models.LeadsData;
import com.techsavanna.technology.riflocrm.models.LoginResponse;
import com.techsavanna.technology.riflocrm.models.OrganizationData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
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

   //url maping for Organizations data
    @GET("getOrganizations.php")
    Call<List<OrganizationData>> getOrganizations();



    //post contacts
    @FormUrlEncoded
    @POST("addContacts.php")
    Call<AddContacts> insertContact(
            @Field("firstname") String firstname,
            @Field("lastname")String lastname,
            @Field("email")String email,
            @Field("phone")String phone,
            @Field("title")String title);

    //postleads
    @FormUrlEncoded
    @POST("addLeads.php")
    Call<AddLeads> insertLead(
            @Field("firstname") String firstname,
            @Field("lastname")String lastname,
            @Field("email")String email,
            @Field("phone")String phone,
            @Field("city") String city,
            @Field("country")String country,
            @Field("map")String map,
            @Field("status")String status);

    @GET("getLeadStatus.php")
    void getJsonStatus(Callback<JsonArray> callback);



}
