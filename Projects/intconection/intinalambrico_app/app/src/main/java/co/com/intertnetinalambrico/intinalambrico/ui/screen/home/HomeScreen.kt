package co.com.intertnetinalambrico.intinalambrico.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.com.intertnetinalambrico.intinalambrico.ui.screen.AuthViewModel

@Composable
fun HomeScreen(role: String, onLogoutSuccess: () -> Unit ,authViewModel: AuthViewModel = viewModel()) {
    val ispBlue = Color(0xFF0A2647)
    val ispGreen = Color(0xFF20C997)
    val bgGray = Color(0xFFF4F7FA)
    // 1. Definir el launcher para el Logout
    val logoutLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) {
        // Al regresar del navegador de logout, volvemos a la pantalla de login
        onLogoutSuccess()
    }

    Scaffold(
        topBar = {
            HomeTopBar(userName = "Usuario ISP", role = role, onLogout = {
                authViewModel.logout(launcher = logoutLauncher) })
        },
        containerColor = bgGray
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Panel de Control",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = ispBlue
            )
            Text(
                text = if (role == "tecnico") "Gestión de Infraestructura" else "Mi Conexión Hogar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Renderizado condicional según el ROL
            if (role == "tecnico") {
                TechnicianGrid(ispGreen, ispBlue)
            } else {
                ClientGrid(ispGreen, ispBlue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(userName: String, role: String, onLogout: () -> Unit) {
    val ispBlue = Color(0xFF0A2647)
    val ispGreen = Color(0xFF20C997)

    TopAppBar(
        title = {
            Column {
                Text(userName, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text(
                    role.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = ispGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = onLogout) {
                Icon(Icons.Default.Logout, contentDescription = "Salir", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = ispBlue)
    )
}

// --- VISTA PARA TÉCNICOS ---
@Composable
fun TechnicianGrid(accentColor: Color, textColor: Color) {
    val options = listOf(
        DashboardOption("Órdenes", Icons.Default.Engineering, "Instalaciones pendientes"),
        DashboardOption("Mapa Nodos", Icons.Default.Map, "Ubicación de cajas NAP"),
        DashboardOption("Inventario", Icons.Default.Inventory, "Equipos y ONUs"),
        DashboardOption("Prueba Velocidad", Icons.Default.Speed, "Test de campo")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(options) { option ->
            MenuCard(option, accentColor, textColor)
        }
    }
}

// --- VISTA PARA CLIENTES ---
@Composable
fun ClientGrid(accentColor: Color, textColor: Color) {
    val options = listOf(
        DashboardOption("Mi Plan", Icons.Default.Wifi, "500 Mbps Fibra"),
        DashboardOption("Facturas", Icons.Default.ReceiptLong, "Pagar mes actual"),
        DashboardOption("Soporte", Icons.Default.SupportAgent, "Chat con asesor"),
        DashboardOption("Mis Equipos", Icons.Default.Router, "Configurar Wi-Fi")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(options) { option ->
            MenuCard(option, accentColor, textColor)
        }
    }
}

@Composable
fun MenuCard(option: DashboardOption, accentColor: Color, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accentColor.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(option.icon, contentDescription = null, tint = accentColor)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(option.title, fontWeight = FontWeight.Bold, color = textColor)
            Text(option.desc, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

data class DashboardOption(val title: String, val icon: ImageVector, val desc: String)
