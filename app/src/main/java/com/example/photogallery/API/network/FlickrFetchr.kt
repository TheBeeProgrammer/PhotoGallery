package com.example.photogallery.API.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photogallery.API.model.FlickrResponse
import com.example.photogallery.API.model.GalleryItem
import com.example.photogallery.API.model.PhotoResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body

private const val TAG = "FlickrFetchr"

class FlickrFetchr {
    private val flickrApi: FlickrApi
    private lateinit var flickrCall:Call<FlickrResponse>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhotos():LiveData<List<GalleryItem>>{
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()
        val flickrRequest: Call<FlickrResponse> = flickrApi.fetchData()
        flickrRequest.enqueue(object : Callback<FlickrResponse>{
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG,"Error Fetching the data", t)
            }

            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                Log.d(TAG,"Response: ${response.body()}")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                val galleryItem:List<GalleryItem>? = photoResponse?.galleryItem?: mutableListOf()
                galleryItem?.filter {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItem

            }
        })
        return responseLiveData
    }

    fun cancelRequestInFlight(){
        if(::flickrCall.isInitialized){flickrCall.cancel()}
    }

    @WorkerThread
    fun fetchPhoto(url:String):Bitmap?{
        val response: Response<ResponseBody> = flickrApi.fetchUrlBytes(url).execute()
        val bitmap=response.body()?.byteStream().use(BitmapFactory::decodeStream)
        Log.i(TAG,"Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }
}