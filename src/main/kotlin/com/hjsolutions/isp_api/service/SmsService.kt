package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.repository.IContratoDao
import com.hjsolutions.isp_api.domain.Numbers
import com.hjsolutions.isp_api.domain.Sms
import com.hjsolutions.isp_api.repository.SmsRepository
import org.springframework.stereotype.Service

@Service
class SmsService(private val smsRepository: SmsRepository , private val contratoRepository: IContratoDao) {

    fun create(idServicio:Number, name:String, description:String){
        //
        var sms:Sms =  Sms(idServicio = idServicio , name = name, description = description)

        smsRepository.save(sms)
    }

    fun listStatus(status:Int): MutableList<Sms>{

        return smsRepository.findAllByStatus(status)
    }

    fun uploadNumbersByIdService(idServicio:Long , id:String , status: Long){
        //find numbers
            var sms : Sms = smsRepository.findById(id).get()
            //find list numeros
        var listaNumeros : List<Array<Any>> = contratoRepository.listNumberbyidService(idServicio , status)

        val procesados:List<String> = procesarLista(listaNumeros)
        var nuevaLista:List<Numbers> = procesados.map { it ->
             Numbers(number = it.toString())
        }
        sms.numbers = nuevaLista

        smsRepository.save(sms)

    }

    fun procesarLista(numeros:List<Array<Any>>):List<String>{
        return numeros.flatMap { fila ->
            listOf(fila[0] , fila[1])
        }
            .mapNotNull { it?.toString()?.trim() }
            .filter { celular ->
                celular.matches(Regex("^\\d{10}$"))
            }
    }

    fun asignateForListString(list:List<String> , id:String){

        var sms: Sms = smsRepository.findById(id).get()
        var lista = list.map { number->
            Numbers(number = number)
        }
        sms.numbers = lista
        smsRepository.save(sms)
    }

    fun asingateUser(id:String , userId:Long){
        //find
        var sms: Sms = smsRepository.findById(id).get()

        sms.userId = userId

        smsRepository.save(sms)
    }

    fun query(userId: Int , status:Int): MutableList<Sms>{

        return  smsRepository.findAllByStatusAndUserId(status , userId)


    }

    fun status(id:String){
        var sms: Sms = smsRepository.findById(id).get()
        sms.status = 2
        smsRepository.save(sms)
    }

}
