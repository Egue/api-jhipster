package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Usuario;

import java.util.List;
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

    @Query(value="SELECT usuarios.id_usuario, usuarios.nick, usuarios.nombre,\n" + //
            "usuarios.apellidos, usuarios.mailnotifica, usuarios.estado,\n" + //
            "usuarios.password_dos, usuarios.avatar, jhi.authority_name\n" + //
            " FROM usuarios\n" + //
            "inner join jhi_user_authority jhi on jhi.user_id = usuarios.id_usuario " , nativeQuery = true)
    public Optional<List<Object[]>> findAllUsers();

    @Query(value="SELECT usuarios.id_usuario, concat(usuarios.nombre,\" \", usuarios.apellidos) as usuario\n" + //
            " FROM usuarios\n" + //
            "WHERE usuarios.nombre like concat('%', :query ,'%')" , nativeQuery = true)
    public Optional<List<Object[]>> findByQuery(@Param("query") String query);

    @Query(value="SELECT * FROM jhi_authority", nativeQuery = true)
    public Optional<List<Object[]>> findRoleAll();

    public Optional<Usuario> findByNick(@Param("nick") String nick);

    public Optional<Usuario> findOneByPushover(@Param("pushover") String pushover);

     
}
