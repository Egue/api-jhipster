package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.ClausulaPermanencia
import com.comunicamosmas.api.domain.Cliente
import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.domain.Direccion
import com.comunicamosmas.api.domain.ListaDepartamento
import com.comunicamosmas.api.domain.ListaMunicipio
import com.comunicamosmas.api.domain.SystemConfig
import com.comunicamosmas.api.domain.Tarifa
import com.comunicamosmas.api.domain.Usuario
import com.comunicamosmas.api.repository.IClausulaPermanenciaDao
import com.comunicamosmas.api.repository.IClienteDao
import com.comunicamosmas.api.repository.IContratoDao
import com.comunicamosmas.api.repository.IDireccionDao
import com.comunicamosmas.api.repository.IListaDepartamentoDao
import com.comunicamosmas.api.repository.IListaMunicipioDao
import com.comunicamosmas.api.repository.ISystemConfigDao
import com.comunicamosmas.api.repository.ITipoTecnologiaDao
import com.comunicamosmas.api.repository.IUsuarioDao
import com.comunicamosmas.api.service.ITarifaService
import com.google.gson.Gson
import com.hjsolutions.isp_api.service.dto.ClausulaContrato
import com.hjsolutions.isp_api.service.dto.ClienteContrato
import com.hjsolutions.isp_api.service.dto.ContratoInfo
import com.hjsolutions.isp_api.service.dto.DatosContrato
import com.hjsolutions.isp_api.service.dto.DireccionContrato
import com.hjsolutions.isp_api.service.dto.TarifaContrato
import com.hjsolutions.isp_api.service.dto.VendedorContrato
import io.appwrite.Client
import io.appwrite.services.Databases
import org.springframework.stereotype.Service
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException

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
    private val tipoTecnologiaRepository: ITipoTecnologiaDao
) {

      suspend fun sincronice(idContrato:Long){
        val client: Client = AppWriteClient().client()
          val database_id = AppWriteClient().app_database()
        val database = Databases(client)
        try {
            val contrato: Contrato = contratoRepository.findById(idContrato).get()
            val cliente: Cliente = clienteRepository.findById(contrato.idCliente).get()
            val tarifa: Tarifa = tarifaRepository.findById(contrato.idTarifaPromo)
            val vendedor: Usuario = userRepository.findById(contrato.idVendedor).get()
            val clausula: ClausulaPermanencia = clausulaRepository.findByIdContrato(idContrato)
            val systemConfig: SystemConfig = systemConfingRepository.findByOrigen("implementacion")
            val systemConfigPath: SystemConfig = systemConfingRepository.findByOrigen("apk_path_contrato")

            var dtoContrato: DatosContrato = datosContratoMapper(contrato)
            var dtoCliente: ClienteContrato = datosClienteMapper(cliente)
            var dtoTarifa: TarifaContrato = datosTarifasMapper(tarifa)
            var dtoVendedor: VendedorContrato = datosVendedorMapper(vendedor)
            var dtoClausula: ClausulaContrato = datosClausula(clausula)
            var contratoDigital: ContratoInfo = ContratoInfo(
                    cliente = dtoCliente,
                    contrato = dtoContrato,
                    tarifa = dtoTarifa,
                    clausula = dtoClausula,
                    vendedor = dtoVendedor
            )
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

    private fun datosClausula(c: ClausulaPermanencia): ClausulaContrato{
        val conexion: Double = listOf(
            c.mes1, c.mes2, c.mes3, c.mes4, c.mes5, c.mes6,
            c.mes7, c.mes8, c.mes9, c.mes10, c.mes11, c.mes12
        ).sumOf { (it ?: 0L).toDouble() }

        return ClausulaContrato(
            conexion = conexion,
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
            tecnologia = typotecnologia.nombre

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

        return DatosContrato(
            id_contrato = contrato.id,
            estrato = contrato.estrato,
            consecutivo = contrato.fisico,
            origen = contrato.grupo,
            observacion = contrato.nota,
            direccionServicio = datosDireccionMapper(direccionServicio),
            direccionResidencia = datosDireccionMapper(direccionResidencia),
            valor_instalacion = 0,
            valor_traslado = 0,
            valor_reconexion = 0,
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
