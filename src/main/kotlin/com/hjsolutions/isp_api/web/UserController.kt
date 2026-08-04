package com.hjsolutions.isp_api.web

import com.comunicamosmas.api.domain.Usuario
import com.hjsolutions.isp_api.service.UsuarioServiceKT
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/api/kt/user")
class UserController(
private val usuarioServiceKT : UsuarioServiceKT
) {
    @GetMapping("/ciudad/{ciudad}/{filter}")
    fun getuserByCiudad(@PathVariable ciudad: Long , @PathVariable filter:String): ResponseEntity<Optional<List<Usuario>>>{
        return ResponseEntity.ok(usuarioServiceKT.getUsuariosByCiudad(ciudad , filter))
    }
}
