package com.example.simranjeettask.viewmodel

import android.util.Log
import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simranjeettask.model.category
import com.example.simranjeettask.model.login
import com.example.simranjeettask.model.register
import com.example.simranjeettask.repository.LoginFlowRespository
import com.example.simranjeettask.webservices.ApiState
import com.example.simranjeettask.webservices.RetrofitClient
import com.example.simranjeettask.webservices.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repository= LoginFlowRespository(RetrofitClient.create(false,""))
    val getLogin = MutableStateFlow(ApiState(Status.NONE, login(),""))
    val getSignUp = MutableStateFlow(ApiState(Status.NONE,register(),""))
    val category = MutableStateFlow(ApiState(Status.NONE, category(),""))


    fun getlogin(map: ArrayMap<String, String>){
        getLogin.value = ApiState.loading()
        viewModelScope.launch {
            repository.login(map)
                .catch {
                    getLogin.value = ApiState.error(it.message.toString())
                }
                .collect{
                    getLogin.value = ApiState.success(it.data)
                }

        }
    }

    fun SignUp(map: ArrayMap<String,String>){
        getSignUp.value = ApiState.loading()
        viewModelScope.launch {
            repository.signUp(map)
                .catch {
                    getSignUp.value = ApiState.error(it.message.toString())
                }
                .collect{
                    getSignUp.value = ApiState.success(it.data)
                }

        }
    }
    fun getCategory() {
        // Set loading state
        category.value = ApiState.loading()

        viewModelScope.launch {
            repository.getCategory()
                .catch { exception ->
                    // Handle any exceptions and set the error state
                    Log.d("Profile", exception.toString())
                    category.value = ApiState.error(exception.message.toString())
                }
                .collect { apiState ->
                    // Check if the data is null before emitting success
                    val data = apiState.data
                    if (data != null) {
                        // If data is not null, emit success state
                        category.value = ApiState.success(data)
                    } else {
                        // Handle null data as an error case
                        Log.d("Profile", "Data is null")
                        category.value = ApiState.error("Received null data from API")
                    }
                }
        }
    }


}