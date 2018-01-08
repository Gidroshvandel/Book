package com.mycompany.book.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lantiets on 26.12.2017.
 */

public class UserModel implements Serializable {

    private String surname;

    private String name;

    private String index;

    private String street;

    private String apartment;

    private String building;

    private ArrayList<BookModel> bookModel;

    public UserModel(String surname, String name, String index, String street, String apartment, String building, ArrayList<BookModel> bookModel) {
        this.surname = surname;
        this.name = name;
        this.index = index;
        this.street = street;
        this.apartment = apartment;
        this.building = building;
        this.bookModel = bookModel;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public ArrayList<BookModel> getBookModel() {
        return bookModel;
    }

    public void setBookModel(ArrayList<BookModel> bookModel) {
        this.bookModel = bookModel;
    }
}
