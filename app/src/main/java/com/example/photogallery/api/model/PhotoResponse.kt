package com.example.photogallery.api.model

import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItem: List<GalleryItem>
    
}