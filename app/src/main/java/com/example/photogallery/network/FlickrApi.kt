package com.example.photogallery.network

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {
    @GET("/")
    fun fetchData(): Call<String>
}