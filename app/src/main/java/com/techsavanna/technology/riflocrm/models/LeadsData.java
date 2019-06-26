package com.techsavanna.technology.riflocrm.models;

public class LeadsData {
    private int id;
    private String leadname, leadstatus, mobile, city, country;

    public LeadsData(int id, String leadname, String leadstatus, String mobile, String city, String country) {
        this.id = id;
        this.leadname = leadname;
        this.leadstatus = leadstatus;
        this.mobile = mobile;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getLeadname() {
        return leadname;
    }

    public String getLeadstatus() {
        return leadstatus;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}


