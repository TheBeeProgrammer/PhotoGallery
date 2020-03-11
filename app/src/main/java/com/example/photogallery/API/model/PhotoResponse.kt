package com.example.photogallery.API.model

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItem: List<GalleryItem>
    
}