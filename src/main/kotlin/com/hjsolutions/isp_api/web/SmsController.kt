package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.domain.Sms
import com.hjsolutions.isp_api.service.SmsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt/sms")
class SmsController(private val smsService: SmsService) {

    /*@GetMapping("listbyuser")
    fun listbyuser(@RequestParam("id") id:Long): ResponseEntity<Any>{

    }*/

    @PostMapping("create")
    fun create(@RequestParam("idServicio") idServicio: Number, @RequestParam("name") name:String, @RequestParam("description")description:String ): ResponseEntity<Any>{

        try {
            smsService.create(idServicio = idServicio , name = name , description= description)

            return ResponseEntity.ok().build()
        }catch (e: Exception)
        {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("list")
    fun list(@RequestParam("status") status:String): ResponseEntity<Any>{
        val list: Any = smsService.listStatus(status.toInt())

        return ResponseEntity.ok().body(list)
    }

    @GetMapping("asignate/numbersbyidService")
    fun numbersbyidService(@RequestParam("idService") idService:String , @RequestParam("id") id:String , @RequestParam("status") status:String): ResponseEntity<Any>{

        smsService.uploadNumbersByIdService(idService.toLong() , id=id , status = status.toLong());

        return ResponseEntity.ok().build()
    }

    @PostMapping("asignate/forlist")
    fun asignateForList(
        @RequestParam("list") list: String,
        @RequestParam("id") id:String
    ): ResponseEntity<Any>{

       try {
           var listString : List<String> = list.split(",")

           smsService.asignateForListString(listString , id)

           return ResponseEntity.ok().build()
       }catch (e: Exception)
       {
           return ResponseEntity.badRequest().body(e.message)
       }
    }

    @PostMapping("asignate/user")
    fun asignateUser(@RequestParam("id") id:String , @RequestParam("userId")userId:String): ResponseEntity<Any>{

        try {
            smsService.asingateUser(id , userId.toLong())

            return ResponseEntity.ok().build()
        }catch (e: Exception){
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("sms/query")
    fun query(@RequestParam("userId") userId:String , @RequestParam("status") status:String): ResponseEntity<Any>{

        try {
            var list : MutableList<Sms> =  smsService.query(userId.toInt() , status.toInt())

            return ResponseEntity.ok().body(list)
        }catch (e: Exception)
        {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("status")
    fun updatedStatus(@RequestParam("id") id:String): ResponseEntity<Any>{
        try {
            smsService.status(id)
            return ResponseEntity.ok().build()
        }catch (e: Exception){
            return ResponseEntity.badRequest().build()
        }
    }
}
