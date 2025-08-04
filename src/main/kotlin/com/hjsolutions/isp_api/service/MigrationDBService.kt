package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Cliente
import com.comunicamosmas.api.repository.IClienteDao
import com.hjsolutions.isp_api.domain.EquiposAsignados
import com.hjsolutions.isp_api.domain.PuntosAccesos
import com.hjsolutions.isp_api.repository.CablemagBarrioRepository
import com.hjsolutions.isp_api.repository.CablemagEquiposAsignadosRepository
import com.hjsolutions.isp_api.repository.CablemagPerfilesRepository
import com.hjsolutions.isp_api.repository.CablemagPuntosAccesoRepository
import com.hjsolutions.isp_api.service.dto.ClienteCablemagDTO
import com.hjsolutions.isp_api.service.dto.EquipoConSecretDTO
import com.hjsolutions.isp_api.service.dto.ResultadoComparacion
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import com.hj_solutions.isp_api.service.ShhClient
import com.hjsolutions.isp_api.service.dto.PppSecreDTO
import com.hjsolutions.isp_api.service.dto.parseMikrotik
import java.nio.charset.StandardCharsets

@Service
class MigrationDBService(
        private val clienteRepository: IClienteDao,
        private val repositoryBarrios: CablemagBarrioRepository,
        private val repositoryPerfiles: CablemagPerfilesRepository,
        private val repositoryEquiposAsignado: CablemagEquiposAsignadosRepository,
        private val repositoryPuntoAcceso: CablemagPuntosAccesoRepository
) {

    fun processCsv(file: MultipartFile) {
        var list: List<ClienteCablemagDTO> =
                BufferedReader(InputStreamReader(file.inputStream)).use { reader ->
                    reader.lines()
                            .skip(1) // Saltar encabezado
                            .map { parseLine(it) }
                            .filter { it != null }
                            .map { it!! }
                            .collect(Collectors.toList())
                }
        val clientes = mutableListOf<Cliente>()
        list.forEach { item ->
            val cli = Cliente()
            var tipoCliente: String
            var razonSocial: String = " "
            var namePrimer: String = " "
            var otherName: String = " "
            var tipoDocument = 0

            if (item.tipo_identificacion == "NI") {
                tipoCliente = "J"
                razonSocial = "${item.nombre} ${item.apellidos}"
                tipoDocument = 2
            } else {
                tipoCliente = "N"
                namePrimer = if (item.nombre.isEmpty()) " " else item.nombre
                otherName = if (item.apellidos.isEmpty()) " " else item.apellidos
                tipoDocument = 1
            }
            cli.setTipoCliente(tipoCliente)
            cli.setRazonSocial(razonSocial)
            cli.setNombreComercial(" ")
            cli.setApellidoPaterno(otherName)
            cli.setApellidoMaterno(" ")
            cli.setNombrePrimer(namePrimer)
            cli.setNombreSegundo(" ")
            cli.setGenero(if (item.sexo.isEmpty()) "M" else item.sexo)
            cli.setNombresRep(
                    if (item.nombre_representante.isEmpty()) " " else item.nombre_representante
            )
            cli.setApellidosRep(" ")
            cli.setIdDocumento(tipoDocument)
            cli.setDocumento(item.identificacion.toLong())
            cli.setDv(if (item.dv.isEmpty()) 0L else item.dv.toLong())
            cli.setfNacimiento(19000000)
            cli.setEstadoCivil("1")
            cli.setTipoVivienda("1")
            cli.setTelefono(0L) // if(item.telefono1.isEmpty()) 0L else item.telefono1.toLong())
            cli.setCelularA(if (item.movil.trim().isEmpty()) "0" else item.movil.trim())
            cli.setIdOperaA(1L)
            cli.setCelularB(
                    if (item.movil_referencia.trim().isEmpty()) "0"
                    else item.movil_referencia.trim()
            )
            cli.setIdOperaB(1L)
            cli.setMail(if (item.email.isEmpty()) "notiene@notiene.com" else item.email)
            cli.setEstrato(0)
            cli.setObservaciones(item.id.toString())
            cli.setSha(" ")
            cli.setBomberil(0L)
            cli.setAutorizaSms(1L)
            cli.setPortalweb("")
            clientes.add(cli)
        }

        clienteRepository.saveAll(clientes)
    }

    private fun parseLine(line: String): ClienteCablemagDTO? {
        return try {
            // Usamos regex para manejar comas dentro de campos entre comillas
            val values =
                    line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                            .map { it.trim().removeSurrounding("\"") }
                            .toTypedArray()

            ClienteCablemagDTO(
                    id = values.getOrNull(0)?.toIntOrNull(),
                    identificacion = values.getOrNull(1)?.toIntOrNull() ?: 0,
                    tipo_identificacion = values.getOrNull(2) ?: "",
                    dv = values.getOrNull(3) ?: "",
                    sexo = values.getOrNull(4) ?: "",
                    estado_civil = values.getOrNull(5) ?: "",
                    cod_municipio = values.getOrNull(6) ?: "",
                    fecha_expedicion_cedula = values.getOrNull(7) ?: "",
                    nombre = values.getOrNull(8) ?: "",
                    apellidos = values.getOrNull(9) ?: "",
                    fecha_nacimiento = values.getOrNull(10) ?: "",
                    cod_municipio_domicilio = values.getOrNull(11) ?: "",
                    cod_barrio_domicilio = values.getOrNull(12) ?: "",
                    direccion_domicilio = values.getOrNull(13) ?: "",
                    punto_referencia_domicilio = values.getOrNull(14) ?: "",
                    indicativo1 = values.getOrNull(15) ?: "",
                    telefono1 = values.getOrNull(16) ?: "",
                    extension1 = values.getOrNull(17) ?: "",
                    indicativo2 = values.getOrNull(18) ?: "",
                    telefono2 = values.getOrNull(19) ?: "",
                    extension2 = values.getOrNull(20) ?: "",
                    movil = values.getOrNull(21) ?: "",
                    email = values.getOrNull(22) ?: "",
                    actividad_economica = values.getOrNull(23) ?: "",
                    empresa = values.getOrNull(24) ?: "",
                    cod_municipio_empresa = values.getOrNull(25) ?: "",
                    cod_barrio_empresa = values.getOrNull(26) ?: "",
                    cod_direccion_empresa = values.getOrNull(27) ?: "",
                    indicativo_empresa = values.getOrNull(28) ?: "",
                    telefono_empresa = values.getOrNull(29) ?: "",
                    extension_empresa = values.getOrNull(30) ?: "",
                    nombre_completo_referencia = values.getOrNull(31) ?: "",
                    cod_municipio_referencia = values.getOrNull(32) ?: "",
                    cod_barrio_referencia = values.getOrNull(33) ?: "",
                    direccion_referencia = values.getOrNull(34) ?: "",
                    indicativo_referencia = values.getOrNull(35) ?: "",
                    telefono_referencia = values.getOrNull(36) ?: "",
                    extension_referencia = values.getOrNull(37) ?: "",
                    movil_referencia = values.getOrNull(38) ?: "",
                    movil_traslado = values.getOrNull(39) ?: "",
                    digito_verificacion = values.getOrNull(40) ?: "",
                    nombre_representante = values.getOrNull(41) ?: "",
                    identificacion_representante = values.getOrNull(42) ?: "",
                    fecha = values.getOrNull(43) ?: ""
            )
        } catch (e: Exception) {
            null // O podrías lanzar una excepción personalizada
        }
    }

    /*migrations suscripciones */
    fun migration_suscripciones(
            estadoSuscripcion: List<String>,
            codServicio: List<Int>
    ): List<Any> {
        if (estadoSuscripcion.isEmpty() || codServicio.isEmpty()) {
            throw IllegalArgumentException("estadoSuscripcion y codServicio no pueden ser vacíos")
        }
        return repositoryEquiposAsignado.findEquipoConPaquetesYPerfilesAnnotation(
                estadoSuscripcion,
                codServicio
        )
    }

    fun validateByStation(): List<ResultadoComparacion> {
        // consultar estaciones
        val lisEstaciones = repositoryPuntoAcceso.findAll()
        val listResultado = mutableListOf<ResultadoComparacion>()
        lisEstaciones.forEach { estacion ->
            // buscar lista de suscripciones por estacion
            val equipoAsigando: List<EquiposAsignados> =
                    repositoryEquiposAsignado.findByCodApAndPrincipalAndCodServicio(codAp = estacion.codigo)
            // realizar la conexion
            val clientMikrotik: MikrotikClient = MikrotikClient()
            // extraer los profiles
            val commando: String = "/ppp/secret/print"
            try {

                /*clientMikrotik.getApiConnection(
                        estacion.usuarioTorre,
                        estacion.claveTorre,
                        estacion.ip,
                        estacion.puerto.toInt()
                )*/
                val listSecrets: List<Map<String, String>> =
                        clientMikrotik.executeCommand(user = estacion.usuarioTorre,
                         pass = estacion.claveTorre,   ip = estacion.ip,      port = estacion.puerto.toInt(),  command = commando)
                val rbNames =
                        listSecrets.filter { it.containsKey("name") }.associateBy {
                            it["name"].orEmpty()
                        }
                clientMikrotik.closeConnection()
                // separar requipos con y sin perfil usando partition
                val (conSecret, sinSecret) =
                        equipoAsigando.partition { equipo ->
                            equipo.usuario != null && rbNames.containsKey(equipo.usuario)
                        }
                // crear lista de equipos con perfil
                val equiposConSecret =
                        conSecret.map { equipo ->
                            EquipoConSecretDTO(equipo, rbNames[equipo.usuario]!!)
                        }

                listResultado.add(ResultadoComparacion(equiposConSecret, sinSecret))
            } catch (e: Exception) {
                println("Error con estación ${estacion.codigo}: ${e.message}")
                e.printStackTrace()
            } finally {
                clientMikrotik.closeConnection()
            }
        }

        return listResultado
    }

    fun appConexion(ap:String):List<PppSecreDTO>{
        val sshClient:ShhClient =  ShhClient()

        val app:PuntosAccesos = repositoryPuntoAcceso.findOneByCodigo(ap.toInt())
        var comando:String = "/ppp secret print terse"
        try {
            val response:String =  sshClient.execute(username = app.usuarioTorre, host = app.ip, port = app.puerto.toInt(), password = app.claveTorre, comando = comando)
            val profiles:List<PppSecreDTO>  = parseMikrotik(response.trimIndent())
            return profiles
        }
        catch(e : Exception) {
            e.printStackTrace()
        }
        return TODO("Provide the return value")
    }

    fun getListByAp(codAp:Number): ByteArray{
        val stado = mutableListOf<String>("N" ,"P" , "C", "T" , "L")
        val resultado = repositoryEquiposAsignado.findByCodApAndSuscriptionStado(codAp = codAp , listEstado = stado )
        val csvHeader = "NUMERO,FECHA_ASIGNACION,COD_SUSCRIPCION,COD_SERVICIO,IP_PUBLICA,USUARIO,CLAVE,SUSCRIPCION.IDENTIFICACION,SUSCRIPCION.DIR_INSTALACION,SUSCRIPCION.ESTADO"
        val csvBody = resultado.map { equipo ->
            listOf(
                equipo.numero.toString(),
                equipo.fechaAsignacion ?: "",
                equipo.codSuscripcion.toString(),
                equipo.codServicio.toString(),
                equipo.ipPublica,
                equipo.usuario,
                equipo.clave,
                equipo.suscripcion?.identificacion.toString(),
                equipo.suscripcion?.dirInstalacion.toString(),
                equipo.suscripcion?.estado.toString()

            ).joinToString(","){ it.wrapCsv()}
        }.joinToString("\n")


        val csvContent = "$csvHeader\n$csvBody"

        val csvBytes = csvContent.toByteArray(Charsets.UTF_8)

        return csvBytes
    }

    fun String.wrapCsv(): String {
        val cleaned = this.replace("\"", "\"\"")
        return "\"$cleaned\""
    }
}
