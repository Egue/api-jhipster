package com.hjsolutions.msm_send.ui.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hjsolutions.msm_send.ui.data.local.dao.SessionDao
import com.hjsolutions.msm_send.ui.data.local.dao.SmsCampaignDao
import com.hjsolutions.msm_send.ui.data.local.dao.SmsStatusDao
import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import com.hjsolutions.msm_send.ui.data.local.entities.SmsStatus
import com.hjsolutions.msm_send.ui.data.local.entities.UserSession

@Database(
    entities = [SmsCampaign::class, SmsStatus::class , UserSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract  class SmsDatabase:RoomDatabase() {
    abstract fun campaignDao(): SmsCampaignDao
    abstract fun statusDao() : SmsStatusDao
    abstract fun sessionDao(): SessionDao

    companion object{
        @Volatile
        private var INSTANCE: SmsDatabase? = null

        fun getDatabase(context: Context):SmsDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmsDatabase::class.java,
                    "sms_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
