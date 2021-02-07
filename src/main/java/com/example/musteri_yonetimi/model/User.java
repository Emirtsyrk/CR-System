package com.example.musteri_yonetimi.model;

public class User {
    protected String id;
    protected String name;
    protected String password;
    protected String numberOfCustomer;

    public User() {
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String id, String name, String password, String numberOfCustomer) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.numberOfCustomer = numberOfCustomer;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberOfCustomer() {return numberOfCustomer; }
    public void setNumberOfCustomer(String numberOfCustomer) { this.numberOfCustomer = numberOfCustomer; }
}