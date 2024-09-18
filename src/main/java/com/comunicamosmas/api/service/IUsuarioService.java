package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Usuario;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.service.dto.userLoginDTO;

import java.util.List;

public interface IUsuarioService {
    //listar todos
    public List<Usuario> findAll();

    //guardar
    public Usuario save(Usuario usuario);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Usuario findById(Long id);

    public String findRolByUser(String username);

    public void updatePassword(Long id, String password);

    public List<userLoginDTO> findAllUsers();

    public List<userLoginDTO> findByName(String name);

    public List<ValorStringDTO> roles();

    public void newUser(Integer id, String role);

    public void initResetPassword(String login);

    public boolean finishResetPassword(String code , String newPassword);
}
