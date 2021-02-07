package com.example.musteri_yonetimi.model;
public class Customer {
    protected String id;
    protected String name;
    protected String email;
    protected String phone;
    protected String department;
    protected String product;
    protected String userID;
    protected String userName;

    public Customer() {
    }

    public Customer(String name, String email, String phone, String product, String department) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.product = product;
        this.department = department;
    }


    public Customer(String id, String name, String email, String phone, String product, String department) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.product = product;
        this.department = department;
    }

    public Customer(String id, String name, String email, String phone, String product, String department, String userID) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.product = product;
        this.department = department;
        this.userID = userID;
    }
    public Customer(String id, String name, String email, String phone, String product, String department, String userID, String userName) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.product = product;
        this.department = department;
        this.userID = userID;
        this.userName = userName;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}