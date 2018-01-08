package com.mycompany.book.activity.category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mycompany.book.R;
import com.mycompany.book.activity.CartActivity;
import com.mycompany.book.activity.book.BookActivity;
import com.mycompany.book.model.CategoryModel;
import com.mycompany.book.service.ApiService;
import com.mycompany.book.service.DataBaseService;

import java.util.ArrayList;

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
        setTitle("Категории");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        ApiService.getInstance().create(DataBaseService.class).getMenuModel().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                progress.hide();
                initAdapter(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                progress.hide();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

    private void initAdapter(ArrayList<CategoryModel> categoryModels){
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryAdapter adapter = new CategoryAdapter(categoryModels, new CategoryAdapter.ItemActionListener() {
            @Override
            public void onItemClick(int position, CategoryModel menuModel) {
                BookActivity.start(CategoryActivity.this, menuModel);
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
