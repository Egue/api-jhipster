package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM usuarios usr WHERE usr.mailnotifica = :email LIMIT 0,1 ", nativeQuery = true)
    Optional<Usuario> findByOneEmail(String email);

    @Query(
        value = "SELECT authority_name FROM jhi_user_authority jua INNER JOIN usuarios usu on " +
        "usu.id_usuario = jua.user_id WHERE usu.nick=:username",
        nativeQuery = true
    )
    public String findRolByUsername(@Param(value = "username") String username);
}
