package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.imageio.ImageIO
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import org.springframework.beans.factory.annotation.Autowired
import com.comunicamosmas.api.repository.UserRepository
import com.comunicamosmas.api.security.SecurityUtils
import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.repository.IContratoHistoricoEstadoDao
import com.comunicamosmas.api.domain.ContratoHistoricoEstado
@Service
class GlobalService( 
    private val contratoHistoricoEstadoDao: IContratoHistoricoEstadoDao,
) {

    @Autowired
    private lateinit var userRepository: UserRepository

    val llave_internet_comentarios = "#$\"R)=k)C@$=HPOI"

    fun getCurrentUser(): String {

        return SecurityUtils.getCurrentUserLogin().orElseThrow {
            RuntimeException("No user is currently logged in")
        }
    }

    fun getCurrentUserID(): Long {
        // This method should return the current user's username or ID
        // For now, we will return a placeholder value
        val login = getCurrentUser()
        return userRepository.findOneByLogin(login)
            .map { it.id }
            .orElseThrow { RuntimeException("User not found") }
    }

    fun resizeImage(image: MultipartFile, width: Int, height: Int): ByteArrayInputStream  {
        // Implement image resizing logic here
        // This is a placeholder implementation
        val originalImage = ImageIO.read(image.inputStream)

        val resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH)
        val outputImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g2d = outputImage.createGraphics()
        g2d.drawImage(resizedImage, 0, 0, null)
        g2d.dispose()

        val baos = ByteArrayOutputStream()
        ImageIO.write(outputImage, "png", baos)

     return ByteArrayInputStream(baos.toByteArray())
    }

    fun get_letters_random(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    fun get_letters_random_numbers(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    fun getMD5Hash(input: String): String {
        val md = java.security.MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun changeStatusContrato(contrato:Contrato , estado:Long){

        var contratoHistorico = ContratoHistoricoEstado()
        contratoHistorico.idContrato = contrato.id
        contratoHistorico.idEstadoEstaba = contrato.estado
        contratoHistorico.idEstadoEntra = estado
        contratoHistorico.fechaf = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")).toLong()
        contratoHistorico.idServicio = contrato.idServicio
        contratoHistorico.idEmpresa = contrato.idEmpresa
        contratoHistorico.idCiudad = contrato.idCiudad

        contratoHistoricoEstadoDao.save(contratoHistorico)
    }
}