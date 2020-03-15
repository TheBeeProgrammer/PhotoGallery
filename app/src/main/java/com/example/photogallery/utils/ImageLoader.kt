package com.example.photogallery.utils

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, imageView: ImageView)
    fun loadWithResize(url: String, imageView: ImageView,resize:Int)
    fun loadLocal(path: String, imageView: ImageView)
}