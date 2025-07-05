package com.hjsolutions.isp_api.repository

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.stereotype.Repository
import org.bson.types.ObjectId
import org.springframework.data.repository.query.Param
import com.hjsolutions.isp_api.domain.Suscripciones
import com.hjsolutions.isp_api.domain.Barrios
import com.hjsolutions.isp_api.domain.PaquetesVenta
import com.hjsolutions.isp_api.domain.Perfiles
import com.hjsolutions.isp_api.domain.EquiposAsignados

@Repository
interface CablemagSuscripcionesRepository : MongoRepository<Suscripciones, ObjectId> {
     
    
}

@Repository
interface CablemagPaquetesVentaRepository : MongoRepository<PaquetesVenta , ObjectId>{

     
}

@Repository
interface CablemagEquiposAsignadosRepository : MongoRepository<EquiposAsignados , ObjectId>{
    @Aggregation(pipeline=[
        "{'\$match'  : {'ACTIVO':'S' , 'PRINCIPAL':'S'}}",
        //lookup para paquetes ventas
        "{'\$lookup' : {'from': 'paquetes_venta' , 'localField':'COD_PAQUETE_VENTA', 'foreignField': 'CODIGO', 'as' :'paquete_info'}}",
        "{'\$unwind' : {'path': '\$paquete_info', 'preserveNullAndEmptyArrays':true}}",
        //lookup para perfiles
        "{'\$lookup' : {'from': 'perfiles', 'localField': 'paquete_info.COD_PERFIL' , 'foreignField': 'CODIGO' , 'as':'perfil_info'}}",
        "{'\$unwind' : {'path': '\$perfil_info' , 'preserveNullAndEmptyArrays':true}}",
        //lookup suscripciones
        "{'\$lookup' : {'from': 'suscripciones' , 'localField':'COD_SUSCRIPCION' , 'foreignField' : 'CODIGO' , 'as':'suscripcion_info'}}",
        "{'\$unwind' : {'path': '\$suscripcion_info' , 'preserveNullAndEmptyArrays':true}}",
        //filtros
        "{'\$match'  : {'suscripcion_info.ESTADO' : {'\$in': ?#(#estado)}}}",
        "{'\$match'  : {'perfil_info.COD_SERVICIO' : {'\$in' : ?#(#cod_servicio)}}}",
    ])
    fun findEquipoConPaquetesYPerfilesAnnotation(
        @Param("estado") estadoSuscripcion: List<String>,
        @Param("cod_servicio") codServicio:List<Int>
    ):List<EquiposAsignados>
}

@Repository
interface CablemagPerfilesRepository : MongoRepository<Perfiles , ObjectId>{

    fun findAllByCodServicioIn(codServicio : List<Number>):List<Perfiles>
}

@Repository 
interface CablemagBarrioRepository : MongoRepository<Barrios, ObjectId> {}