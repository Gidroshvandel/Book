package com.mycompany.book.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.mycompany.book.R;
import com.mycompany.book.activity.category.CategoryActivity;
import com.mycompany.book.activity.files.HistoryActivity;

public class MainActivity extends AppCompatActivity {

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, MainActivity.class);
        parentContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));

        RelativeLayout menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryActivity.start(MainActivity.this);
            }
        });
        RelativeLayout news = findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsActivity.start(MainActivity.this);

            }
        });
        RelativeLayout photoReports = findViewById(R.id.history);
        photoReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.start(MainActivity.this);

            }
        });
        RelativeLayout delivery = findViewById(R.id.delivery);
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressActivity.start(MainActivity.this);
            }
        });
//        RelativeLayout about = findViewById(R.id.about);
//        about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AboutActivity.start(MainActivity.this);
//            }
//        });

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
            default:
                return true;
        }
    }

    private void showCart(){
        CartActivity.start(this);
    }
}
