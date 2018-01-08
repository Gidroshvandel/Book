package com.mycompany.book.model;

import java.io.Serializable;

/**
 * Created by Lantiets on 24.12.2017.
 */

public class BookModel implements Serializable {

    private String imageUrl;

    private String name;

    private String author;

    private int price;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
