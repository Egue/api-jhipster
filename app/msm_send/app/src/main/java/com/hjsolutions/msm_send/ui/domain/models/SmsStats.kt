package com.hjsolutions.msm_send.ui.domain.models

data class SmsStats(
    val total: Int,
    val pending : Int,
    val sent: Int,
    val delivered: Int,
    val failed: Int
)
