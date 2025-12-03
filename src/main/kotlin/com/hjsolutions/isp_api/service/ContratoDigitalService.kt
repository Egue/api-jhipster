package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.ClausulaPermanencia
import com.comunicamosmas.api.domain.Cliente
import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.domain.Direccion
import com.comunicamosmas.api.domain.Empresa
import com.comunicamosmas.api.domain.ListaDepartamento
import com.comunicamosmas.api.domain.ListaMunicipio
import com.comunicamosmas.api.domain.SystemConfig
import com.comunicamosmas.api.domain.Tarifa
import com.comunicamosmas.api.domain.TarifaInstalacion
import com.comunicamosmas.api.domain.Usuario
import com.comunicamosmas.api.repository.IClausulaPermanenciaDao
import com.comunicamosmas.api.repository.IClienteDao
import com.comunicamosmas.api.repository.IContratoDao
import com.comunicamosmas.api.repository.IDireccionDao
import com.comunicamosmas.api.repository.IEmpresaDao
import com.comunicamosmas.api.repository.IListaDepartamentoDao
import com.comunicamosmas.api.repository.IListaMunicipioDao
import com.comunicamosmas.api.repository.ISystemConfigDao
import com.comunicamosmas.api.repository.ITarifaInstalacionDao
import com.comunicamosmas.api.repository.ITipoTecnologiaDao
import com.comunicamosmas.api.repository.IUsuarioDao
import com.comunicamosmas.api.service.ITarifaService
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.hjsolutions.isp_api.domain.Templates
import com.hjsolutions.isp_api.repository.TemplateRepository
import com.hjsolutions.isp_api.repositoryMysql.ContratoRepository
import com.hjsolutions.isp_api.service.dto.AppWriteContrato
import com.hjsolutions.isp_api.service.dto.ClausulaContrato
import com.hjsolutions.isp_api.service.dto.ClienteContrato
import com.hjsolutions.isp_api.service.dto.ContratoInfo
import com.hjsolutions.isp_api.service.dto.DatosContrato
import com.hjsolutions.isp_api.service.dto.DireccionContrato
import com.hjsolutions.isp_api.service.dto.DocumentImages
import com.hjsolutions.isp_api.service.dto.TarifaContrato
import com.hjsolutions.isp_api.service.dto.TypeService
import com.hjsolutions.isp_api.service.dto.VendedorContrato
import io.appwrite.Client
import io.appwrite.services.Databases
import org.springframework.stereotype.Service
import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.models.RowList
import io.appwrite.services.TablesDB
import liquibase.pro.packaged.ac
import liquibase.pro.packaged.da
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ContratoDigitalService(
    private val contratoRepository: IContratoDao,
    private val clienteRepository: IClienteDao,
    private val direccionRepository: IDireccionDao,
    private val tarifaRepository: ITarifaService,
    private val userRepository: IUsuarioDao,
    private val clausulaRepository: IClausulaPermanenciaDao,
    private val departamentoRepository: IListaDepartamentoDao,
    private val listaMunicipioRepository: IListaMunicipioDao,
    private val systemConfingRepository: ISystemConfigDao,
    private val tipoTecnologiaRepository: ITipoTecnologiaDao,
    private val templateRepository: TemplateRepository,
    private val empresaRepository: IEmpresaDao,
    private val tarifaInstalacionRepository: ITarifaInstalacionDao
) {

    private val gson = Gson()

    suspend fun link_contrato_pdf(): SystemConfig{
        val system : SystemConfig = systemConfingRepository.findByOrigen("services_pdf_contrato")

        return system;
    }

    suspend fun findContratoByImplementacion(contrato:String): RowList<Map<String , Any>>
    {
        val client : Client = AppWriteClient().client()
        val database_id = AppWriteClient().app_database()
        val tablesDB = TablesDB(client)
        val implementacion : SystemConfig = systemConfingRepository.findByOrigen("implementacion")
        try{

            val response: RowList<Map<String, Any>> = tablesDB.listRows(
                databaseId = database_id,
                tableId = "contratos",
                queries = listOf(
                    Query.equal("id_contrato" , contrato ),
                    Query.equal("implementacion" , implementacion.comando)

                )
            )

            return response

        }catch (e: AppwriteException){
            throw Exception("fallo al consultar : ${e.message}")
        }catch (e: Exception){
            throw Exception("error al generar el envio ${e.message}")
        }

    }

    suspend fun templateAndInfoContrato(idContrato:Long):Map<String, Any?>{

        val (contratoInfo , contrato) = generateInformationContrato(idContrato)
        val empresa: Empresa = empresaRepository.findById(contrato.idEmpresa).get()

        val template: Templates? = templateRepository.findOneByIdServicioAndName(idServicio = contrato.idServicio , name = "contrato")
        val response = mapOf(
            "empresa" to empresa,
            "contrato" to contratoInfo,
            "template" to template?.template
        )
        return response
    }

    private fun generateInformationContrato(idContrato:Long): Pair<ContratoInfo , Contrato>{
        val contrato: Contrato = contratoRepository.findById(idContrato).get()
        val cliente: Cliente = clienteRepository.findById(contrato.idCliente).get()
        val tarifa: Tarifa = tarifaRepository.findById(contrato.idTarifaPromo)
        val vendedor: Usuario = userRepository.findById(contrato.idVendedor).get()
        val clausula: ClausulaPermanencia = clausulaRepository.findByIdContrato(idContrato)
        var dtoContrato: DatosContrato = datosContratoMapper(contrato)
        var dtoCliente: ClienteContrato = datosClienteMapper(cliente)
        var dtoTarifa: TarifaContrato = datosTarifasMapper(tarifa)
        var dtoVendedor: VendedorContrato = datosVendedorMapper(vendedor)
        var dtoInstalacion: TarifaInstalacion = tarifaInstalacionRepository.findById(contrato.idTarifaInstalacion.toLong()).get()
        var dtoClausula: ClausulaContrato = datosClausula(clausula , dtoInstalacion.valor , contrato.duracion  , contrato.marca)
        var medios : SystemConfig = systemConfingRepository.findByOrigen("medios_atencion")
        var contratoDigital: ContratoInfo = ContratoInfo(
            cliente = dtoCliente,
            contrato = dtoContrato,
            tarifa = dtoTarifa,
            clausula = dtoClausula,
            vendedor = dtoVendedor,
            mediosAtencion = medios.comando
        )

        return Pair(contratoDigital , contrato)
    }

    suspend fun findContratoAppwrite(idContrato:String): List<AppWriteContrato?> {
        val client: Client = AppWriteClient().client()
        val database_id = AppWriteClient().app_database()
        val database = Databases(client)
        val implementacion : SystemConfig = systemConfingRepository.findByOrigen("implementacion")
        try {
            val response = database.listDocuments(
                database_id,
                "contratos",
                listOf(
                     Query.equal("id_contrato" , idContrato),
                    Query.equal("implementacion" , implementacion.comando)
                )
            )

            val contrato:List<AppWriteContrato> = response.documents.map { document->
                val images = parseDocumentJson(document.data["documentos"] as? String?: "")
                AppWriteContrato(
                    id_contrato = document.data["id_contrato"] as? String?: "",
                    id_servicio = document.data["id_servicio"] as? String?: "",
                    id_cliente = document.data["id_cliente"] as? String?: "",
                    firma = document.data["firma"] as? String ?: "",
                    documentos = images
                )
            }

            return contrato
        }catch (e: Exception){
            e.printStackTrace()

            return emptyList()
        }


    }

    private fun parseDocumentJson(document:String): DocumentImages?{
        return try {
            if(document.isEmpty()){
                null
            }else{
                gson.fromJson(document , DocumentImages::class.java)
            }
        }catch (e: JsonSyntaxException){
            null
        }
    }

      suspend fun sincronice(idContrato:Long){
        val client: Client = AppWriteClient().client()
          val database_id = AppWriteClient().app_database()
        val database = Databases(client)
        try {

            val systemConfig: SystemConfig = systemConfingRepository.findByOrigen("implementacion")
            val systemConfigPath: SystemConfig = systemConfingRepository.findByOrigen("apk_path_contrato")

            val (contratoDigital , contrato) = generateInformationContrato(idContrato)

            val gson = Gson()
            val json = gson.toJson(contratoDigital)
            val documentData: Map<String, Any> = mapOf(
                "id_contrato" to contrato.id.toString(),
                "id_cliente" to contrato.idCliente.toString(),
                "id_servicio" to contrato.idServicio,
                "implementacion" to systemConfig.comando,
                "path_contrato" to systemConfigPath.comando,
                "contrato" to json
            )
            val document = database.createDocument(
                databaseId = database_id,
                collectionId = "contratos",
                documentId = ID.unique(),
                data = documentData
            )
        }catch (e: AppwriteException){
            throw Exception("fallo al guardar : ${e.message}")
        }catch (e: Exception){
            throw Exception("error al generar el envio ${e.message}")
        }
    }

    private fun datosClausula(c: ClausulaPermanencia , instalacion:Long , duracion: Long , registro:String): ClausulaContrato{
        val conexion = c.mes1.toLong() + instalacion
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        val fecha = LocalDateTime.parse(registro, inputFormatter)
        val fechaNueva = fecha.plusMonths(duracion)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val resultado = fechaNueva.format(outputFormatter)
        return ClausulaContrato(
            conexion = conexion,
            instalacion = instalacion,
            duracion = resultado,
            mes_1 = c.mes1,
            mes_2 = c.mes2,
            mes_3 = c.mes3,
            mes_4 = c.mes4,
            mes_5 = c.mes5,
            mes_6 = c.mes6,
            mes_7 = c.mes7,
            mes_8 = c.mes8,
            mes_9 = c.mes9,
            mes_10 = c.mes10,
            mes_11 = c.mes11,
            mes_12 = c.mes12
        )
    }

    private fun datosVendedorMapper(user: Usuario): VendedorContrato{
        return VendedorContrato(
            nombre = "${user.nombre} ${user.apellidos}"
        )
    }

    private fun datosTarifasMapper(tarifa: Tarifa): TarifaContrato{
        val typotecnologia = tipoTecnologiaRepository.findById(tarifa.idTecnologia).get()
        return TarifaContrato(
            valor = tarifa.valor.toDouble(),
            tecnologia = typotecnologia.nombre,
            velocidad = tarifa.velocidad.toString()
        )
    }

    private fun datosClienteMapper(cliente: Cliente): ClienteContrato{
        return ClienteContrato(
            id_cliente = cliente.id,
            nombre_cliente = when (cliente.tipoCliente) {
                "N" -> "${cliente.nombrePrimer} ${cliente.nombreSegundo} ${cliente.apellidoPaterno} ${cliente.apellidoMaterno}"
                 else -> "${cliente.razonSocial}"
            },
            tipo_cliente = cliente.tipoCliente,
            documento_cliente = "${cliente.documento} ${cliente.dv}",
            correo_cliente = cliente.mail,
            numeros_cliente = "${cliente.celularA} / ${cliente.celularB}"

        )
    }
    private fun datosContratoMapper(contrato:Contrato): DatosContrato{
        val direccionServicio: Direccion = direccionRepository.findById(contrato.idDireccionServicio).get()
        val direccionResidencia: Direccion = direccionRepository.findById(contrato.idDireccionFactura).get()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        val fecha = LocalDateTime.parse(contrato.marca  , formatter)
        val activacion = fecha.plusDays(6)
        val formatterNueva = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaActivacion = activacion.format(formatterNueva)
        val fechaRegistro = fecha.format(formatterNueva)
        return DatosContrato(

            id_contrato = contrato.id,
            estrato = contrato.estrato,
            consecutivo = contrato.fisico,
            origen = contrato.grupo,
            observacion = contrato.nota,
            direccionServicio = datosDireccionMapper(direccionServicio),
            direccionResidencia = datosDireccionMapper(direccionResidencia),
            valor_traslado = 0,
            valor_reconexion = 0,
            vigencia = contrato.duracion,
            registro = fechaRegistro.toString(),
            activacion = fechaActivacion.toString()
        )
    }

    private fun datosDireccionMapper(direccion:Direccion): DireccionContrato{
        val departamento: ListaDepartamento = departamentoRepository.findById(direccion.departamento.toLong()).get()
        val municipio: ListaMunicipio = listaMunicipioRepository.findById(direccion.municipio.toLong()).get()
        return DireccionContrato(
            departamento = departamento.departamento,
            municipio = municipio.municipio,
            barrio = direccion.barrio,
            nomenclatura = "${direccion.atipo} ${direccion.anumero} ${direccion.aletra} ${direccion.btipo} ${direccion.bnumero} ${direccion.bletra} ${direccion.numero}"

        )
    }


}
