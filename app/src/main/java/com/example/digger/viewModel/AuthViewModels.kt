package com.example.digger.viewModel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.digger.interactor.AuthInteractor
import com.example.digger.models.LoginSignUpBody
import com.example.digger.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModels @Inject constructor(
    private val authInteractor: AuthInteractor
) : ViewModel() {

    val login get() = userLogin()
    val signUp get() = userSignUp()
    var userEmail = ""
    var password = ""
    var first_name = ""
    var last_name = ""
    var code = ""

    private fun userLogin(): LiveData<Resource<out ResponseBody>> {
        val loginBody = LoginSignUpBody()
        loginBody.user.email = userEmail
        loginBody.user.password = password
        return authInteractor.executeLogin(loginBody).asLiveData()
    }

    private fun userSignUp(): LiveData<Resource<out ResponseBody>> {
        var loginBody = LoginSignUpBody()
        loginBody.user.email = userEmail
        loginBody.user.password = password
        loginBody.user.first_name = first_name
        loginBody.user.last_name = last_name
        loginBody.user.code = code
        return authInteractor.executeSignUp(loginBody).asLiveData()
    }

    fun forgetPassword(email: String): LiveData<Resource<out ResponseBody>> {
        return authInteractor.executeForgetPassword(email).asLiveData()
    }


    fun validateSignupCredentials(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        code: String
    ): Boolean {
        if (TextUtils.isEmpty(email)) {
            return false
        } else if (TextUtils.isEmpty(password)) {
            return false
        } else if (password.length >= 5) {
            return false
        } else if (TextUtils.isEmpty(firstName)) {
            return false
        } else if (TextUtils.isEmpty(lastName)) {
            return false
        } else if (TextUtils.isEmpty(code)) {
            return false
        }
        return true
    }

    fun validateLoginCredentials(
        email: String,
        password: String,
    ): Boolean {
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (TextUtils.isEmpty(password)) {
            return false
        }
        if (password.length >= 5) {
            return false
        }
        return true
    }

}