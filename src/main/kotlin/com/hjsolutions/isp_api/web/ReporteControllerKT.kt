package com.hjsolutions.isp_api.web

import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql
import com.hjsolutions.isp_api.service.OrdenArticuloService
import okio.IOException
import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt/reportes")
class ReporteControllerKT(private val ordenArticuloService : OrdenArticuloService) {

    @PostMapping("/consumo-materiales")
    fun reporteConsumoMateriales(@RequestParam("init") inicial:String , @RequestParam("last") last:String): ResponseEntity<Resource>{

        val response = mutableMapOf<String, Any>()

        return try {
            val result = ordenArticuloService.reporteConsumo(inicial , last)
                ?: return ResponseEntity.notFound().build()

            val workbook: Workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("ReporteIsumos")
            val headerRow = sheet.createRow(0)

            val headers = listOf(
                "idUsuario" , "empleado" , "articulo" , "nota" , "nota_final" , "fechaRegistro" ,
                "fechaAsiste" , "cliente" , "estacion" , "idContrato" , "idEstacion" , "cantidadEmpresa" , "cantidadUsuario")
            headers.forEachIndexed { index, title ->
                headerRow.createCell(index).setCellValue(title)
            }

            var rowNum = 1
            for(registro in result){
                val row = sheet.createRow(rowNum++)

                row.createCell(0).setCellValue(registro.idUsuario.toString())
                row.createCell(1).setCellValue(registro.empleado)
                row.createCell(2).setCellValue(registro.articulo)
                row.createCell(3).setCellValue(registro.nota)
                row.createCell(4).setCellValue(registro.nota_final)
                row.createCell(5).setCellValue(registro.fechaRegistro.toString())
                row.createCell(6).setCellValue(registro.fechaAsiste.toString())
                row.createCell(7).setCellValue(registro.cliente)
                row.createCell(8).setCellValue(registro.estacion)
                row.createCell(9).setCellValue(registro.idContrato.toString())
                row.createCell(10).setCellValue(registro.idEstacion.toString())
                row.createCell(11).setCellValue(registro.cantidadEmpresa.toString())
                row.createCell(12).setCellValue(registro.cantidadUsuario.toString())

            }
            val byteArrayOutputStream = ByteArrayOutputStream()
            workbook.write(byteArrayOutputStream)
            workbook.close()

            val resource = ByteArrayResource(byteArrayOutputStream.toByteArray())

            ResponseEntity.ok()
                .header("Content-Disposition" , "attachment; filename=registros.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource)
        } catch (e: IOException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ByteArrayResource(e.message!!.toByteArray()))
        } catch (e: ExceptionNullSql) {
            response["error"] = "${e.message}-${e.details}"
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ByteArrayResource(e.message!!.toByteArray()))
        } catch (e: Exception) {
            response["error"] = e.message ?: ""
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ByteArrayResource((e.message ?: "").toByteArray()))
        }

    }
}
