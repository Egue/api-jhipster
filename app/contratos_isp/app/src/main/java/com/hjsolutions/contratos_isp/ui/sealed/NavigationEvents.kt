package com.hjsolutions.contratos_isp.ui.sealed

sealed class NavigationEvent {
    data class NavigateToPdf(val url: String, val title: String) : NavigationEvent()
    // Puedes agregar más eventos según necesites
}
