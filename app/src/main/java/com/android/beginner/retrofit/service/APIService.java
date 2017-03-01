package com.android.beginner.retrofit.service;

import com.android.beginner.retrofit.model.Student;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;


/**
 * Created by root on 27/02/17.
 */

public interface APIService {
    @GET("index.php")
    Call<List<Student>> getPeopleDetails();
}
