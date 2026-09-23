package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.SystemConfig
import com.comunicamosmas.api.repository.ISystemConfigDao
import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import com.resend.services.emails.model.CreateEmailResponse
import org.springframework.stereotype.Service

@Service
class ResendMailService(private val systemConfig : ISystemConfigDao) {

    fun sendMail(to : String){
        val api_key : SystemConfig = systemConfig.findByOrigen("RESENDMAIL_API")
        val mailfrom : SystemConfig = systemConfig.findByOrigen("RESEND_FROM")

        val resend  = Resend(api_key.comando)

        val params = CreateEmailOptions.builder()
            .from(mailfrom.comando)
            .to(to)
            .subject("Send Mail")
            .html("<p>Send Mail</p>")
            .build()
        try {
            val data: CreateEmailResponse = resend.emails().send(params)
            println("correo enviado con id: ${data.id}")
        }catch (e:Exception){
            println("Error al enviar: ${e.message}")
        }
    }
}
