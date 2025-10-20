package com.hjsolutions.isp_api.web
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import com.comunicamosmas.api.repository.IClienteDao
import com.hjsolutions.isp_api.service.MigrationDBService
import com.hjsolutions.isp_api.service.dto.ListString
import com.hjsolutions.isp_api.domain.Barrios
import com.hjsolutions.isp_api.domain.EquiposAsignados
import com.hjsolutions.isp_api.domain.Perfiles
import com.hjsolutions.isp_api.service.dto.PppSecreDTO
import org.springframework.http.HttpHeaders
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.InputStream
import java.io.InputStreamReader
import javax.print.attribute.standard.Media

@RestController
@RequestMapping("/api/kt/migrationsCB")
class MigrationDbController(private val migrationDBService:MigrationDBService){

    @PostMapping("/cliente")
    fun migration_clientes(@RequestParam file : MultipartFile):ResponseEntity<Any>{
        return try {
        migrationDBService.processCsv(file)
        ResponseEntity.ok(mapOf(
            "message" to "Archivo procesaod",

        ))
        }
        catch(e:Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf(
                "error" to "Error procesando",
                "detail" to e.message
             ))
        }
    }

	@PostMapping("/suscripciones")
	fun migration_suscripciones(
		@RequestBody() listString : ListString
	):ResponseEntity<Any>{
		val list: List<Any> = migrationDBService.migration_suscripciones(estadoSuscripcion = listString.estado , codServicio = listString.codServicio)
		return ResponseEntity.ok(list)
	}

	@GetMapping("/rbconexion")
	fun rbvalidation():ResponseEntity<Any>{
		val list : List<Any> = migrationDBService.validateByStation()

		return ResponseEntity.ok(list)
	}

	@GetMapping("/testssh")
	fun testSsh(@RequestParam("ap") ap:String):ResponseEntity<ByteArray>{
		val response:List<PppSecreDTO> = migrationDBService.appConexion(ap)

        val csvHeader = "ID,NAME,SERVICE,PASSWORD,PROFILE,REMOTEADDRESS,CALLERID,COMENTARIO"
        val csvBody = response.map {rb ->
            listOf(
                rb.id ?: "",
                rb.name ?: "",
                rb.service ?: "",
                rb.password ?: "",
                rb.profile ?: "",
                rb.remoteAddress ?: "" ,
                rb.callerId ?: "",
                rb.comentario ?: "").joinToString(",")
        }.joinToString("\n")
         val csvContent = "$csvHeader\n$csvBody"
        val csvBytes = csvContent.toByteArray(Charsets.UTF_8)


        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"equipos_asignados.csv\"")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csvBytes)
	}

    @GetMapping("/equipoAsignados", produces = ["text/csv"])
    fun equiposAsignados(@RequestParam("codAp") codAp:String): ResponseEntity<ByteArray>{
        val response : ByteArray = migrationDBService.getListByAp(codAp = codAp.toInt())

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"equipos_asignados.csv\"")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(response)
    }

    /***
    If @Oper='ECT'--(ESTADO CLIENTES TELEVISION)--Lista los estado de los clientes
Begin
	--Servicios de television: 1: Tv Analoga, 7: Tv Digital
	Set @CantNormalActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='N')
	Set @CantNormalAnterior			=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='N' And COD_SERVICIO IN (1,7))
	Set @CantParaCorteActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='P')
	Set @CantParaCorteAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='P' And COD_SERVICIO IN (1,7))
	Set @CantCortadosActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='C')
	Set @CantCortadosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='C' And COD_SERVICIO IN (1,7))
	Set @CantSuspSinDeudaActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='S')
	Set @CantSuspSinDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='S' And COD_SERVICIO IN (1,7))
	Set @CantSuspConDeudaActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='D')
	Set @CantSuspConDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='D' And COD_SERVICIO IN (1,7))
	Set @CantGestionadoActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='G')
	Set @CantGestionadAnterioro		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='G' And COD_SERVICIO IN (1,7))
	Set @CantCortesiaActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='T')
	Set @CantCortesiaAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='T' And COD_SERVICIO IN (1,7))
	Set @CantRetiradoActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='R')
	Set @CantRetiradoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='R' And COD_SERVICIO IN (1,7))
	Set @CantLevantamientoActual	=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='L')
	Set @CantLevantamientoAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='L' And COD_SERVICIO IN (1,7))
	Set @CantAnuladosActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='A')
	Set @CantAnuladosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='A' And COD_SERVICIO IN (1,7))
	Set @CantLevCableActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7)And ESTADO='V')
	Set @CantLevCableAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='V' And COD_SERVICIO IN (1,7))
	Set @CantProcInstalacionActual	=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (1,7) And ESTADO='I')
	Set @CantProcInstalacionAnterior=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='I' And COD_SERVICIO IN (1,7))
	Set @CantBloqueadoActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='B')
	Set @CantBloqueadoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='B' And COD_SERVICIo IN (1,7))

If @Oper='ECI'--(ESTADO CLIENTES)--Lista los estado de los clientes
Begin
	--Servicio 2: Internet
	Set @CantNormalActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='N')
	Set @CantNormalAnterior			=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='N' And COD_SERVICIO IN (2))
	Set @CantParaCorteActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='P')
	Set @CantParaCorteAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='P' And COD_SERVICIO IN (2))
	Set @CantCortadosActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='C')
	Set @CantCortadosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='C' And COD_SERVICIO IN (2))
	Set @CantSuspSinDeudaActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='S')
	Set @CantSuspSinDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='S' And COD_SERVICIO IN (2))
	Set @CantSuspConDeudaActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='D')
	Set @CantSuspConDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='D' And COD_SERVICIO IN (2))
	Set @CantGestionadoActual		=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='G')
	Set @CantGestionadAnterioro		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='G' And COD_SERVICIO IN (2))
	Set @CantCortesiaActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='T')
	Set @CantCortesiaAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='T' And COD_SERVICIO IN (2))
	Set @CantRetiradoActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='R')
	Set @CantRetiradoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='R' And COD_SERVICIO IN (2))
	Set @CantLevantamientoActual	=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='L')
	Set @CantLevantamientoAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='L' And COD_SERVICIO IN (2))
	Set @CantAnuladosActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='A')
	Set @CantAnuladosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='A' And COD_SERVICIO IN (2))
	Set @CantLevCableActual			=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='V')
	Set @CantLevCableAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='V' And COD_SERVICIO IN (2))
	Set @CantProcInstalacionActual	=(Select Count(*) From SUSCRIPCIONES As SU Inner Join DETALLES_SUSCRIPCION As DS On DS.COD_SUSCRIPCION=SU.CODIGO Where DS.COD_SERVICIO IN (2) And ESTADO='I')
	Set @CantProcInstalacionAnterior=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='I' And COD_SERVICIO IN (2))
	Set @CantBloqueadoActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='B')
	Set @CantBloqueadoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='B' And COD_SERVICIO IN (2))

If @Oper='ECD'--(ESTADO CLIENTES DUOCOMBO)--Lista los estado de los clientes
Begin
	--Servicios de television: 1: Tv Analoga, 7: Tv Digital, 2: Internet
	Set @CantNormalActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='N')
	Set @CantNormalAnterior			=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='N' And COD_SERVICIO=0)
	Set @CantParaCorteActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='P')
	Set @CantParaCorteAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='P' And COD_SERVICIO=0)
	Set @CantCortadosActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='C')
	Set @CantCortadosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='C' And COD_SERVICIO=0)
	Set @CantSuspSinDeudaActual		=(0)--Suspendido sin deuda
	Set @CantSuspSinDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='S' And COD_SERVICIO=0)
	Set @CantSuspConDeudaActual		=(0)--Suspendido con deuda
	Set @CantSuspConDeudaAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='D' And COD_SERVICIO=0)
	Set @CantGestionadoActual		=(0)--Cortado gestionado
	Set @CantGestionadAnterioro		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='G' And COD_SERVICIO=0)
	Set @CantCortesiaActual			=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='T')
	Set @CantCortesiaAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='T' And COD_SERVICIO=0)
	Set @CantRetiradoActual			=(0)--Retirado
	Set @CantRetiradoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='R' And COD_SERVICIO=0)
	Set @CantLevantamientoActual	=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='L')
	Set @CantLevantamientoAnterior	=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='L' And COD_SERVICIO=0)
	Set @CantAnuladosActual			=(0)--Anulado
	Set @CantAnuladosAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='A' And COD_SERVICIO=0)
	Set @CantLevCableActual			=(0)--Levantamiento de cable
	Set @CantLevCableAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='V' And COD_SERVICIO=0)
	Set @CantProcInstalacionActual	=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2)) And ESTADO='I')
	Set @CantProcInstalacionAnterior=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='I' And COD_SERVICIO=0)
	Set @CantBloqueadoActual		=(Select Count(*) From SUSCRIPCIONES As SU Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='B')
	Set @CantBloqueadoAnterior		=(Select CANTIDAD_ANTERIOR From ESTADO_CLIENTES Where ESTADO='B' And COD_SERVICIO=0)
SELECT * FROM CABLEMAG.dbo.SUSCRIPCIONES s
WHERE CODIGO In (Select EA.COD_SUSCRIPCION From CABLEMAG.dbo.EQUIPOS_ASIGNADOS as EA
Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA
Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL
Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S') and s.ESTADO in('N' , 'P' , 'C' , 'I' , 'T' , 'L')
Select * From CABLEMAG.dbo.SUSCRIPCIONES As SU Where
CODIGO In (Select EA.COD_SUSCRIPCION From CABLEMAG.dbo.EQUIPOS_ASIGNADOS as EA
Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA
Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL
Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And SU.ESTADO IN ('N' , 'P' , 'C' , 'I' , 'T' , 'L')

Select Count(*) From .CABLEMAG.dbo.SUSCRIPCIONES As SU
Where CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA
Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA
Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL
Where PF.COD_SERVICIO IN (1,7) And ACTIVO='S' And PRINCIPAL='S')
And CODIGO In (Select EA.COD_SUSCRIPCION From dbo.EQUIPOS_ASIGNADOS as EA
	Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA
	Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL
	Where PF.COD_SERVICIO IN (2) And ACTIVO='S' And PRINCIPAL='S') And ESTADO='N'

migracion de internet:
Select * From CABLEMAG.dbo.SUSCRIPCIONES SU
LEFT JOIN CABLEMAG.dbo.EQUIPOS_ASIGNADOS eac ON eac.COD_SUSCRIPCION = SU.CODIGO
LEFT JOIN CABLESOFT.dbo.PAQUETES_VENTA pvc ON pvc.CODIGO  = eac.COD_PAQUETE_VENTA
LEFT JOIN CABLESOFT.dbo.CONTENIDO_PAQUETE_VENTA cpv ON cpv.COD_PAQUETE = pvc.CODIGO
LEFT JOIN CABLESOFT.dbo.PUNTOS_ACCESOS pa  ON pa.CODIGO = eac.COD_AP
WHERE SU.CODIGO IN (select EA.COD_SUSCRIPCION FROM CABLEMAG.dbo.EQUIPOS_ASIGNADOS EA
Left Join CABLESOFT.dbo.PAQUETES_VENTA as PV on PV.CODIGO = EA.COD_PAQUETE_VENTA
Left Join CABLESOFT.dbo.PERFILES as PF on PF.CODIGO = PV.COD_PERFIL
Where PF.COD_SERVICIO IN (2) And EA.ACTIVO='S' And EA.PRINCIPAL='S') And SU.ESTADO IN ('N' , 'P' , 'C' , 'I' , 'T' , 'L')

***/
}
