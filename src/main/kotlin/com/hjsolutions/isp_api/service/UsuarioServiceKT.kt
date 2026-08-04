package com.hjsolutions.isp_api.service

import com.comunicamosmas.api.domain.Usuario
import com.comunicamosmas.api.repository.IUsuarioDao
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UsuarioServiceKT(
    private val userRepository: IUsuarioDao
) {

    fun getUsuariosByCiudad(ciudad: Long , filter : String): Optional<List<Usuario>> {
        return  when (filter) {
        "all" -> userRepository.findByIdCiudadAndEstado(ciudad , 1L)
        else ->   userRepository.findByIdCiudadAndEstadoAndIdNivel(ciudad , 1L ,  3L)
        }
    }
}
