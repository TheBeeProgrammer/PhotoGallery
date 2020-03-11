package com.example.photogallery.API.network

import com.example.photogallery.API.model.FlickrResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FlickrApi {
    @GET("services/rest/" +
            "?method=flickr.interestingness.getList&" +
            "api_key=dd54a23d08e4e777760b5cea178b73be" +
            "&format=json&nojsoncallback=1&extras=url_s")
    fun fetchData(): Call<FlickrResponse>

    @GET
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>
}