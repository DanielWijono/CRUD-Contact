package com.example.daniel.crudcontact.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.daniel.crudcontact.R;
import com.example.daniel.crudcontact.RecyclerViewInterface;
import com.example.daniel.crudcontact.adapter.ContactAdapter;
import com.example.daniel.crudcontact.connection.ConnectionCallbackPresenter;
import com.example.daniel.crudcontact.connection.ConnectionManagerPresenter;
import com.example.daniel.crudcontact.connection.RetrofitService;
import com.example.daniel.crudcontact.model.ContactData;
import com.example.daniel.crudcontact.model.Contacts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity implements RecyclerViewInterface {

    ConnectionManagerPresenter connectionManagerPresenter;
    RecyclerViewInterface recyclerViewInterface = this;
    List<ContactData> contactDataList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        getContactListAPI();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ContactAdapter contactAdapter = new ContactAdapter(this, contactDataList, recyclerViewInterface);
        recyclerView.setAdapter(contactAdapter);
    }

    private void getContactListAPI() {
        Call call = RetrofitService.retrofitRequest().getContactDataList();
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                Contacts contacts = (Contacts) response.body();
                contactDataList = contacts.getContactDataList();
                initRecyclerView();
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                Toast.makeText(ContactActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                Toast.makeText(ContactActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRecyclerViewClicked(int position) {
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("id", contactDataList.get(position).getId());
        intent.putExtra("firstname", contactDataList.get(position).getFirstName());
        intent.putExtra("lastname", contactDataList.get(position).getLastName());
        intent.putExtra("age", contactDataList.get(position).getAge());
        intent.putExtra("photourl", contactDataList.get(position).getPhoto());
        startActivity(intent);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContactListAPI();
    }
}
