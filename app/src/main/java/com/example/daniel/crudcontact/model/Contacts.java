package com.example.daniel.crudcontact.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contacts extends MainResponse {
    @SerializedName("data")
    List<ContactData> contactDataList;

    public List<ContactData> getContactDataList() {
        return contactDataList;
    }
}
