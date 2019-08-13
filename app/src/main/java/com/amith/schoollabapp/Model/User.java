package com.amith.schoollabapp.Model;

public class User {

    private String id;

    private String nameWithInitial;
    private String username;
    private String approve;
    private String imageURL;
    private String email;
    private String phoneNumber;
    private String gender;
    private String status;
    private String search;

    public User(String id, String nameWithInitial, String username, String approve, String imageURL, String email, String  phoneNumber, String gender, String status, String search) {
        this.id = id;
        this.nameWithInitial = nameWithInitial;
        this.username = username;
        this.approve = approve;
        this.imageURL = imageURL;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
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

    public String getNameWithInitial() {
        return nameWithInitial;
    }

    public void setNameWithInitial(String nameWithInitial) {
        this.nameWithInitial = nameWithInitial;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
