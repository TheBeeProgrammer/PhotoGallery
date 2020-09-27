package com.example.photogallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.R
import com.example.photogallery.api.model.GalleryItem
import com.example.photogallery.databinding.PhotosRowBinding
import com.example.photogallery.utils.loadImage


class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoAdapter.Companion.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = DataBindingUtil.inflate<PhotosRowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photos_row,
            parent,
            false
        )
        return PhotoHolder(binding)
    }

    override fun getItemCount() = galleryItems.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItems[position]
        holder.binding.ivPhotos.loadImage(galleryItem.url)
    }


    companion object {
        class PhotoHolder(binding: PhotosRowBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val binding: PhotosRowBinding = binding
        }
    }
}