package com.mycompany.book.activity.category;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.book.R;
import com.mycompany.book.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MenuViewHolder> {

    private List<CategoryModel> categoryModels;

    private ItemActionListener listener;
    private int colorCount;

    public CategoryAdapter(List<CategoryModel> categoryModels, ItemActionListener listener) {
        this.categoryModels = categoryModels;
        this.listener = listener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.update();
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView ivIcon;

        MenuViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_title);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }

        void update() {
            int[] androidColors = itemView.getResources().getIntArray(R.array.categoryPalette);
            colorCount ++;
            if(colorCount > androidColors.length-1){
                colorCount = 0;
            }

            Drawable drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.circle);
            drawable.setColorFilter(androidColors[colorCount], PorterDuff.Mode.LIGHTEN);

            ivIcon.setImageDrawable(drawable);
            final CategoryModel categoryModel = categoryModels.get(getAdapterPosition());
            text.setText(categoryModel.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition(), categoryModel);
                }
            });
        }
    }

    public interface ItemActionListener {
        void onItemClick(int position, CategoryModel categoryModel);
    }
}
