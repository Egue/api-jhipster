package com.hjsolutions.msm_send.ui.services.sms

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hjsolutions.msm_send.ui.data.local.database.SmsDatabase
import com.hjsolutions.msm_send.ui.data.local.entities.SmsDeliveryStatus
import com.hjsolutions.msm_send.ui.data.repository.SmsRepository

class SmsWorker(context:Context , params : WorkerParameters):CoroutineWorker(context , params) {

    override suspend fun doWork(): Result{
        val campaignId = inputData.getString("campaignId") ?: return Result.failure()
        val message = inputData.getString("message") ?: return Result.failure()

        val database = SmsDatabase.getDatabase(applicationContext)
        val repository = SmsRepository(campaignDao =  database.campaignDao() , statusDao =  database.statusDao() )
        val smsManager = SmsManagerHelper(applicationContext)
        val campaign = database.campaignDao().getCampaignById(campaignId)

        campaign?.let {
            it.numbers.forEach{phoneNumber ->
                val success = smsManager.sendSms(phoneNumber.number , message, campaignId)
                val status = if(success) SmsDeliveryStatus.SENT else SmsDeliveryStatus.FAILED
                val errorMsg = if (!success) "Error al enviar Sms" else null

                repository.updateSmsStatus(campaignId, phoneNumber.number, status , errorMsg)

                kotlinx.coroutines.delay(1000)
            }
        }

        return Result.success()
    }

}
