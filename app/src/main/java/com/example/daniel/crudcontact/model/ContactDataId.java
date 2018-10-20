package com.example.daniel.crudcontact.model;

import com.google.gson.annotations.SerializedName;

public class ContactDataId {
    @SerializedName("data")
    ContactData contactData;

    public ContactData getContactData() {
        return contactData;
    }
}
