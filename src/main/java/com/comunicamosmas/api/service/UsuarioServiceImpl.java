package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.Usuario;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IUsuarioDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    IUsuarioDao usuarioDao;

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Usuario findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String findRolByUser(String username) {
        // TODO Auto-generated method stub
        return usuarioDao.findRolByUsername(username);
    }
}
