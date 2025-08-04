package com.hjsolutions.isp_api.repository

import com.hjsolutions.isp_api.domain.Barrios
import com.hjsolutions.isp_api.domain.EquiposAsignados
import com.hjsolutions.isp_api.domain.PaquetesVenta
import com.hjsolutions.isp_api.domain.Perfiles
import com.hjsolutions.isp_api.domain.Suscripcion
import com.hjsolutions.isp_api.domain.PuntosAccesos
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository interface CablemagSuscripcionesRepository : MongoRepository<Suscripcion, ObjectId> {}

@Repository interface CablemagPaquetesVentaRepository : MongoRepository<PaquetesVenta, ObjectId> {}

@Repository
interface CablemagEquiposAsignadosRepository : MongoRepository<EquiposAsignados, ObjectId> {

    @Aggregation(pipeline = [
        "{ \$match :{ \"PRINCIPAL\": \"S\", \"COD_SERVICIO\": { \$in: [2] }, \"COD_AP\": ?0 } } " ,
            "{ \$lookup: { \"from\": \"suscripciones\", \"localField\": \"COD_SUSCRIPCION\", \"foreignField\": \"CODIGO\", \"as\": \"suscripcion\" } }",
            "{ \$unwind: { \"path\": \"\$suscripcion\", \"preserveNullAndEmptyArrays\": false } }",
            "{ \$match: { \"suscripcion.ESTADO\": { \$in: ?1 } } }"
    ])
    fun findByCodApAndSuscriptionStado(codAp:Number , listEstado:List<String>):List<EquiposAsignados>

    fun findByCodApAndPrincipalAndCodServicio(codAp: Number , principal:String = "S" , codServicio:Number = 2):List<EquiposAsignados>

    /*@Aggregation(
        pipeline = [
            "{ \$match: { \"ACTIVO\": \"S\", \"PRINCIPAL\": \"S\" } }",
            "{ \$lookup: { \"from\": \"paquetes_venta\", \"localField\": \"COD_PAQUETE_VENTA\", \"foreignField\": \"CODIGO\", \"as\": \"paquete_info\" } }",
            "{ \$unwind: { \"path\": \"\$paquete_info\", \"preserveNullAndEmptyArrays\": true } }",
            "{ \$lookup: { \"from\": \"perfiles\", \"localField\": \"paquete_info.COD_PERFIL\", \"foreignField\": \"CODIGO\", \"as\": \"perfil_info\" } }",
            "{ \$unwind: { \"path\": \"\$perfil_info\", \"preserveNullAndEmptyArrays\": true } }",
            "{ \$lookup: { \"from\": \"suscripciones\", \"localField\": \"COD_SUSCRIPCION\", \"foreignField\": \"CODIGO\", \"as\": \"suscripciones\" } }",
            "{ \$unwind: { \"path\": \"\$suscripciones\", \"preserveNullAndEmptyArrays\": true } }",
            "{ \$match: { \"suscripciones.ESTADO\": { \$in: ?0 } } }",
            "{ \$match: { \"perfil_info.COD_SERVICIO\": { \$in: ?1 } } }"
        ]
    )*/
    @Aggregation(
            pipeline =
                    [
                            "{ \$match: { \"ACTIVO\": \"S\", \"PRINCIPAL\": \"S\" } }",
                            "{ \$lookup: { " +
                                    "\"from\": \"paquetes_venta\"," +
                                    "\"localField\": \"COD_PAQUETE_VENTA\"," +
                                    "\"foreignField\": \"CODIGO\", " +
                                    "\"as\": \"paquete_venta\", " +
                                    "\"pipeline\":[" +
                                    "{ \$lookup: { " +
                                    "\"from\":\"contenido_paquete_venta\", " +
                                    "\"localField\": \"CODIGO\" , " +
                                    "\"foreignField\":\"COD_PAQUETE_VENTA\", " +
                                    "\"as\": \"contenido_paquete_venta\", " +
                                    "\"pipeline\": [" +
                                    "{ \$lookup:{ " +
                                    "\"from\": \"velocidades\" , " +
                                    "\"localField\": \"COD_VELOCIDAD\", " +
                                    "\"foreignField\": \"CODIGO\", " +
                                    "\"as\": \"velocidad\" " +
                                    "} }, " +
                                    "{ \$unwind: { \"path\": \"\$velocidad\", \"preserveNullAndEmptyArrays\": true } } " +
                                    "] " +
                                    "} } " +
                                    "] " +
                                    "} }",
                            "{ \$unwind: { \"path\": \"\$paquete_venta\", \"preserveNullAndEmptyArrays\": true } }",
                            "{ \$lookup: { \"from\": \"suscripciones\", \"localField\": \"COD_SUSCRIPCION\", \"foreignField\": \"CODIGO\", \"as\": \"suscripcion\" } }",
                            "{ \$unwind: { \"path\": \"\$suscripcion\", \"preserveNullAndEmptyArrays\": true } }",
                            "{ \$match: { \"suscripcion.ESTADO\": { \$in: ?0 } } }",
                            "{ \$match: { \"paquete_venta.COD_SERVICIO\": { \$in: ?1 } } }"]
    )
    fun findEquipoConPaquetesYPerfilesAnnotation(
            estadoSuscripcion: List<String>,
            codServicio: List<Int>
    ): List<EquiposAsignados>
}

@Repository
interface CablemagPerfilesRepository : MongoRepository<Perfiles, ObjectId> {

    fun findAllByCodServicioIn(codServicio: List<Number>): List<Perfiles>
}

@Repository
interface CablemagBarrioRepository : MongoRepository<Barrios, ObjectId> {}

@Repository
interface CablemagPuntosAccesoRepository : MongoRepository<PuntosAccesos , ObjectId> {

    fun findOneByCodigo(codigo:Number):PuntosAccesos
}

