package com.mycompany.book.activity.book;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.book.R;
import com.mycompany.book.model.BookModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.SelectDishViewHolder> {

    private ArrayList<BookModel> bookModelList;
    private ItemActionListener listener;
    private boolean isAdd;

    public BookAdapter(ArrayList<BookModel> bookModelList, boolean isAdd, ItemActionListener listener) {
        this.bookModelList = bookModelList;
        this.listener = listener;
        this.isAdd = isAdd;
    }

    @Override
    public SelectDishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new SelectDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectDishViewHolder holder, int position) {
        holder.update();
    }

    @Override
    public int getItemCount() {
        return bookModelList.size();
    }

    class SelectDishViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView text_view;
        private TextView price;
        private Button btnCart;

        SelectDishViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text_view = itemView.findViewById(R.id.text_view);
            price = itemView.findViewById(R.id.price);
            btnCart = itemView.findViewById(R.id.btn_cart);
        }

        void update() {
            final BookModel bookModel = bookModelList.get(getAdapterPosition());
            if(!TextUtils.isEmpty(bookModel.getImageUrl()))
            Picasso.with(itemView.getContext()).load(bookModel.getImageUrl()).into(image);
            text_view.setText(bookModel.getName());
            String text = bookModel.getPrice() + "Р";
            price.setText(text);
            if(isAdd){
                btnCart.setText("Добавить");
            }else {
                btnCart.setText("Удалить");
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition(), bookModel);
                }
            });
            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition(), bookModel);
                }
            });
        }
    }

    public interface ItemActionListener {
        void onItemClick(int position, BookModel bookModel);
    }

}
