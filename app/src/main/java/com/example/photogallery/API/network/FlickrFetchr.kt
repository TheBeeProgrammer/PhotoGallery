package com.example.photogallery.api.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.api.model.FlickrResponse
import com.example.photogallery.api.model.GalleryItem
import com.example.photogallery.api.model.PhotoResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"

class FlickrFetchr {
    private val flickrApi: FlickrApi
    private lateinit var flickrCall: Call<FlickrResponse>

    init {

        val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhoto(): LiveData<List<GalleryItem>> {
        return fetchPhotoMetaData(flickrApi.fetchData())
    }

    fun searchPhotos(query: String): LiveData<List<GalleryItem>> {
        return fetchPhotoMetaData(flickrApi.searchPhotos(query))
    }

    private fun fetchPhotoMetaData(flickrRequest: Call<FlickrResponse>): LiveData<List<GalleryItem>> {
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        flickrRequest.enqueue(object : Callback<FlickrResponse> {
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "Error Fetching the data", t)
            }

            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>
            ) {
                Log.d(TAG, "Response: ${response.body()}")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                val galleryItem: List<GalleryItem>? = photoResponse?.galleryItem ?: mutableListOf()
                galleryItem?.filter {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItem
            }
        })
        return responseLiveData
    }

    fun cancelRequestInFlight() {
        if (::flickrCall.isInitialized) {
            flickrCall.cancel()
        }
    }


}