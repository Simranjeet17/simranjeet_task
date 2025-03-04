package com.example.simranjeettask.webservices

data class ApiState<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        // In case of Success,set status as
        // Success and data as the response
        fun <T> success(data: T?): ApiState<T> {
            return ApiState(Status.SUCCESS, data, null)
        }

        // In case of failure ,set state to Error ,
        // add the error message,set data to null
        fun <T> error(msg: String): ApiState<T> {
            return ApiState(Status.ERROR, null, msg)
        }

        // When the call is loading set the state
        // as Loading and rest as null
        fun <T> loading(): ApiState<T> {
            return ApiState(Status.LOADING, null, null)
        }

        fun <T> none(): ApiState<T> {
            return ApiState(Status.NONE, null, null)
        }
    }
}

// An enum to store the
// current state of api call
enum class Status {
    NONE,
    SUCCESS,
    ERROR,
    LOADING
}