package com.hjsolutions.msm_send.ui.data

data class LoginRequest(
    val username:String,
    val password:String
)

data class LoginResponse(
    val id_token:String,
    val usario:Usuario
)

data class Usuario(
    val id:Number,
    val login:String,
    val firtsName:String,
    val lastName: String,
    val email: String,
    val activated:Boolean,
    val rol:String
)
