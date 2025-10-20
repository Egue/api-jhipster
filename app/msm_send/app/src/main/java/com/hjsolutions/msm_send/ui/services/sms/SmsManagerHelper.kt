package com.hjsolutions.msm_send.ui.services.sms

import android.content.Context
import android.telephony.SmsManager
import com.hjsolutions.msm_send.ui.domain.models.PhoneNumber

class SmsManagerHelper (private val context: Context){

    fun sendSms(phoneNumber: String , message:String, campaignId:String):Boolean{
        return try {
            val smsManager = SmsManager.getDefault()

            val parts = smsManager.divideMessage(message)

            if (parts.size == 1){
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            }else{
                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null , null)
            }

            true
        }catch (e:Exception)
        {
            e.printStackTrace()
            false
        }
    }
}
