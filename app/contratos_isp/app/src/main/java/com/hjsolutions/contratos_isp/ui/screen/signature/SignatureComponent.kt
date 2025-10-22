package com.hjsolutions.contratos_isp.ui.screen.signature

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import android.graphics.Path as AndroidPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import com.hjsolutions.contratos_isp.ui.viewModels.SignatureViewModel

data class PathPoint(
    val offset: Offset,
    val pressure: Float = 1f
)

data class SignaturePath(
    val points: List<PathPoint> = emptyList(),
    val color : Color = Color.Black,
    val strokeWidth: Float = 5f
)

@Composable
fun SignatureComponent(
    signatureViewModel: SignatureViewModel,
    onSignatureSaved: (File) -> Unit
){
    var paths by remember { mutableStateOf(listOf<SignaturePath>()) }
    var currentPath by remember { mutableStateOf(SignaturePath()) }
    var isDrawing by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val scope = rememberCoroutineScope()

    //dimensiones
    val canvasWidth = with(density) { (configuration.screenWidthDp * 0.97f).dp.toPx()}
    val canvasHeight = 300.dp

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Canvas(
            modifier = Modifier
                .size(width = (canvasWidth / density.density).dp, height = canvasHeight)
                .clip(RoundedCornerShape(8.dp))
                .border(2.dp , MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                .background(Color.White)
                .pointerInput(Unit){
                    detectDragGestures(
                        onDragStart ={ offset ->
                            isDrawing = true
                            currentPath = SignaturePath(
                                points = listOf(PathPoint(offset)),
                                color = Color.Black,
                                strokeWidth = 5f
                            )
                        },
                        onDrag = { change , _ ->
                                if(isDrawing){
                                    currentPath = currentPath.copy(
                                        points = currentPath.points + PathPoint(change.position)
                                    )
                                }
                        },
                        onDragEnd = {
                            if(currentPath.points.isNotEmpty()){
                                paths = paths + currentPath
                                currentPath = SignaturePath()
                            }
                            isDrawing = false
                        }
                    )
                }
        ) {
            drawSignaturePaths(paths + if(isDrawing) listOf(currentPath) else emptyList())
        }

        Spacer(modifier = Modifier.height(16.dp))

        //acciones buttons
        Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.SpaceEvenly) {

            OutlinedButton(
                onClick = {
                    paths = emptyList()
                    currentPath = SignaturePath()
                },
                modifier =  Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        if(paths.isNotEmpty()){
                            //val signatureFile
                            val signatureFile = createSignatureFile(
                                paths = paths,
                                width = canvasWidth.toInt(),
                                height = with(density) { canvasHeight.toPx().toInt() },
                                context = context
                            )
                            onSignatureSaved(signatureFile)
                            paths = emptyList()
                            currentPath = SignaturePath()
                        }
                    }
                },
                enabled = paths.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                if(signatureViewModel.response.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.Transparent),  // Centrado en el Box
                        color = Color.White,
                        trackColor = Color.Transparent)
                }else{
                    Text("Guardar Firma")
                }
            }
        }
    }
}


private fun DrawScope.drawSignaturePaths(paths: List<SignaturePath>){
    paths.forEach { signaturePath ->
        if (signaturePath.points.size > 1) {
            val path = Path()
            signaturePath.points.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(point.offset.x, point.offset.y)
                } else {
                    val prevPoint = signaturePath.points[index - 1]
                    val midX = (prevPoint.offset.x + point.offset.x) / 2
                    val midY = (prevPoint.offset.y + point.offset.y) / 2
                    path.quadraticBezierTo(
                        prevPoint.offset.x, prevPoint.offset.y,
                        midX, midY
                    )
                }
            }

            drawPath(
                path = path,
                color = signaturePath.color,
                style = Stroke(
                    width = signaturePath.strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}

private suspend fun createSignatureFile(
    paths: List<SignaturePath>,
    width: Int,
    height : Int,
    context : Context
):File {

    val bitmap = Bitmap.createBitmap(width  , height , Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(bitmap)


    canvas.drawColor(android.graphics.Color.WHITE)

    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE //Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    paths.forEach{ signaturePath ->
        if(signaturePath.points.size > 1){
            paint.color = signaturePath.color.toArgb()
            paint.strokeWidth = signaturePath.strokeWidth

            val androidPath = AndroidPath()
            signaturePath.points.forEachIndexed { index, point ->
                if(index == 0){
                    androidPath.moveTo(point.offset.x , point.offset.y)
                }else{
                    val prevPoint = signaturePath.points[index -1]
                    val midX = (prevPoint.offset.x + point.offset.x) / 2
                    val midY = (prevPoint.offset.y + point.offset.y) / 2
                    androidPath.quadTo(
                        prevPoint.offset.x, prevPoint.offset.y,
                        midX, midY
                    )
                }
            }
            canvas.drawPath(androidPath , paint)
        }
    }

    val file = File(context.cacheDir , "signature_${System.currentTimeMillis()}.png")
    val fos = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100 , fos)
    fos.close()

    return file
}
