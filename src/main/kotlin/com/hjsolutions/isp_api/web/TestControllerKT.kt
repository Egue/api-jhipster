package com.hjsolutions.isp_api.web

import com.hjsolutions.isp_api.service.ResendMailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kt/test")
class TestControllerKT(private val resedMail : ResendMailService) {

    @GetMapping("/resend")
    fun testMail(): ResponseEntity<Any> {

        try {
            val resendService = resedMail.sendMail("web@internetinalambrico.com.co")
            return ResponseEntity.ok().body(resendService)
        }catch (e : Exception){
            val response: HashMap<String, String> = HashMap()
            response.put("error" , e.message.toString())
            return ResponseEntity.badRequest().body(response)
        }
    }
}
