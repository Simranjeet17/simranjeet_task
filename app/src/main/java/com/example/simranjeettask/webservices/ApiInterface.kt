package com.example.simranjeettask.webservices

import androidx.collection.ArrayMap
import com.example.simranjeettask.model.category
import com.example.simranjeettask.model.login
import com.example.simranjeettask.model.register
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiInterface {

    @POST(UrlHelper.USER_SIGNUP)
    @FormUrlEncoded
    suspend fun register(
        @FieldMap UserRegister : ArrayMap<String, String>
    ):register

    @POST(UrlHelper.USER_LOGIN)
    @FormUrlEncoded
    suspend fun login(
        @FieldMap UserLogin : ArrayMap<String, String>
    ):login

    @GET(UrlHelper.CATEGORY)
    suspend fun getCategory(
    ):category
}