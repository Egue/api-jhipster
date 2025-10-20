package com.hjsolutions.msm_send.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.hjsolutions.msm_send.ui.data.local.entities.SmsCampaign
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CampaignCard(campaign : SmsCampaign,
                 onSendSms:(String)-> Unit ,
                 onViewStats: ()->Unit){

    var message by remember { mutableStateOf("") }
    var showSendDialog by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp))
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = campaign.name , style = MaterialTheme.typography.headlineSmall)

            Text(text = campaign.description , style = MaterialTheme.typography.bodyMedium)

            Text(text = "${campaign.numbers.size} números" , style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End)
            {
                TextButton(onClick = {}) {
                    Text("Ver Estado")
                }

                Button(onClick = {showSendDialog = true}) {
                    Text("Enviar sms")
                }
            }

        }

    }
}
