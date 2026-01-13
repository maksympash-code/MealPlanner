package com.example.mealplanner.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitServiceApiFactory {
    // https://api.spoonacular.com/recipes/complexSearch
    private const val BASE_URL: String = "https://api.spoonacular.com/"

    fun create(): RetrofitService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}