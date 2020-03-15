package com.example.photogallery.api.model

import com.google.gson.annotations.SerializedName

data class GalleryItem(var title: String = "",
                       var name: String = "",
                       @SerializedName("url_s")var url: String = "")