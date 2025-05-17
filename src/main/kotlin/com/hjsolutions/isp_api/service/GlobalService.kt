package com.hjsolutions.isp_api.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.imageio.ImageIO
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@Service
class GlobalService( 
) {

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
}