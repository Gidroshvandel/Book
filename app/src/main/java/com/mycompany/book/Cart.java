package com.mycompany.book;

import com.mycompany.book.model.BookModel;

import java.util.ArrayList;

public class Cart {

    public static ArrayList<BookModel> bookModels = new ArrayList<>();

    public static int findItemPosition(BookModel bookModel){
        for (int i = 0; i < bookModels.size(); i++) {
            BookModel bookModel1 = bookModels.get(i);
            if(bookModel1.getImageUrl().equals(bookModel.getImageUrl())
                    && bookModel1.getPrice() == bookModel.getPrice()
                    && bookModel1.getName().equals(bookModel.getName())
                    && bookModel1.getAuthor().equals(bookModel.getAuthor())){
                return i;
            }
        }
        return -1;
    }

    public static int sumAllPrice(){
        int price = 0;
        for (BookModel dishModel : Cart.bookModels) {
            price = price + dishModel.getPrice();
        }
        return price;
    }

}
