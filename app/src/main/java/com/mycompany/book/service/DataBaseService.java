package com.mycompany.book.service;

import com.mycompany.book.model.CategoryModel;
import com.mycompany.book.model.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataBaseService {
    @GET("/dataBase/categoryModel.json")
    Call<ArrayList<CategoryModel>> getMenuModel();

    @PUT("/dataBase/{new}.json")
    Call<ArrayList<UserModel>> sendUserModel(@Path("new") String phone, @Body ArrayList<UserModel> userModels);

    @GET("/dataBase/{new}.json")
    Call<ArrayList<UserModel>> getUserModel(@Path("new") String phone);
}
