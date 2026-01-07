package com.hjsolutions.contratos_isp.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.hjsolutions.contratos_isp.ui.components.PdfViewScreen
import com.hjsolutions.contratos_isp.ui.screen.gallery.GalleryScreen
import com.hjsolutions.contratos_isp.ui.screen.login.LoginScreen
import com.hjsolutions.contratos_isp.ui.screen.main.MainScreen
import com.hjsolutions.contratos_isp.ui.screen.signature.SignatureScreen
import com.hjsolutions.contratos_isp.ui.viewModels.ContratosViewModel
import com.hjsolutions.contratos_isp.ui.viewModels.LoginViewModel
import com.hjsolutions.contratos_isp.ui.viewModels.SignatureViewModel
import com.hjsolutions.contratos_isp.ui.viewModels.UploadFileViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
    object PDF : Screen("pdf_viewer/{pdfUrl}")
    object Signature: Screen("signature/{documentId}")
    object Gallery: Screen("gallery/{documentId}")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(),
    contratosViewModel : ContratosViewModel = viewModel(),
    singatureViewModel: SignatureViewModel = viewModel(),
    uploadFileViewModel: UploadFileViewModel = viewModel()
) {
    val uiState = loginViewModel.uiState

    NavHost(navController = navController,
        startDestination = if (uiState.isLoggedIn) Screen.Main.route else Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(loginViewModel = loginViewModel)
        }

        composable(Screen.Main.route){
            MainScreen(
                navController = navController,
                loginViewModel = loginViewModel ,
                contratosViewModel = contratosViewModel)
        }
        composable(
            route = Screen.PDF.route ,
            arguments = listOf(navArgument("pdfUrl"){ type= NavType.StringType})
            ) { url ->
            val pdfUrl = url.arguments?.getString("pdfUrl") ?: ""
            val decodedUrl = Uri.decode(pdfUrl)
            PdfViewScreen(
                url = decodedUrl,
                title = "Contrato Isp",
                atras = {navController.popBackStack()}
            )

        }

        composable(
            route = Screen.Signature.route,
            arguments = listOf(navArgument("documentId"){type= NavType.StringType})
        ) { documentId ->
            val document = documentId.arguments?.getString("documentId") ?: ""
            val decodedDocument = Uri.decode(document)
            SignatureScreen(
                signatureViewModel = singatureViewModel,
                documentId = decodedDocument,
                atras = {navController.popBackStack()},
                contratosViewModel = contratosViewModel
            )
        }

        /**Gallery**/
        composable(
            route = Screen.Gallery.route,
            arguments = listOf(navArgument("documentId"){type = NavType.StringType})
        ) { documentId ->
            val document = documentId.arguments?.getString("documentId") ?: ""
            val decodeDocument = Uri.decode(document)
            GalleryScreen(
                documentId = decodeDocument,
                atras = {navController.popBackStack()},
                contratosViewModel = contratosViewModel,
                uploadFileViewModel = uploadFileViewModel
            )
        }
    }

}
