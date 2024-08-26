package com.example.simranjeettask.model

data class login(
    val `data`: Dataa?=null,
    val message: String?=null,
    val statusCode: Int?=null,
    val success: Boolean?=null
)

data class Dataa(
    val created_at: String,
    val device_token: String,
    val device_type: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val phone_number: String,
    val token: String,
    val updated_at: String
)