package com.mycompany.book.activity.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.book.Cart;
import com.mycompany.book.activity.CartActivity;
import com.mycompany.book.R;
import com.mycompany.book.model.CategoryModel;
import com.mycompany.book.model.BookModel;

public class BookActivity extends AppCompatActivity implements BookAdapter.ItemActionListener {

    private static final String MENU_MODEL = "MENU_MODEL";

    public static void start(Context parentContext, CategoryModel categoryModel) {
        Intent intent = new Intent(parentContext, BookActivity.class);
        intent.putExtra(MENU_MODEL, categoryModel);
        parentContext.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CategoryModel categoryModel = (CategoryModel) getIntent().getSerializableExtra(MENU_MODEL);

        setTitle(categoryModel.getName());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        TextView textView = findViewById(R.id.text_error);

        if(categoryModel.getBookModel() != null){
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            BookAdapter adapter = new BookAdapter(categoryModel.getBookModel(), true, this);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void onItemClick(int position, BookModel bookModel) {
        Cart.bookModels.add(bookModel);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Товар добавлен в корзину!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
