package com.pmdm.university.interfaz;

import com.pmdm.university.entidad.University;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IUniversityApiService {

    @GET("search?country=Spain")
    Call<List<University>> getUniversities();
}
