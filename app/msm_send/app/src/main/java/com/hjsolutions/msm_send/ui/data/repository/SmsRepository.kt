package com.hjsolutions.msm_send.ui.data.repository

import android.telephony.SmsMessage
import android.util.Log
import com.hjsolutions.msm_send.ui.data.local.dao.SmsCampaignDao
import com.hjsolutions.msm_send.ui.data.local.dao.SmsStatusDao
import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import com.hjsolutions.msm_send.ui.data.local.entities.SmsDeliveryStatus
import com.hjsolutions.msm_send.ui.data.local.entities.SmsStatus
import com.hjsolutions.msm_send.ui.data.remote.api.SmsApiService
import com.hjsolutions.msm_send.ui.domain.models.CampaignWithStatus
import com.hjsolutions.msm_send.ui.domain.models.PhoneNumber
import com.hjsolutions.msm_send.ui.domain.models.SmsStats
import com.hjsolutions.msm_send.ui.services.ApiClient
import kotlinx.coroutines.flow.Flow

class SmsRepository(
    private val campaignDao: SmsCampaignDao,
    private val statusDao: SmsStatusDao
) {
    fun getAllCampaigns(): Flow<List<SmsCampaign>> = campaignDao.getAllCampaigns()

    var apiClient:ApiClient = ApiClient
    suspend fun downloadAndSaveCampaigns(token: String , userId:String):Result<List<SmsCampaign>>{
        return try{

            Log.d("Repository", "=== INICIO DOWNLOAD ===")
            Log.d("Repository", "Token: Bearer ${token.take(20)}...")
            Log.d("Repository", "UserId: $userId")
            Log.d("Repository", "Status: 1")

            val campaigns = apiClient.sms.getCampaigns(token = "Bearer $token" , userId = userId , status = "1")
            Log.d("Repository", "=== RESPUESTA API ===")
            Log.d("Repository", "Campañas recibidas: ${campaigns.size}")
            Log.d("Repository", "Campaigns raw: $campaigns")

            if (campaigns.isEmpty()) {
                Log.w("Repository", "⚠️ API devolvió lista vacía")
            }
            val campaignWithDownloadFlag = campaigns.map { it.copy(isDownloaded = true) }
            campaignDao.insertCampaigns(campaignWithDownloadFlag)
            //inicializar stado de SMS para numeros nuevos
            campaigns.forEach{ campaign ->
                campaign.numbers.forEach{phoneNumber ->
                    val smsStatus = SmsStatus(
                        campaignId = campaign.id,
                        phoneNumber = phoneNumber.number,
                        status = SmsDeliveryStatus.PENDING
                    )
                    statusDao.insetStatus(smsStatus)
                }
            }
            Result.success(campaignWithDownloadFlag)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun getCampaignWhitStatus(campaignId:String):Flow<CampaignWithStatus?>{
        return kotlinx.coroutines.flow.flow{
            val campaign = campaignDao.getCampaignById(campaignId)
            if(campaign != null){
                statusDao.getStatusByCampaign(campaignId).collect{statusList ->
                    emit(CampaignWithStatus(campaign, statusList))
                }
            }else{
                emit(null)
            }
        }
    }

    suspend fun updateSmsStatus(campaignId: String , phoneNumber: String,
                                status: SmsDeliveryStatus, errorMessage: String? = null){
        val id = "${campaignId}_${phoneNumber}"
        statusDao.updateStatus(id , status , System.currentTimeMillis() , errorMessage)
    }

    suspend fun getCampaignStats(campaignId: String):SmsStats{
        val total = statusDao.getCountByStatus(campaignId, SmsDeliveryStatus.PENDING) +
            statusDao.getCountByStatus(campaignId , SmsDeliveryStatus.SENT) +
            statusDao.getCountByStatus(campaignId , SmsDeliveryStatus.DELIVERED) +
            statusDao.getCountByStatus(campaignId , SmsDeliveryStatus.FAILED)

        return SmsStats(
            total = total,
            pending = statusDao.getCountByStatus(campaignId, SmsDeliveryStatus.PENDING),
            sent = statusDao.getCountByStatus(campaignId, SmsDeliveryStatus.SENT),
            delivered = statusDao.getCountByStatus(campaignId, SmsDeliveryStatus.DELIVERED),
            failed = statusDao.getCountByStatus(campaignId, SmsDeliveryStatus.FAILED)
        )

    }
}
