package com.hjsolutions.msm_send.ui.domain.models

import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import com.hjsolutions.msm_send.ui.data.local.entities.SmsStatus


data class CampaignWithStatus(
    val campaign: SmsCampaign,
    val statusList: List<SmsStatus>
)
