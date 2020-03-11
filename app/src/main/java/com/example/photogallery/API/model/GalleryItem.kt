package com.example.photogallery.API.model

import com.google.gson.annotations.SerializedName

data class GalleryItem(var title: String = "",
                       var name: String = "",
                       @SerializedName(" url_s")var url: String = "")