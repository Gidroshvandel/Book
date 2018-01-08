package com.mycompany.book.activity.files;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.book.R;
import com.mycompany.book.model.UserModel;
import com.mycompany.book.service.ApiService;
import com.mycompany.book.service.DataBaseService;
import com.mycompany.book.utils.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, HistoryActivity.class);
        parentContext.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("История заказов");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        String phone = Prefs.with(HistoryActivity.this).getString(Prefs.CURRENT_PHONE, null);
        TextView tvPhone = findViewById(R.id.phone);
        final TextView text_error = findViewById(R.id.text_error);

        tvPhone.setText(phone);

        recyclerView = findViewById(R.id.recycler_view);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        ApiService.getInstance().create(DataBaseService.class).getUserModel(phone).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                progress.hide();
                setAdapter(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                progress.hide();
                recyclerView.setVisibility(View.GONE);
                text_error.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Не удалось отправить заказ", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return true;
        }
    }

    private void setAdapter(ArrayList<UserModel> userModels){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter adapter = new HistoryAdapter(userModels);
        recyclerView.setAdapter(adapter);
    }

}
