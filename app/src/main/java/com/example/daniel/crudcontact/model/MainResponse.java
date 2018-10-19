package com.example.daniel.crudcontact.model;

import com.google.gson.annotations.SerializedName;

public class MainResponse {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
