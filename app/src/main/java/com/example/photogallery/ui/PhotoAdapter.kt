package com.example.photogallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.API.model.GalleryItem
import com.example.photogallery.R
import com.example.photogallery.utils.ThumbnailDownloader

class PhotoAdapter(private val galleryItems:List<GalleryItem>) :
    RecyclerView.Adapter<PhotoAdapter.Companion.PhotoHolder>() {
    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoHolder>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view= LayoutInflater.from(parent?.context)
            .inflate(R.layout.photos_row,parent,false)
        return PhotoHolder(view)
    }

    override fun getItemCount() = galleryItems.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItems[position]
        thumbnailDownloader = ThumbnailDownloader()
        thumbnailDownloader.queueThumbNail(holder,galleryItem.url)
    }


    companion object {
        class PhotoHolder(v: View):RecyclerView.ViewHolder(v){
          val ivPhotos:ImageView = v.findViewById(R.id.ivPhotos)
        }
    }
}