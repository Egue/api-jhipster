package com.hjsolutions.msm_send.ui.screen.home

import android.content.res.Resources.Theme
import android.provider.Telephony.Sms
import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjsolutions.msm_send.ui.theme.Pink40
import com.hjsolutions.msm_send.ui.theme.Pink80
import com.hjsolutions.msm_send.ui.theme.Purple40
import com.hjsolutions.msm_send.ui.theme.PurpleGrey80
import com.hjsolutions.msm_send.ui.viewmodels.SmsViewModel

@Composable
fun HomeScreen(smsViewModel: SmsViewModel){

      Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(16.dp))
                  .background(PurpleGrey80)
                  .padding(16.dp)
              ,
              horizontalAlignment = Alignment.CenterHorizontally) {


              Box(
                  modifier = Modifier
                      .size(80.dp)
                      .clip(CircleShape)
                      .background(Purple40),
                  contentAlignment = Alignment.Center
                  ) {
                  Icon(imageVector = Icons.Default.Download , tint = Color.White , contentDescription = null )
              }
              Spacer(Modifier.height(16.dp))
              Text(text = "Descargar Lista" , fontWeight = FontWeight.Bold , fontSize = 20.sp)
              Spacer(Modifier.height(8.dp))
              Text("Obtén los números y mensajes desde la API")

              ButtonDownLoadFile(onClick = {
                  smsViewModel.downloadCampaigns()
              })


          }

          Spacer(modifier = Modifier.height(16.dp))
          SmsCampaignScreen(smsViewModel = smsViewModel)


          /*Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = "Mensajes" , fontWeight = FontWeight.Bold)
              Text(text = "3 pendientes" , fontWeight = FontWeight.Bold)
          }*/

      }
}
