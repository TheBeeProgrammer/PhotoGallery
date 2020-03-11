package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.API.model.GalleryItem
import com.example.photogallery.API.network.FlickrFetchr

class PhotoGalleryViewModel : ViewModel() {
    val galleryItemLiveData:LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()
    private val flickrFetchr = FlickrFetchr()
    override fun onCleared() {
        super.onCleared()
        flickrFetchr.cancelRequestInFlight()
    }
}