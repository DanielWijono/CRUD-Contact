package com.example.daniel.crudcontact.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.daniel.crudcontact.R;
import com.example.daniel.crudcontact.connection.APIBody;
import com.example.daniel.crudcontact.connection.ConnectionCallbackPresenter;
import com.example.daniel.crudcontact.connection.ConnectionManagerPresenter;
import com.example.daniel.crudcontact.connection.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_photo_url)
    EditText etPhotoUrl;
    @BindView(R.id.btn_add_contact)
    Button btnAddContact;
    @BindView(R.id.btn_edit_contact)
    Button btnEditContact;
    @BindView(R.id.btn_delete_contact)
    Button btnDeleteContact;

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

    ConnectionManagerPresenter connectionManagerPresenter;
    Bundle bundle;
    String id, firstName, lastName, photoUrl;
    int age = 909090;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etAge.addTextChangedListener(textWatcher);
        etPhotoUrl.addTextChangedListener(textWatcher);

        btnAddContact.setVisibility(View.VISIBLE);
        btnDeleteContact.setVisibility(View.GONE);
        btnEditContact.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            firstName = bundle.getString("firstname");
            lastName = bundle.getString("lastname");
            age = bundle.getInt("age");
            photoUrl = bundle.getString("photourl");
        }
        setViewBundleExist();
    }

    private void setViewBundleExist() {
        if (id != null && firstName != null && lastName != null && age != 909090 && photoUrl != null) {
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etAge.setText(String.valueOf(age));
            etPhotoUrl.setText(photoUrl);

            btnAddContact.setVisibility(View.GONE);
            btnDeleteContact.setVisibility(View.VISIBLE);
            btnEditContact.setVisibility(View.VISIBLE);
        }
    }

    private void deletePopUpDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this contact ? ")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteContactAPI();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void deleteContactAPI() {
        progressBar.setVisibility(View.VISIBLE);
        Call call = RetrofitService.retrofitRequest().deleteContact(id);
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, "SUKSES DELETE", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addContactAPI() {
        progressBar.setVisibility(View.VISIBLE);
        Call call = RetrofitService.retrofitRequest().addContact(APIBody.addContact(etFirstName.getText().toString(), etLastName.getText().toString(),
                Integer.valueOf(etAge.getText().toString()), etPhotoUrl.getText().toString()));
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editContactAPI() {
        progressBar.setVisibility(View.VISIBLE);
        Call call = RetrofitService.retrofitRequest().editContact(id, APIBody.addContact(etFirstName.getText().toString(), etLastName.getText().toString(),
                Integer.valueOf(etAge.getText().toString()), etPhotoUrl.getText().toString()));
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddContactActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btn_add_contact, R.id.btn_edit_contact, R.id.btn_delete_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_contact:
                if (etFirstName.length() > 0 && etLastName.length() > 0 && etAge.length() > 0 && etPhotoUrl.length() > 0) {
                    addContactAPI();
                } else {
                    Toast.makeText(this, "data yang anda isi belum lengkap", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_edit_contact:
                editContactAPI();
                break;
            case R.id.btn_delete_contact:
                deletePopUpDialog();
                break;
        }
    }
}
