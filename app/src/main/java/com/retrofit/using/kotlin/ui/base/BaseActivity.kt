package com.retrofit.using.kotlin.ui.base

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    /**
     ***********************************************************************************************
     ******************************************** Properties ***************************************
     ***********************************************************************************************
     */
    private lateinit var viewSparseArray: SparseArray<View>

    /**
     ***********************************************************************************************
     ********************************* BaseActivity abstract methods *******************************
     ***********************************************************************************************
     */
    @LayoutRes
    protected abstract fun getLayoutID(): Int
    protected abstract fun initializeView()
    protected abstract fun initializeObject()
    protected abstract fun initializeToolBar()
    protected abstract fun initializeCallbackListener()
    protected abstract fun addTextChangedListener()
    protected abstract fun setOnClickListener()
    protected abstract fun handleClickEvent(view: View?)

    /**
     ***********************************************************************************************
     ************************************ Activity lifecycle methods *******************************
     ***********************************************************************************************
     */
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate(Bundle savedInstanceState)")

        viewSparseArray = SparseArray()

        setContentView(getLayoutID())

        initializeView()
        initializeObject()
        initializeToolBar()
        initializeCallbackListener()
        addTextChangedListener()
        setOnClickListener()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart()")
    }

    @CallSuper
    override fun onRestart() { /* Only called after onStop() */
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy()")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.i(TAG, "onBackPressed()")
    }

    /**
     * Find the view control and put it in the collection
     *
     * @param viewID the id of the view to be found
     * @param <E>    the returned view
     * @return
    </E> */
    fun <E : View?> findView(viewID: Int): E {
        var view: E = viewSparseArray[viewID] as E
        if (view == null) {
            /* If the searched view is not in the collection, add it to the collection */
            view = findViewById<View>(viewID) as E
            if (view != null) {
                viewSparseArray.append(viewID, view)
            }
        }
        return view
    }

    /**
     * View's click event
     *
     * @param v
     */
    override fun onClick(v: View) {
        handleClickEvent(v)
    }

    /**
     * Set the click event to the view
     *
     * @param view The view bound to the event
     * @param <E>
    </E> */
    fun <E : View?> setOnClick(view: E) {
        view?.setOnClickListener(this)
    }
}