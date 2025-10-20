package com.hjsolutions.msm_send.ui.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_status")
data class SmsStatus(
    @PrimaryKey
    val id: String,
    val campaignId: String,
    val phoneNumber: String,
    val status: SmsDeliveryStatus,
    val sentAt: Long? = null,
    val errorMessage: String? = null
) {
    // ✅ Constructor secundario para crear fácilmente
    constructor(
        campaignId: String,
        phoneNumber: String,
        status: SmsDeliveryStatus,
        sentAt: Long? = null,
        errorMessage: String? = null
    ) : this(
        id = "${campaignId}_${phoneNumber}",
        campaignId = campaignId,
        phoneNumber = phoneNumber,
        status = status,
        sentAt = sentAt,
        errorMessage = errorMessage
    )
}

enum class SmsDeliveryStatus {
    PENDING,    // ⏳ Esperando envío
    SENDING,    // 📤 Enviando
    SENT,       // ✅ Enviado exitosamente
    DELIVERED,  // 📩 Entregado al destinatario
    FAILED,     // ❌ Falló el envío
    CANCELLED   // 🚫 Cancelado por el usuario
}
