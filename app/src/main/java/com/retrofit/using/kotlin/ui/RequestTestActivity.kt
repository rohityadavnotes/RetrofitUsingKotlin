package com.retrofit.using.kotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonObject
import com.retrofit.using.kotlin.R
import com.retrofit.using.kotlin.data.remote.ApiService
import com.retrofit.using.kotlin.data.remote.ApiServiceGenerator.createService
import com.retrofit.using.kotlin.data.remote.RemoteConfiguration
import com.retrofit.using.kotlin.data.remote.glide.GlideCacheUtil
import com.retrofit.using.kotlin.data.remote.glide.GlideImageLoader
import com.retrofit.using.kotlin.data.remote.glide.GlideImageLoadingListener
import com.retrofit.using.kotlin.data.remote.picasso.PicassoImageLoader
import com.retrofit.using.kotlin.data.remote.picasso.PicassoImageLoadingListener
import com.retrofit.using.kotlin.ui.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class RequestTestActivity : BaseActivity() {

    companion object {
        private val TAG = RequestTestActivity::class.java.simpleName
    }

    private lateinit var userImageView: ImageView
    private lateinit var imageLoadingProgressBar: ProgressBar
    private lateinit var progressBar: ProgressBar
    private lateinit var responseTextView: TextView
    private lateinit var requestMaterialButton: MaterialButton
    private lateinit var cancelRequestMaterialButton: MaterialButton

    private var subscribe: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_request_test
    }

    override fun initializeView() {
        userImageView               = findView(R.id.imageView)
        imageLoadingProgressBar     = findView(R.id.imageLoadingProgressBar)
        progressBar                 = findView(R.id.requestProgressBar)
        responseTextView            = findView(R.id.responseTextView)
        requestMaterialButton       = findView(R.id.requestMaterialButton)
        cancelRequestMaterialButton = findView(R.id.cancelRequestMaterialButton)
    }

    override fun initializeObject() {
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
        /*GlideCacheUtil.clearAllCache(this)
        GlideImageLoader.load(
            this,
            "https://backend24.000webhostapp.com/Json/profile.jpg",
            R.drawable.user_placeholder,
            R.drawable.error_placeholder,
            userImageView,
            object : GlideImageLoadingListener {
                override fun imageLoadSuccess() {
                    imageLoadingProgressBar.visibility = View.GONE
                }

                override fun imageLoadError() {
                    imageLoadingProgressBar.visibility = View.GONE
                }
            })*/

        PicassoImageLoader.load(
            this,
            "https://backend24.000webhostapp.com/Json/profile.jpg",
            R.drawable.user_placeholder,
            R.drawable.error_placeholder,
            userImageView,
            object : PicassoImageLoadingListener {
                override fun imageLoadSuccess() {
                    imageLoadingProgressBar.visibility = View.GONE
                }

                override fun imageLoadError(exception: Exception?) {
                    Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT)
                        .show()
                    imageLoadingProgressBar.visibility = View.GONE
                }
            })
    }

    override fun addTextChangedListener() {
    }

    override fun setOnClickListener() {
        requestMaterialButton.setOnClickListener(this)
        cancelRequestMaterialButton.setOnClickListener(this)
    }

    override fun handleClickEvent(view: View?) {
        when (view?.id) {
            R.id.requestMaterialButton -> getSingleEmployee()
            R.id.cancelRequestMaterialButton -> dispose(subscribe)
            else -> {
                println("Invalid view id")
            }
        }
    }

    private fun getSingleEmployee() {
        val apiService = createService(this@RequestTestActivity, ApiService::class.java)

        val observable: Observable<Response<JsonObject>> = apiService.getEmployee()
        val observer: Observer<Response<JsonObject>> = object : Observer<Response<JsonObject>> {
            override fun onSubscribe(disposable: Disposable) {
                progressBar.visibility = View.VISIBLE
                subscribe = disposable
            }

            override fun onError(e: Throwable) {
                progressBar.visibility = View.GONE
                responseTextView.text = e.message
            }

            override fun onNext(response: Response<JsonObject>) {
                progressBar.visibility = View.GONE
                if (response != null) {
                    if (response.body() != null && response.isSuccessful()) {
                        responseTextView.setText(response.body().toString())
                    }
                }
            }

            override fun onComplete() {
            }
        }
        observable
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun getPage() {
        val apiService = createService(this@RequestTestActivity, ApiService::class.java)

        val observable: Observable<Response<JsonObject>> = apiService.getPage(RemoteConfiguration.API_KEY, "1")
        val observer: Observer<Response<JsonObject>> = object : Observer<Response<JsonObject>> {
            override fun onSubscribe(disposable: Disposable) {
                progressBar.visibility = View.VISIBLE
                subscribe = disposable
            }

            override fun onError(e: Throwable) {
                progressBar.visibility = View.GONE
                responseTextView.text = e.message
            }

            override fun onNext(response: Response<JsonObject>) {
                progressBar.visibility = View.GONE
                if (response != null) {
                    if (response.body() != null && response.isSuccessful()) {
                        responseTextView.setText(response.body().toString())
                    }
                }
            }

            override fun onComplete() {
            }
        }
        observable
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose(subscribe)
    }

    /**
     * unsubscribe
     *
     * @param disposable subscription information
     */
    fun dispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
            Log.e(TAG, "Call dispose(Disposable disposable)")
        }
    }
}