package com.example.daniel.crudcontact.connection;

import com.example.daniel.crudcontact.Constants;
import com.example.daniel.crudcontact.model.ContactData;
import com.example.daniel.crudcontact.model.Contacts;
import com.example.daniel.crudcontact.model.MainResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APICollections {

    @GET(Constants.URL_API.CONTACT)
    Call<Contacts> getContactDataList();

    @POST(Constants.URL_API.CONTACT)
    Call<MainResponse> addContact(@Body Map<String, Object> body);

    @DELETE("contact/{id}")
    Call<MainResponse> deleteContact(@Path(value = "id", encoded = true) String id);

    @GET("contact/{id}")
    Call<MainResponse> getContactBasedOnId(@Path(value = "id", encoded = true) String id);

    @PUT("contact/{id}")
    Call<MainResponse> editContact(@Path(value = "id", encoded = true) String id, @Body Map<String, Object> body);

}
