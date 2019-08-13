package com.techsavanna.technology.riflocrm.models;

public class LeadsData {
    private int id;
    private String leadname, leadstatus, mobile, city, country, map;

    public LeadsData(int id, String leadname, String leadstatus, String mobile, String city, String country, String map) {
        this.id = id;
        this.leadname = leadname;
        this.leadstatus = leadstatus;
        this.mobile = mobile;
        this.city = city;
        this.country = country;
        this.map = map;
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

    public String getMap() {
        return map;
    }
}


