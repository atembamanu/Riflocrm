package com.techsavanna.technology.riflocrm.models;

public class User {
    private int id;
    private String  first_name, last_name, email1;

    public User(int id, String first_name, String last_name, String email1) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email1 = email1;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail1() {
        return email1;
    }
}
