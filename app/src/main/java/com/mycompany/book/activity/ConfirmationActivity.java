package com.mycompany.book.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.book.Cart;
import com.mycompany.book.R;
import com.mycompany.book.model.UserModel;
import com.mycompany.book.service.ApiService;
import com.mycompany.book.service.DataBaseService;
import com.mycompany.book.utils.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {

    private EditText phone;
    private EditText surname;
    private EditText name;
    private EditText index;
    private EditText address;
    private EditText building;
    private EditText apartment;
    private TextView amount;
    private Button btnSend;
    private ProgressDialog progress;

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, ConfirmationActivity.class);
        parentContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Оформление заказа");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progress = new ProgressDialog(ConfirmationActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        phone = findViewById(R.id.phone);
        surname = findViewById(R.id.surname);
        name = findViewById(R.id.name);
        index = findViewById(R.id.index);
        address = findViewById(R.id.address);
        building = findViewById(R.id.building);
        apartment = findViewById(R.id.apartment);
        amount = findViewById(R.id.amount);
        String text = Cart.sumAllPrice() + " р";
        amount.setText(text);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phone.getText()) &&
                        !TextUtils.isEmpty(surname.getText()) &&
                        !TextUtils.isEmpty(name.getText()) &&
                        !TextUtils.isEmpty(index.getText()) &&
                        !TextUtils.isEmpty(address.getText()) &&
                        !TextUtils.isEmpty(building.getText()) &&
                        !TextUtils.isEmpty(apartment.getText())) {
                    ArrayList<UserModel> userModels = new ArrayList<>();
                    userModels.add(new UserModel(surname.getText().toString(),
                            name.getText().toString(),
                            index.getText().toString(),
                            address.getText().toString(),
                            apartment.getText().toString(),
                            building.getText().toString(),
                            Cart.bookModels));
                    getInformation(phone.getText().toString(),userModels);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Заполните все поля!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void getInformation(final String phone, final ArrayList<UserModel> userModels){
        progress.show();
        ApiService.getInstance().create(DataBaseService.class).getUserModel(phone).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                if(response.body() != null){
                    userModels.addAll(response.body());
                }
                sendInformation(phone, userModels);
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                progress.hide();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Не удалось отправить заказ", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void sendInformation(final String phone, ArrayList<UserModel> user) {
        ApiService.getInstance().create(DataBaseService.class).sendUserModel(phone, user).enqueue(
                new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        progress.hide();
                        Prefs.with(ConfirmationActivity.this).save(Prefs.CURRENT_PHONE, phone);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваша заявка принята в обработку!", Toast.LENGTH_SHORT);
                        toast.show();
                        Cart.bookModels.clear();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                        progress.hide();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Не удалось отправить заказ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
