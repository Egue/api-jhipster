package com.hjsolutions.contratos_isp.ui.screen.main

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.hjsolutions.contratos_isp.data.models.UiStateContrato
import androidx.compose.runtime.getValue
import com.hjsolutions.contratos_isp.ui.screen.gallery.GalleryScreen

@Composable
fun ListContratos(
    contratosViewModel: ContratosViewModel,
    implementacion: String,
    onViewPdf:(path:String) -> Unit ,
    onSignature:(documentId:String) -> Unit,
    onDoc:(documentId:String) -> Unit
    ) {

    val uiState by contratosViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Lista de contratos", style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(targetState = uiState) { state ->
                Log.d("ListContratos", "Renderizando estado: ${state::class.simpleName}")
                when (state) {

                    is UiStateContrato.Loading -> {
                        Log.d("ListContratos", "entro aca loading")
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                        {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(Modifier.height(16.dp))
                                Text("Cargando contratos")
                            }
                        }
                    }

                    is UiStateContrato.Success -> {
                        //Log.d("ListContratos", "${state.contratos}")
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.contratos) { contrato ->
                                CardContrato(
                                    contrato = contrato ,
                                    onViewPdf = {
                                        onViewPdf(contrato.path_contrato) } ,
                                    onFirma = {
                                        onSignature(contrato.id)
                                    },
                                    onDoc = {
                                        onDoc(contrato.id)
                                    }

                                )


                            }
                        }


                    }

                    is UiStateContrato.Empty -> {
                        Log.d("ListContratos", "entro aca limpio")
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SearchOff, // o el ícono que prefieras
                                contentDescription = "Sin contratos",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = "No se encontraron contratos",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = "No hay contratos disponibles para esta implementación",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    contratosViewModel.refreshContrato(implementacion = implementacion)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Actualizar")
                            }
                        }
                    }

                    is UiStateContrato.Error -> {
                        Log.d("ListContratos", "entro aca con error")
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )

                            Spacer(Modifier.height(16.dp))

                            Button(
                                onClick = { contratosViewModel.refreshContrato(implementacion = implementacion) }
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }

                    else -> {
                        Log.d("ListContratos", "no se cumploo nada")
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { contratosViewModel.refreshContrato(implementacion = implementacion) },
            contentColor = Color.White,
            containerColor = Color(0xFF86030E),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = "User Account",
                tint = Color.White,
            )
        }
    }
}
