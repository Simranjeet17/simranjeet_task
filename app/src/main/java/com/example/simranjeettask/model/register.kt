package com.example.simranjeettask.model

data class register(
    val `data`: Data?=null,
    val message: String?=null,
    val statusCode: Int?=null,
    val success: Boolean?=null
)

data class Data(
    val created_at: String,
    val device_token: String,
    val device_type: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone_number: String,
    val updated_at: String
)