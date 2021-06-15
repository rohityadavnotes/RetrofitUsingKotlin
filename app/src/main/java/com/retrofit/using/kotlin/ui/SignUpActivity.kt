package com.retrofit.using.kotlin.ui

import android.os.Bundle
import android.view.View
import com.retrofit.using.kotlin.R
import com.retrofit.using.kotlin.ui.base.BaseActivity

class SignUpActivity : BaseActivity() {

    companion object {
        private val TAG = SignUpActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_sign_up
    }

    override fun initializeView() {
    }

    override fun initializeObject() {
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
    }

    override fun addTextChangedListener() {
    }

    override fun setOnClickListener() {
    }

    override fun handleClickEvent(view: View?) {
    }
}