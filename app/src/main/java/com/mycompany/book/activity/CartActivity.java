package com.mycompany.book.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mycompany.book.Cart;
import com.mycompany.book.activity.files.HistoryActivity;
import com.mycompany.book.R;
import com.mycompany.book.activity.book.BookAdapter;
import com.mycompany.book.model.BookModel;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements BookAdapter.ItemActionListener{

    private RecyclerView recyclerView;
    private TextView textView;
    private Button btnSend;

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, CartActivity.class);
        parentContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<BookModel> bookModel = Cart.bookModels;
        setContentView(R.layout.cart_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Корзина покупок");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_view);
        textView = findViewById(R.id.text_error);
        btnSend = findViewById(R.id.btn_send);

        if(bookModel != null && bookModel.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookAdapter adapter = new BookAdapter(bookModel, false, this);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        String text = "Перейти к оформлению\n" + "Общая сумма заказа: " + Cart.sumAllPrice() + " p";
        btnSend.setText(text);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmationActivity.start(CartActivity.this);
            }
        });
    }

    @Override
    public void onItemClick(int position, BookModel bookModel) {
        int pos = Cart.findItemPosition(bookModel);
        if(pos != -1)
            Cart.bookModels.remove(pos);
        String text = "Перейти к оформлению\n\n" + "Общая сумма заказа:\n" + Cart.sumAllPrice() + " p";
        btnSend.setText(text);
        if(Cart.bookModels != null && Cart.bookModels.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookAdapter adapter = new BookAdapter(Cart.bookModels, false, this);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.status_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_status:
                showStatusActivity();
                return true;
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            default:
                return true;
        }
    }

    private void showStatusActivity(){
        HistoryActivity.start(this);
    }
}
