package com.example.daniel.crudcontact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daniel.crudcontact.R;
import com.example.daniel.crudcontact.RecyclerViewInterface;
import com.example.daniel.crudcontact.adapter.ContactAdapter;
import com.example.daniel.crudcontact.connection.ConnectionCallbackPresenter;
import com.example.daniel.crudcontact.connection.ConnectionManagerPresenter;
import com.example.daniel.crudcontact.connection.RetrofitService;
import com.example.daniel.crudcontact.model.ContactData;
import com.example.daniel.crudcontact.model.ContactDataId;
import com.example.daniel.crudcontact.model.Contacts;
import com.example.daniel.crudcontact.model.MainResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContactActivity extends AppCompatActivity implements RecyclerViewInterface {

    ConnectionManagerPresenter connectionManagerPresenter;
    RecyclerViewInterface recyclerViewInterface = this;
    List<ContactData> contactDataList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        etSearch.addTextChangedListener(textWatcher);
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

    private void searchContactIdAPI() {
        Call call = RetrofitService.retrofitRequest().getContactBasedOnId(etSearch.getText().toString());
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                ContactDataId contactDataId = (ContactDataId) response.body();
                popUpContactInfo(contactDataId);
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                Toast.makeText(ContactActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                Toast.makeText(ContactActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void popUpContactInfo(ContactDataId contactDataId) {
        AlertDialog alertDialog;
        View dialogView = getLayoutInflater().inflate(R.layout.item_contact_list, null);
        final AlertDialog.Builder popupContact = new AlertDialog.Builder(this);
        popupContact.setView(dialogView);

        ImageView photo = dialogView.findViewById(R.id.img_contact);
        TextView name = dialogView.findViewById(R.id.tv_contact_name);
        TextView age = dialogView.findViewById(R.id.tv_contact_age);

        Glide.with(getApplicationContext()).load(contactDataId.getContactData().getPhoto())
                .placeholder(R.drawable.ic_launcher_background).fitCenter().into(photo);
        name.setText(contactDataId.getContactData().getFirstName() + " " + contactDataId.getContactData().getLastName());
        age.setText(String.valueOf(contactDataId.getContactData().getAge()));

        alertDialog = popupContact.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        getContactListAPI();
    }

    @OnClick({R.id.btn_search, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                searchContactIdAPI();
                break;
            case R.id.fab:
                Intent intent = new Intent(this, AddContactActivity.class);
                startActivity(intent);
                break;
        }
    }
}
