package com.example.photogallery.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.R
import com.example.photogallery.network.FlickrApi
import retrofit2.Retrofit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotoGalleryFragment : Fragment() {


    private lateinit var rvPhotos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        rvPhotos = view.findViewById(R.id.rvPhotos)
        rvPhotos.layoutManager = GridLayoutManager(context, 3)
        initRetrofit()
        return view
    }

    private fun initRetrofit() {
        val retrofit =
            Retrofit.Builder().baseUrl("https://www.flickr.com/").addConverterFactory(ScalarsConverterFactory.create()).build()
        val flickrApi = retrofit.create(FlickrApi::class.java)
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()

    }
}


