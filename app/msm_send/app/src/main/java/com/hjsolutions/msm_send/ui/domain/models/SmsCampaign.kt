package com.hjsolutions.msm_send.ui.domain.models


data class SmsCampaign(
    val id:String,
    val userId:Int,
    val idServicio:Int,
    val name:String,
    val description:String,
    val numbers: List<PhoneNumber>,
    val status: Int,
    val cretedAt:String,
    val isDownloaded:Boolean = true,
    val lastUpdate: Long = System.currentTimeMillis()
)
