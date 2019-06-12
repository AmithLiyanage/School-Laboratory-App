package com.amith.schoollabapp.Model;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private String email;
    private String  phoneNumber;
    private String status;
    private String search;

    public User(String id, String username, String imageURL, String email, String  phoneNumber, String status, String search) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.search = search;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getPhoneNumbe() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
