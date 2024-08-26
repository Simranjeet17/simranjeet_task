package com.example.simranjeettask.webservices

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

interface RetrofitClient {
    companion object Factory {
        private var retrofitCall: RetrofitClient? = null
        fun getInstance(): RetrofitClient {
            if (retrofitCall == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(UrlHelper.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()
                retrofitCall = retrofit.create(RetrofitClient::class.java)
            }
            return retrofitCall!!
        }

        fun create(isheader: Boolean, token: String?): ApiInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(30, TimeUnit.SECONDS)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.addInterceptor(interceptor)
            if (isheader) {
                if (token != null) {
                    Log.e("token", token)
                    httpClient.addInterceptor(object : Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                            var builders = chain.request()
                            try {
                                builders = chain.request().newBuilder()
                                    .header("Authorization", "Bearer $token")
                                    .header("Accept", "application/json")
                                    .build()


                            } catch (exception: Exception) {
                                when (exception) {
                                    is SocketTimeoutException -> {
                                    }

                                    is SocketException -> {
                                    }

                                    is IOException -> {
                                    }

                                    else -> exception.stackTrace
                                }
                            }
                            return chain.proceed(builders)
                        }
                    })

                }
            }else {
                httpClient.addInterceptor(object : Interceptor {
                    @Throws(IOException::class)

                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var builders = chain.request()
                        try {
                            builders = chain.request().newBuilder()
                                .header("Accept", "application/json")
                                .build()


                        } catch (exception: Exception) {
                            when (exception) {
                                is SocketTimeoutException -> {
                                    //message.sendToTarget()
                                }
                                is SocketException -> {
                                    //message.sendToTarget()
                                }
                                is IOException -> {
                                    // message.sendToTarget()
                                }
                                else -> exception.stackTrace
                            }
                        }

                        return chain.proceed(builders)
                    }
                })
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(UrlHelper.base_url)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            return retrofit.create(ApiInterface::class.java)

        }
    }
}