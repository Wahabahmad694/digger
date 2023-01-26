package com.example.digger.interactor

import com.example.digger.models.LoginSignUpBody
import com.example.digger.retrofits.ApiService
import com.example.digger.state.networkBoundResource

class AuthInteractor constructor(private val apiService: ApiService) : BaseInteractor() {

    fun executeLogin(user: LoginSignUpBody) = networkBoundResource(
        fetch = { apiService.login(user) }
    )

    fun executeSignUp(user: LoginSignUpBody) = networkBoundResource(
        fetch = { apiService.signUp(user) }
    )


    fun executeForgetPassword(email: String) = networkBoundResource(
        fetch = { apiService.forgetPassword(email) }
    )
}