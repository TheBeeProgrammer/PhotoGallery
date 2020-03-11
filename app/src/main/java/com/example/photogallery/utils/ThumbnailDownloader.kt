package com.example.photogallery.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.photogallery.API.network.FlickrFetchr
import java.util.concurrent.ConcurrentHashMap

const val TAG = "ThumbnailDownloader"
private const val MESSAGE_DOWNLOAD = 0
class ThumbnailDownloader<in T>:HandlerThread(TAG), LifecycleObserver {

    private var hasQuit = false
    private var requestHandler = Handler()
    private val requestMap = ConcurrentHashMap<T, String>()
    private val flickrFetchr = FlickrFetchr()


    @Suppress("UNCHECKED_CAST")
    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        requestHandler = object : Handler(){
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    val target = msg.obj as T
                    Log.i(TAG, "Got a request for URL: ${requestMap[target]}")
                    handleRequest(target)
                }
            }
        }
    }

    override fun quit(): Boolean {
        hasQuit=true
        return super.quit()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setup() {
        Log.i(TAG, "Starting background thread")
        start()
        looper
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun tearDown() {
        Log.i(TAG, "Destroying background thread")
        quit()
    }

    fun queueThumbNail(target: T, url:String){
        Log.i(TAG,"Get a url: $url")
        requestMap[target] = url
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget()
    }
    fun handleRequest(target: T){
        val url = requestMap[target] ?: return
        val bitmap = flickrFetchr.fetchPhoto(url) ?: return
    }

}