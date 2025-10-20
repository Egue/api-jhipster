package com.hjsolutions.msm_send.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsCampaignDao {

    @Query("SELECT * FROM sms_campaigns ORDER BY lastUpdated DESC")
    fun getAllCampaigns(): Flow<List<SmsCampaign>>

    @Query("SELECT * FROM sms_campaigns WHERE id = :campaingId")
    suspend fun getCampaignById(campaingId:String):SmsCampaign?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaigns(campaigns:List<SmsCampaign>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: SmsCampaign)

    @Delete
    suspend fun deleteCampaign(campaign: SmsCampaign)

    @Query("DELETE FROM sms_campaigns")
    suspend fun deleteAllCampaigns()


}
