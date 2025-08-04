package com.hjsolutions.isp_api.service.dto
import java.io.Serializable

data class ListString(
    val estado : List<String> = emptyList(),
    val codServicio : List<Int> = emptyList()
) :Serializable
{}

