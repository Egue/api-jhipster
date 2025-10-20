package com.hjsolutions.msm_send.ui.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjsolutions.msm_send.ui.data.local.entities.SmsDeliveryStatus
import com.hjsolutions.msm_send.ui.domain.models.PhoneNumber

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromPhoneNumberList(value: List<PhoneNumber>):String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPhoneNumberList(value:String) : List<PhoneNumber>{
        val listType = object :  TypeToken<List<PhoneNumber>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromSmsStatus(status: SmsDeliveryStatus):String = status.name

    @TypeConverter
    fun toSmsStatus(status: String): SmsDeliveryStatus = SmsDeliveryStatus.valueOf(status)
}
