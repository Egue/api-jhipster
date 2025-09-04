package com.hjsolutions.msm_send.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import com.hjsolutions.msm_send.ui.data.local.entities.SmsDeliveryStatus
import com.hjsolutions.msm_send.ui.data.local.entities.SmsStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsStatusDao {
    @Query("SELECT * FROM sms_status WHERE campaignId = :campaignId")
    fun getStatusByCampaign(campaignId:String):Flow<List<SmsStatus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetStatus(status: SmsStatus)

    @Query("UPDATE sms_status SET status = :status, sentAt = :sentAt, errorMessage = :errorMessage WHERE id = :id")
    suspend fun updateStatus(id: String, status: SmsDeliveryStatus, sentAt: Long?, errorMessage: String?)

    @Query("SELECT COUNT(*) FROM sms_status WHERE campaignId = :campaignId AND status = :status")
    suspend fun getCountByStatus(campaignId: String, status: SmsDeliveryStatus): Int
}
