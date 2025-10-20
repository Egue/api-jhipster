package com.hjsolutions.msm_send.ui.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.hjsolutions.msm_send.ui.data.local.database.Converters
import com.hjsolutions.msm_send.ui.domain.models.PhoneNumber


@Entity(tableName = "sms_campaigns")
@TypeConverters(Converters::class)
data class SmsCampaign(
    @PrimaryKey
    val id: String,
    val userId: Int,
    val idServicio: Int,
    val name: String,
    val description: String,
    val numbers: List<PhoneNumber>,
    val status: Int,
    val cretedAt: String, // Mantengo el typo de la API
    val isDownloaded: Boolean = true,
    val lastUpdated: Long = System.currentTimeMillis()
)
