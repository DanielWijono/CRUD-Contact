package com.example.daniel.crudcontact.connection;

import android.util.Log;

import com.example.daniel.crudcontact.model.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ConnectionManagerPresenter {

    private ConnectionCallbackPresenter mConnectionCallback;
    private Call mCall;

    public void connect( Call mCall,  ConnectionCallbackPresenter mConnectionCallback) {
        this.mCall = mCall;
        this.mConnectionCallback = mConnectionCallback;
        callAPIRequest();
    }

    public void callAPIRequest() {

        mCall.clone().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i(TAG, "Response code : "+ response.code());
                Log.i(TAG, "Response url  : "+ response.raw().request().url().toString());

                if (response.isSuccessful()) { //ONLY FOR RESPONSE CODE 200
                    System.out.println("Response success");
                    mConnectionCallback.onSuccessResponse(call, response);
                } else {
                    mConnectionCallback.onFailedResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mConnectionCallback.onFailure(call, t.getMessage());
            }
        }) ;
    }
}
