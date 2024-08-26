package com.example.simranjeettask.repository

import androidx.collection.ArrayMap
import com.example.simranjeettask.model.category
import com.example.simranjeettask.model.login
import com.example.simranjeettask.model.register
import com.example.simranjeettask.webservices.ApiInterface
import com.example.simranjeettask.webservices.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class LoginFlowRespository(private val apiService: ApiInterface) {

    suspend fun login(hashMap: ArrayMap<String, String>): Flow<ApiState<login>> {
        return flow {
            val checklist = apiService.login(hashMap)
            emit(ApiState.success(checklist))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun signUp(hashMap: ArrayMap<String, String>): Flow<ApiState<register>> {
        return flow {
            val checklist = apiService.register(hashMap)
            emit(ApiState.success(checklist))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCategory(): Flow<ApiState<category>> {
        return flow {
            try {
                val data = apiService.getCategory()
                emit(ApiState.success(data))
            } catch (e: Exception) {
                emit(ApiState.error("Failed to fetch category: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }




}