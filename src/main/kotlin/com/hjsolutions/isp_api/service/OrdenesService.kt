package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.domain.Estacion
import com.comunicamosmas.api.domain.Orden
import com.comunicamosmas.api.domain.Servicio
import com.comunicamosmas.api.repository.IContratoDao
import com.comunicamosmas.api.repository.IDeudaDao
import com.comunicamosmas.api.repository.IEstacionDao
import com.comunicamosmas.api.repository.IOrdenDao
import com.comunicamosmas.api.repository.IServicioDao
import com.hjsolutions.isp_api.domain.Prorroga
import com.hjsolutions.isp_api.repository.ProrrogaRepository
import com.hjsolutions.isp_api.service.dto.DeudasContratoMesCoDTO
import com.hjsolutions.isp_api.service.dto.OrdenesDTO
import com.hjsolutions.isp_api.service.dto.createDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Optional

@Service
class OrdenesService(
    private val ordenesRepository: IOrdenDao ,
    private val contratosRepository: IContratoDao,
    private val estacionRepository: IEstacionDao ,
    private val servicioRepository: IServicioDao ,
    private val deudaRepository: IDeudaDao,
    private val prorrogaRepository: ProrrogaRepository) {

    private val logger = LoggerFactory.getLogger(OrdenesService::class.java)

    companion object {
        private const val TIPO_ORDEN_CORTE = 2
        private const val ESTADO_INICIAL = 0
        private const val ORDEN_ABIERTA = 1
        private const val ORDEN_NO_ANULADA = 0
        private const val ESTADO_PRORROGA_ACTIVA = "A"
        private const val GRUPO_A = "A"
        private val TIPOS_ORDEN_RECONEXION = listOf(2, 18)
        private val FORMATO_FECHA_CORTA = DateTimeFormatter.ofPattern("yyyyMMdd")
        private val FORMATO_FECHA_COMPLETA = DateTimeFormatter.ofPattern("yyyy-MM")
        private val FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        private const val MENSAJE_ORDEN_GENERADA = "Orden generada por módulo de cortes masivos"
        private var consecutivoA:Long = 0L
        private var consecutivoB:Long = 0L
    }


    fun cortesMasivamente(idServicio:Long):  List<Array<Any>> {
        //buscar listado de contratos con deudas superiores a
        val listContrato = contratosRepository.listContratoByCorteMasivamente(idServicio)
        //buscar convertir a idContrato
        val listContratoLong:List<Long> = listContrato.map{
            (it[0] as Number).toLong()
        }
        //lista de contrato con reconexiones u orden de cortes
        val listContratoWhitOrdenExist:List<Orden> = ordenesRepository.findOrdenCorteAndReconexionExist(listContratoLong)
        //quitar los contratos existentes de la listContratos
        //obtener solo los idContratos de las ordenes
        val contratosConOrdenExiste: Set<Long> = listContratoWhitOrdenExist.map { it.idContrato }.toSet()
        //
        val contratoWithCorte:List<Array<Any>> = listContrato.filter { it[0] !in contratosConOrdenExiste }
        //crear orden de corte

        return contratoWithCorte
    }

    fun ordenes(servicio:Long , tipo:Long):List<OrdenesDTO>{

        //recuperar el lot
        val listOrdenes:List<Array<Any>> = ordenesRepository.findOrdenes(servicio , tipo)
        val ordenes :List<OrdenesDTO> = listOrdenes.map {
            OrdenesDTO(
                id = (it[0] as Number).toLong(),
                causa = it[1] as String,
                cliente = it[2] as String,
                direccion = it[3] as String,
                registroFecha = (it[4] as Number).toString(),
                asignaFecha = (it[5] as Number).toString(),
                asisteFecha = (it[6] as Number).toString(),
                usuarioEjecuta = (it[7] as Number).toLong(),
                nota = it[8] as String,
                contrato = (it[9] as Number).toLong()
                )
        }

        return ordenes

    }

    fun anularOrde(id:Long , comment :String){
        var orden: Optional<Orden>  = ordenesRepository.findById(id);
        orden.map { it.estado = 3 }
        ordenesRepository.save(orden.get())
    }

    fun createOrden(createDTO: createDTO){
        ///buscar orden si existe una abierta
        var existByAbierta: Int = ordenesRepository.existByAbierta(createDTO.idContrato , createDTO.typeOrden)
        if (existByAbierta == 1)
        {
            throw Exception("Contrato con orden Activa")
        }
        var orden : Orden = Orden();
        var numeroA:Any;
        var numeroB:Any;
        if(createDTO.typeTransfer == 2L){
            var contrato: Contrato = contratosRepository.findById(createDTO.idContrato).get()
            orden.idEstacion = 0
            orden.idCiudad = contrato.idCiudad
            orden.idEmpresa = contrato.idEmpresa
            orden.idServicio = contrato.idServicio
            orden.idDireccion = contrato.idDireccionServicio
            orden.idZona = contrato.idZona
            orden.refiere = contrato.grupo
            orden.idTecnologia = contrato.idTecnologia
            orden.idCliente = contrato.idCliente
            orden.idContrato = createDTO.idContrato

        }else if(createDTO.typeTransfer == 3L)
        {
            var estacion: Estacion = estacionRepository.findById(createDTO.idEstacion).get()
            orden.idEstacion = createDTO.idEstacion
            orden.idContrato = 0
            orden.idCliente = 0
            orden.idDireccion = 0
            orden.idServicio = 0
            orden.idCiudad = estacion.idCiudad
            orden.idEmpresa = estacion.idEmpresa
            orden.idZona = estacion.idZona
            orden.refiere = "A"
        }
        if(orden.refiere.equals("A")){
            numeroA = ordenesRepository.findLastRefiereA(orden.refiere , orden.idEmpresa)
            orden.numeroA = numeroA.let { it + 1}
            orden.numeroB = 0
        }else{
            numeroB = ordenesRepository.findLastRefiereB(orden.refiere , orden.idEmpresa)
            orden.numeroB = numeroB.let { it + 1 }
            orden.numeroA  = 0
        }
        orden.tipoOrden = createDTO.typeOrden
        orden.causaSolicitud = "Orden de Servicio - Corte"
        orden.fechafRegistra = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toLong()
        orden.fechafSolicita = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toLong()
        orden.estado = 0
        orden.nota= createDTO.observation
        orden.idUsuarioRegistra = createDTO.userId
        orden.a = " "
        orden.b = " "
        orden.c = " "
        orden.d = " "
        orden.e = " "
        orden.f = " "
        orden.g = " "
        orden.h = " "
        orden.i = " "
        orden.j = " "
        orden.abierta = 1
        orden.anulada = 0
        orden.winmax = 0
        orden.winmaxIdUsuario =0
        orden.idTicketSoporte = 0
        orden.tipoReconecta = 0
        orden.apiAutomatica = 0
        orden.anulaJustifica = " "
        orden.fechafAnula = 0
        orden.fechafAsigna = 0
        orden.fechafAsiste = 0
        orden.fechafDescarga = 0
        orden.horaAsisteInicio = " "
        orden.hotaAsisteFin = " "
        orden.idUsuarioAnula = 0
        orden.idUsuarioAsigna = 0
        orden.idUsuarioDescarga = 0
        orden.idUsuarioEjecuta = 0
        orden.logApi = " "
        orden.notaFinal = " "
        orden.pdfDescargaFecha = " "
        orden.pdfDescargaUsuario = 0
        orden.ultimaDow  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm")).toString()
        orden.winmaxMarca = "0"
        ordenesRepository.save(orden)
    }

    /**
     * Genera órdenes masivas de corte para contratos con deudas que excedan el umbral configurado
     * @param idServicio ID del servicio para el cual generar las órdenes
     * @param userId ID del usuario que ejecuta la operación
     * @return Lista de órdenes generadas
     * @throws IllegalArgumentException si el servicio no existe
     * @throws RuntimeException si hay errores en la generación
     */

    fun generateOrdenMasivadeCortes(idServicio: Long, userId: Long): MutableList<Orden> {
        return try {
            val servicio = obtenerServicio(idServicio)
                ?: throw IllegalArgumentException("Servicio con ID $idServicio no encontrado")

            val listadoDeudas = obtenerDeudasContrato(idServicio)
            val prorrogasActivas = obtenerProrrogasActivas()
            val repeticionesPorContrato = agruparDeudasPorContrato(listadoDeudas)

            generarOrdenesParaContratos(repeticionesPorContrato, servicio, prorrogasActivas, userId )

        } catch (e: Exception) {
            logger.error("Error al generar órdenes masivas de corte para servicio $idServicio", e)
            throw RuntimeException("Error en la generación de órdenes masivas", e)
        }
    }
    private fun obtenerServicio(idServicio: Long): Servicio? {
        return servicioRepository.findById(idServicio).orElse(null)
    }
    private fun obtenerDeudasContrato(idServicio: Long): List<DeudasContratoMesCoDTO> {
        return deudaRepository.listDeudasxContrato(idServicio).map {
            mapperContratoConDeudas(it)
        }
    }
    private fun obtenerProrrogasActivas(): List<Prorroga> {
        val fechaHoy = LocalDateTime.now().format(FORMATO_FECHA_COMPLETA)
        return prorrogaRepository.findAllByStateAndFechaProrrogaContaining(
            state = ESTADO_PRORROGA_ACTIVA,
            fechaProrroga = fechaHoy
        )
    }
    private fun agruparDeudasPorContrato(deudas: List<DeudasContratoMesCoDTO>): Map<Number, Int> {
        return deudas.groupingBy { it.idContrato }.eachCount()
    }

    private fun generarOrdenesParaContratos(
        repeticionesPorContrato: Map<Number, Int>,
        servicio: Servicio,
        prorrogasActivas: List<Prorroga>,
        userId: Long
    ): MutableList<Orden> {
        val ordenesGeneradas = mutableListOf<Orden>()
        consecutivoA = try {
            ordenesRepository.findLastRefiereA(GRUPO_A, servicio.idEmpresa ?: 0L) ?: 0L
        } catch (e: Exception) { 0L }

        consecutivoB = try {
            ordenesRepository.findLastRefiereB("B", servicio.idEmpresa ?: 0L) ?: 0L
        } catch (e: Exception) { 0L }

        repeticionesPorContrato.forEach { (contratoId, cantidadDeudas) ->
            if (debeGenerarOrdenCorte(cantidadDeudas, servicio.mesescorte.toInt())) {
                val contratoIdLong = contratoId.toLong()

                when {
                    tieneProrrogaActiva(contratoId, prorrogasActivas) -> {
                        logger.info("Contrato $contratoId tiene prórroga activa")
                    }
                    tieneOrdenReconexionPendiente(contratoIdLong) -> {
                        logger.debug("Contrato $contratoId ya tiene orden de reconexión pendiente")
                    }
                    else -> {
                        try {
                            val orden = crearOrdenCorte(contratoIdLong, userId)
                            ordenesGeneradas.add(orden)
                            logger.info("Orden de corte generada para contrato $contratoId")
                        } catch (e: Exception) {
                            logger.error("Error al crear orden para contrato $contratoId", e)
                        }
                    }
                }
            }
        }

        logger.info("Se generaron ${ordenesGeneradas.size} órdenes de corte")
        saveOrdenesCorte(ordenesGeneradas)
        return ordenesGeneradas
    }

    private fun saveOrdenesCorte(ordenesGeneradas: MutableList<Orden>){
        if(ordenesGeneradas.isNotEmpty())
        {
            try {
                ordenesRepository.saveAll(ordenesGeneradas)
                logger.info("${ordenesGeneradas.size} órdenes de corte guardadas exitosamente")
            }catch (e: Exception) {
                logger.error("Error al guardar batch de órdenes de corte", e)
                throw e
            }
        }
    }

    private fun debeGenerarOrdenCorte(cantidadDeudas: Int, umbralCorte: Int): Boolean {
        return cantidadDeudas > umbralCorte
    }

    private fun tieneOrdenReconexionPendiente(contratoId: Long): Boolean {
        return try {
            val resultado = ordenesRepository.existOrdenByStatus(contratoId, TIPOS_ORDEN_RECONEXION)
            (resultado ?: 0) > 0
        } catch (e: Exception) {
            logger.warn("Error al verificar órdenes de reconexión para contrato $contratoId", e)
            false // En caso de error, asumimos que no hay orden pendiente
        }
    }

    private fun tieneProrrogaActiva(contratoId: Number, prorrogas: List<Prorroga>): Boolean {
        return prorrogas.any { it.idContrato == contratoId.toLong() }
    }

    private fun crearOrdenCorte(contratoId: Long, userId: Long): Orden {
        val contrato = contratosRepository.findById(contratoId).orElseThrow {
            IllegalArgumentException("Contrato con ID $contratoId no encontrado")
        }

        return Orden().apply {
            // Información básica de la orden
            tipoOrden = TIPO_ORDEN_CORTE.toLong()
            causaSolicitud = "Orden de Servicio - Corte"
            nota = MENSAJE_ORDEN_GENERADA
            estado = ESTADO_INICIAL.toLong()

            // Información del contrato
            idContrato = contratoId
            idCliente = contrato.idCliente
            idCiudad = contrato.idCiudad
            idEmpresa = contrato.idEmpresa
            idServicio = contrato.idServicio
            idDireccion = contrato.idDireccionServicio
            idZona = contrato.idZona
            idTecnologia = contrato.idTecnologia
            refiere = contrato.grupo

            // Numeración de la orden
            asignarNumeracionOrden(contrato.grupo, contrato.idEmpresa)

            // Fechas
            val fechaActual = LocalDateTime.now()
            fechafRegistra = fechaActual.format(FORMATO_FECHA_CORTA).toLong()
            fechafSolicita = fechaActual.format(FORMATO_FECHA_CORTA).toLong()
            ultimaDow = fechaActual.format(FORMATO_FECHA_HORA)

            // Usuario y estado
            idUsuarioRegistra = userId
            abierta = ORDEN_ABIERTA.toLong()
            anulada = ORDEN_NO_ANULADA.toLong()

            // Inicializar campos por defecto
            inicializarCamposPorDefecto()
        }
    }

    private fun Orden.asignarNumeracionOrden(grupo: String, idEmpresa: Long) {
        if (grupo == GRUPO_A) {

            numeroA = consecutivoA++
            numeroB = 0
        } else {

            numeroB = consecutivoB++
            numeroA = 0
        }
    }

    private fun Orden.inicializarCamposPorDefecto() {
        // Estación y configuración
        idEstacion = 0

        // Campos de texto vacíos
        listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j").forEach { campo ->
            this::class.java.getDeclaredField(campo).apply {
                isAccessible = true
                set(this@inicializarCamposPorDefecto, " ")
            }
        }

        // Configuración WinMax
        winmax = 0
        winmaxIdUsuario = 0
        winmaxMarca = "0"

        // IDs de soporte y tipos
        idTicketSoporte = 0
        tipoReconecta = 0
        apiAutomatica = 0

        // Campos de anulación
        anulaJustifica = " "
        fechafAnula = 0
        idUsuarioAnula = 0

        // Campos de asignación y ejecución
        fechafAsigna = 0
        fechafAsiste = 0
        fechafDescarga = 0
        idUsuarioAsigna = 0
        idUsuarioDescarga = 0
        idUsuarioEjecuta = 0

        // Horarios de asistencia
        horaAsisteInicio = " "
        hotaAsisteFin = " "

        // Logs y notas
        logApi = " "
        notaFinal = " "

        // PDF
        pdfDescargaFecha = " "
        pdfDescargaUsuario = 0
    }


    /*
        fun generateOrdenMasivadeCortes(idServicio:Long , userId:Long): MutableList<Orden> {
            val servicio : Servicio = servicioRepository.findById(idServicio).get()

             //deudas
            val listadoDeudas : List<DeudasContratoMesCoDTO> = deudaRepository.listDeudasxContrato(idServicio).map {
                mapperContratoConDeudas(it)
            };

            val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
            //prorrogas
            val prorrogas : List<Prorroga> = prorrogaRepository.findAllByStateAndFechaProrrogaContaining(state = "A" , fechaProrroga = fecha)

            val repeticiones = listadoDeudas.groupingBy { it.idContrato }.eachCount()
            val listType: List<Int> = listOf<Int>(2,18)
            var listOrdes = mutableListOf<Orden>()
            repeticiones.forEach { (contratoRow , count) ->
                if( count > servicio.mesescorte){
                    //validar si existe ods reconexion
                    val reconexion = ordenesRepository.existOrdenByStatus(contratoRow.toLong() , listType)
                    if(reconexion == 0)
                    {
                        //validar que no este en las prorrogas
                        if(prorrogas.any { it.idContrato == contratoRow }){
                            println("Tiene prorroga")
                        }else{
                            val contrato :Contrato = contratosRepository.findById(contratoRow.toLong()).get()
                            val orden: Orden = Orden()
                            var numeroA:Long = 0L
                            var numeroB:Long  = 0L
                            orden.tipoOrden = 2
                            orden.refiere = contrato.grupo
                            if(orden.refiere.equals("A")){
                                numeroA = ordenesRepository.findLastRefiereA(orden.refiere , orden.idEmpresa)
                                orden.numeroA = numeroA.let { it + 1}
                                orden.numeroB = 0
                            }else{
                                numeroB = ordenesRepository.findLastRefiereB(orden.refiere , orden.idEmpresa)
                                orden.numeroB = numeroB.let { it + 1 }
                                orden.numeroA  = 0
                            }
                            orden.idEstacion = 0
                            orden.idCiudad = contrato.idCiudad
                            orden.idEmpresa = contrato.idEmpresa
                            orden.idServicio = contrato.idServicio
                            orden.idDireccion = contrato.idDireccionServicio
                            orden.idZona = contrato.idZona
                            orden.refiere = contrato.grupo
                            orden.idTecnologia = contrato.idTecnologia
                            orden.idCliente = contrato.idCliente
                            orden.idContrato = contratoRow.toLong()
                            orden.causaSolicitud = "Orden de Servicio - Corte"
                            orden.fechafRegistra = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toLong()
                            orden.fechafSolicita = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toLong()
                            orden.estado = 0
                            orden.nota= "Orden Genereda por modulo de cortes masivos"
                            orden.idUsuarioRegistra = userId
                            orden.a = " "
                            orden.b = " "
                            orden.c = " "
                            orden.d = " "
                            orden.e = " "
                            orden.f = " "
                            orden.g = " "
                            orden.h = " "
                            orden.i = " "
                            orden.j = " "
                            orden.abierta = 1
                            orden.anulada = 0
                            orden.winmax = 0
                            orden.winmaxIdUsuario =0
                            orden.idTicketSoporte = 0
                            orden.tipoReconecta = 0
                            orden.apiAutomatica = 0
                            orden.anulaJustifica = " "
                            orden.fechafAnula = 0
                            orden.fechafAsigna = 0
                            orden.fechafAsiste = 0
                            orden.fechafDescarga = 0
                            orden.horaAsisteInicio = " "
                            orden.hotaAsisteFin = " "
                            orden.idUsuarioAnula = 0
                            orden.idUsuarioAsigna = 0
                            orden.idUsuarioDescarga = 0
                            orden.idUsuarioEjecuta = 0
                            orden.logApi = " "
                            orden.notaFinal = " "
                            orden.pdfDescargaFecha = " "
                            orden.pdfDescargaUsuario = 0
                            orden.ultimaDow  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm")).toString()
                            orden.winmaxMarca = "0"

                            listOrdes.add(orden)
                        }

                    }
                }
            }

            return listOrdes

            //recorrer los clientes




        }
*/
        fun mapperContratoConDeudas(obj : Array<Any>):DeudasContratoMesCoDTO{
            return DeudasContratoMesCoDTO(idContrato = (obj[0]) as Number , mesServicio = (obj[1])as Number )
        }

}
