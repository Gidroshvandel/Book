package com.mycompany.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mycompany.restaurant.model.MenuModel;
import com.mycompany.restaurant.model.SelectDishModel;
import com.mycompany.restaurant.service.DataBaseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, CategoryActivity.class);
        parentContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ApiService.getInstance().create(DataBaseService.class).getMenuModel().enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {
                initAdapter(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {

            }
        });
    }

    private void initAdapter(ArrayList<MenuModel> menuModels){
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MenuAdapter adapter = new MenuAdapter(menuModels, new MenuAdapter.ItemActionListener() {
            @Override
            public void onItemClick(int position, MenuModel menuModel) {
                SelectDishActivity.start(CategoryActivity.this, menuModel);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                showCart();
                return true;
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return true;
        }
    }

    private void showCart(){
        CartActivity.start(this);
    }

}
