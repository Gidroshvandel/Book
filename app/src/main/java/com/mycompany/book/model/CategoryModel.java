package com.mycompany.book.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lantiets on 25.12.2017.
 */

public class CategoryModel implements Serializable {

    private String name;

    private ArrayList<BookModel> bookModel;

    public ArrayList<BookModel> getBookModel() {
        return bookModel;
    }

    public void setBookModel(ArrayList<BookModel> bookModel) {
        this.bookModel = bookModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
