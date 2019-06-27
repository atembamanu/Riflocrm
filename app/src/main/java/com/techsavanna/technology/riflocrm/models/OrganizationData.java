package com.techsavanna.technology.riflocrm.models;

public class OrganizationData {

    int id;
    String organization, city, country, phone1, phone2;

    public OrganizationData(int id, String organization, String city, String country, String phone1, String phone2) {
        this.id = id;
        this.organization = organization;
        this.city = city;
        this.country = country;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public int getId() {
        return id;
    }

    public String getOrganization() {
        return organization;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }
}
