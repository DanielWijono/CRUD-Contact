package com.example.daniel.crudcontact.model;

import com.google.gson.annotations.SerializedName;

public class ContactData extends MainResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("age")
    private int age;
    @SerializedName("photo")
    private String photo;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoto() {
        return photo;
    }
}
