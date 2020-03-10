package com.example.photogallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photogallery.fragments.PhotoGalleryFragment

class PhotoGalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, PhotoGalleryFragment.newInstance()).commit()

        }
    }
}
