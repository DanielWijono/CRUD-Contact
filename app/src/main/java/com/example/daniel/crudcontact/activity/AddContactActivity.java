package com.example.daniel.crudcontact.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etAge.addTextChangedListener(textWatcher);
        etPhotoUrl.addTextChangedListener(textWatcher);
    }

    private void addContactAPI() {
        Call call = RetrofitService.retrofitRequest().addContact(APIBody.addContact(etFirstName.getText().toString(), etLastName.getText().toString(),
                Integer.valueOf(etAge.getText().toString()), etPhotoUrl.getText().toString()));
        connectionManagerPresenter = new ConnectionManagerPresenter();
        connectionManagerPresenter.connect(call, new ConnectionCallbackPresenter() {
            @Override
            public void onSuccessResponse(Call call, Response response) {
                finish();
            }

            @Override
            public void onFailedResponse(Call call, Response response) {
                Toast.makeText(AddContactActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, String message) {
                Toast.makeText(AddContactActivity.this, "FAILURE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_add_contact)
    public void onViewClicked() {
        if (etFirstName.length() > 0 && etLastName.length() > 0 && etAge.length() > 0 && etPhotoUrl.length() > 0) {
            addContactAPI();
        } else {
            Toast.makeText(this, "data yang anda isi belum lengkap", Toast.LENGTH_SHORT).show();
        }

    }
}
