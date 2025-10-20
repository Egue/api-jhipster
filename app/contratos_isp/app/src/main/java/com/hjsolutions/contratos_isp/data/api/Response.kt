package com.hjsolutions.contratos_isp.data.api

data class Response(
    val isLoading: Boolean = false,
    val data:Any? = null,
    val errorMessage:String? = null
)
