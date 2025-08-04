package com.hjsolutions.isp_api.repository
import com.hjsolutions.isp_api.domain.Prorroga
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.bson.types.ObjectId;

@Repository
interface ProrrogaRepository: MongoRepository<Prorroga, String> {

    fun findAllByIdContrato(@Param("idContrato") idContrato:Long):List<Prorroga>

    fun findAllByIdContratoAndFechaProrrogaContaining(@Param("idContrato") idContrato:Long , @Param("fechaProrroga") fechaProrroga:String):List<Prorroga>

    fun findAllByIdContratoAndFechaProrroga(@Param("idContrato") idContrato:Long , @Param("fechaProrroga") fechaProrroga:String):List<Prorroga>

    fun findAllByStateAndFechaProrrogaContaining(@Param("state") state:String , @Param("fechaProrroga") fechaProrroga:String):List<Prorroga>
     
}