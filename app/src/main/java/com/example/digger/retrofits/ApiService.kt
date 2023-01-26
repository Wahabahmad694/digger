package com.example.digger.retrofits

import com.example.digger.models.LoginSignUpBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginSignUpBody: LoginSignUpBody): ResponseBody


    @POST("register")
    suspend fun signUp(@Body loginBody: LoginSignUpBody): ResponseBody

    @POST("register")
    suspend fun forgetPassword(@Body email: String): ResponseBody
}