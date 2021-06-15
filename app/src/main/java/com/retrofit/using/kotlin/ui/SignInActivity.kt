package com.retrofit.using.kotlin.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.retrofit.using.kotlin.R
import com.retrofit.using.kotlin.data.remote.ApiService
import com.retrofit.using.kotlin.data.remote.ApiServiceGenerator.createService
import com.retrofit.using.kotlin.model.BaseResponse
import com.retrofit.using.kotlin.model.Data
import com.retrofit.using.kotlin.ui.base.BaseActivity
import com.retrofit.using.kotlin.utilities.ActivityUtils
import com.retrofit.using.kotlin.utilities.ValidationUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class SignInActivity : BaseActivity() {

    companion object {
        private val TAG = SignInActivity::class.java.simpleName
    }

    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout:TextInputLayout
    private lateinit var emailTextInputEditText: TextInputEditText
    private lateinit var passwordTextInputEditText:TextInputEditText

    private lateinit var appSignInMaterialButton: MaterialButton
    private lateinit var appSignUpLinkMaterialButton:MaterialButton

    private lateinit var emailString: String
    private lateinit var passwordString: String
    private val fcmTokenString = "NJjMmJkNDAxCnBhY2thZ2VOYW1lPWNvbS5jYXJ0by5hZHZhbmNlZC5rb3RsaW4Kb25saW5lT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_sign_in
    }

    override fun initializeView() {
        emailTextInputLayout        = findView(R.id.emailTextInputLayout)
        emailTextInputEditText      = findView(R.id.emailTextInputEditText)
        passwordTextInputLayout     = findView(R.id.passwordTextInputLayout)
        passwordTextInputEditText   = findView(R.id.passwordTextInputEditText)

        appSignInMaterialButton     = findView(R.id.appSignInMaterialButton)
        appSignUpLinkMaterialButton = findView(R.id.appSignUpLinkMaterialButton)
    }

    override fun initializeObject() {
    }

    override fun initializeToolBar() {
    }

    override fun initializeCallbackListener() {
    }

    override fun addTextChangedListener() {
        emailTextInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 1)
                {
                    emailTextInputLayout.isErrorEnabled = true
                    emailTextInputLayout.error = getString(R.string.email_message_one)
                }
                else if (s.length > 0)
                {
                    emailTextInputLayout.error = null
                    emailTextInputLayout.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val emailValidCode = ValidationUtils.isValidEmail(emailTextInputEditText.text.toString())
                if (emailValidCode > 0)
                {
                    if (emailValidCode == 1)
                    {
                        emailTextInputLayout.error = getString(R.string.email_message_one)
                    }
                    else if (emailValidCode == 2)
                    {
                        emailTextInputLayout.error = getString(R.string.email_message_two)
                    }
                }
            }
        })

        passwordTextInputLayout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 1)
                {
                    passwordTextInputLayout.isErrorEnabled = true
                    passwordTextInputLayout.error = getString(R.string.password_message_one)
                }
                else if (s.length > 0)
                {
                    passwordTextInputLayout.error = null
                    passwordTextInputLayout.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val passwordValidCode = ValidationUtils.isValidPassword(passwordTextInputEditText.text.toString())
                if (passwordValidCode > 0)
                {
                    if (passwordValidCode == 1)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_one)
                    }
                    else if (passwordValidCode == 2)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_two)
                    }
                    else if (passwordValidCode == 3)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_three)
                    }
                    else if (passwordValidCode == 4)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_four)
                    }
                    else if (passwordValidCode == 5)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_five)
                    }
                    else if (passwordValidCode == 6)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_six)
                    }
                    else if (passwordValidCode == 7)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_seven)
                    }
                    else if (passwordValidCode == 8)
                    {
                        passwordTextInputLayout.error = getString(R.string.password_message_eight)
                    }
                }
            }
        })
    }

    override fun setOnClickListener() {
        appSignInMaterialButton.setOnClickListener(this);
        appSignUpLinkMaterialButton.setOnClickListener(this);
    }

    override fun handleClickEvent(view: View?) {
        when (view?.id) {
            R.id.appSignInMaterialButton -> appSignIn()
            R.id.appSignUpLinkMaterialButton -> launchSignUpScreen()
            else -> {
                println("Invalid view id")
            }
        }
    }

    fun appSignIn() {
        val progressDialog: ProgressDialog
        progressDialog = ProgressDialog(this@SignInActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)

        emailString             = emailTextInputEditText.getText().toString();
        passwordString          = passwordTextInputEditText.getText().toString();

        if (validation(emailString, passwordString) == null)
        {
            val apiService = createService(this@SignInActivity, ApiService::class.java)

            val observable: Observable<Response<BaseResponse<Data>>> = apiService.signIn(emailString, passwordString, fcmTokenString)
            val observer: Observer<Response<BaseResponse<Data>>> = object : Observer<Response<BaseResponse<Data>>> {
                override fun onSubscribe(disposable: Disposable) {
                    progressDialog.show();
                }

                override fun onNext(response: Response<BaseResponse<Data>>) {
                    progressDialog.dismiss();
                    if (response != null) {
                        if (response.body() != null && response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), response.body()?.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    progressDialog.dismiss();
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(applicationContext, validation(emailString, passwordString), Toast.LENGTH_SHORT).show()
        }
    }

    fun launchSignUpScreen() {
        ActivityUtils.launchActivity(this@SignInActivity, SignUpActivity::class.java)
    }

    fun validation(email: String?, password: String?): String?
    {
        val emailValidCode = ValidationUtils.isValidEmail(email)
        val passwordValidCode = ValidationUtils.isValidPassword(password)

        if (emailValidCode > 0)
        {
            if (emailValidCode == 1)
            {
                return getString(R.string.email_message_one)
            }
            else if (emailValidCode == 2)
            {
                return getString(R.string.email_message_two)
            }
        }
        else if (passwordValidCode > 0)
        {
            if (passwordValidCode == 1)
            {
                return getString(R.string.password_message_one)
            }
            else if (passwordValidCode == 2)
            {
                return getString(R.string.password_message_two)
            }
            else if (passwordValidCode == 3)
            {
                return getString(R.string.password_message_three)
            }
            else if (passwordValidCode == 4)
            {
                return getString(R.string.password_message_four)
            }
            else if (passwordValidCode == 5)
            {
                return getString(R.string.password_message_five)
            }
            else if (passwordValidCode == 6)
            {
                return getString(R.string.password_message_six)
            }
            else if (passwordValidCode == 7)
            {
                return getString(R.string.password_message_seven)
            }
            else if (passwordValidCode == 8)
            {
                return getString(R.string.password_message_eight)
            }
        }
        return null
    }
}