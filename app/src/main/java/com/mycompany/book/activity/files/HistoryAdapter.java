package com.mycompany.book.activity.files;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycompany.book.R;
import com.mycompany.book.model.BookModel;
import com.mycompany.book.model.UserModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.StatusViewHolder> {

    private ArrayList<UserModel> selectDishModelList;

    public HistoryAdapter(ArrayList<UserModel> selectDishModelList) {
        this.selectDishModelList = selectDishModelList;
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        holder.update();
    }

    @Override
    public int getItemCount() {
        return selectDishModelList.size();
    }

    class StatusViewHolder extends RecyclerView.ViewHolder {

        private TextView address;
        private TextView amount;

        StatusViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            amount = itemView.findViewById(R.id.amount);
        }

        void update() {
            final UserModel selectDishModel = selectDishModelList.get(getAdapterPosition());
            String text = "ул. " + selectDishModel.getStreet() + " д. " + selectDishModel.getBuilding() + " кв. " + selectDishModel.getApartment();
            address.setText(text);
            String text1 = String.valueOf(sumAllPrice(selectDishModel.getBookModel()));
            amount.setText(text1);
        }

        private int sumAllPrice(ArrayList<BookModel> bookModels){
            int price = 0;
            for (BookModel dishModel : bookModels) {
                price = price + dishModel.getPrice();
            }
            return price;
        }
    }

}
