package com.hjsolutions.msm_send.ui.screen.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.hjsolutions.msm_send.ui.viewmodels.SmsViewModel
import androidx.compose.runtime.getValue
@Composable
fun SmsCampaignScreen(smsViewModel: SmsViewModel){
    val campaigns by smsViewModel.campaigns.collectAsState(initial = emptyList())

    LazyColumn {


        items(campaigns) { campaign ->
            CampaignCard(
                campaign = campaign,
                onSendSms = {message ->
                    print("")
                },
                onViewStats = {
                    
                }
            )
        }
    }
}
