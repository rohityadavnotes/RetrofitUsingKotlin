package com.retrofit.using.kotlin.data.remote.picasso

interface PicassoImageLoadingListener {
    fun imageLoadSuccess()
    fun imageLoadError(exception: Exception?)
}