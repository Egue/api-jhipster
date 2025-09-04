package com.hjsolutions.msm_send.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hjsolutions.msm_send.ui.data.local.dao.SessionDao
import com.hjsolutions.msm_send.ui.data.local.database.SmsDatabase
import com.hjsolutions.msm_send.ui.data.repository.SmsRepository
import kotlinx.coroutines.launch

class SmsViewModel( application: Application): AndroidViewModel(application) {

    private val smsRepository : SmsRepository
    private val sessionDao : SessionDao

    init {
        val database = SmsDatabase.getDatabase(application)
        smsRepository = SmsRepository(campaignDao =  database.campaignDao() , statusDao = database.statusDao())
        sessionDao = database.sessionDao()
    }

    val campaigns = smsRepository.getAllCampaigns()

    fun downloadCampaigns(){
        viewModelScope.launch {
            Log.e("DownLoad_excel" , "inicio del downLoad")
            sessionDao.getCurrentSession()?.let {session ->

                try {
                    val result = smsRepository.downloadAndSaveCampaigns(session.token , session.userId)

                    result.onSuccess { campaigns ->
                        Log.d("DownLoad_excel", "Download exitoso. Campañas descargadas: ${campaigns.size}")
                    }.onFailure { error->
                        Log.e("DownLoad_excel", "Error en download: ${error.message}")
                    }
                }catch (e:Exception){
                    Log.e("DownLoad_excel", "Excepción en downloadCampaigns: ${e.message}")
                }

            } ?: run {
                Log.e("DownLoad_excel", "No hay sesión activa")
            }
        }
    }
}
