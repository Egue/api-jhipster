package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Usuario;
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
}
