package com.example.daniel.crudcontact.connection;

import com.example.daniel.crudcontact.Constants;
import com.example.daniel.crudcontact.model.ContactData;
import com.example.daniel.crudcontact.model.Contacts;
import com.example.daniel.crudcontact.model.MainResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APICollections {

    @GET(Constants.URL_API.CONTACT)
    Call<Contacts> getContactDataList();

    @POST(Constants.URL_API.CONTACT)
    Call<MainResponse> addContact(@Body Map<String, Object> body);

}
