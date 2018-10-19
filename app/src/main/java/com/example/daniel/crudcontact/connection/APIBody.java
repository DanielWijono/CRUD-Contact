package com.example.daniel.crudcontact.connection;

import com.example.daniel.crudcontact.Constants;

import java.util.HashMap;
import java.util.Map;

public class APIBody {

    public static Map<String, Object> addContact (String firstName, String lastName, int age, String photoUrl) {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("age", age);
        body.put("photo", photoUrl);
        return body;
    }

}
