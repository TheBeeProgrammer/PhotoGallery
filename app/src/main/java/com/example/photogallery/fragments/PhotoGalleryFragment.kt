package com.example.photogallery.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.API.model.GalleryItem
import com.example.photogallery.R
import com.example.photogallery.API.network.FlickrFetchr
import com.example.photogallery.ui.PhotoAdapter
import com.example.photogallery.utils.ThumbnailDownloader
import com.example.photogallery.viewmodel.PhotoGalleryViewModel


private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {


    private lateinit var rvPhotos: RecyclerView
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoAdapter:PhotoAdapter
    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoAdapter.Companion.PhotoHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance=true
        photoGalleryViewModel= ViewModelProviders.of(this).get(PhotoGalleryViewModel::class.java)
        thumbnailDownloader = ThumbnailDownloader()
        lifecycle.addObserver(thumbnailDownloader)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        rvPhotos = view.findViewById(R.id.rvPhotos)
        rvPhotos.layoutManager = GridLayoutManager(context, 3)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            photoGalleryViewModel.galleryItemLiveData.observe(viewLifecycleOwner, Observer { galleryItems->
                Log.d(TAG,"Items From ViewModel $galleryItems")
                photoAdapter=PhotoAdapter(galleryItems)
                rvPhotos.adapter=photoAdapter
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(thumbnailDownloader)
    }
    companion object {
        fun newInstance() = PhotoGalleryFragment()

    }
}


