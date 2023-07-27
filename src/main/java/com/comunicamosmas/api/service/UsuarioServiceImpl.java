package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.Usuario;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IUsuarioDao;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.service.dto.userLoginDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    IUsuarioDao usuarioDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public void updatePassword(Long id, String password) {

        try {

            String encryptPassword = passwordEncoder.encode(password);

            Optional<Usuario> user = usuarioDao.findById(id);

            if (user.isPresent()) {
                Usuario usuario = user.get();

                usuario.setPasswordDos(encryptPassword);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String timestamp = LocalDateTime.now().format(formatter);

                usuario.setLastModifiedDate(timestamp);

                usuarioDao.save(usuario);

            } else {

            }
        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Cambio de contraseña", e.getMessage());
        }

    }

    @Override
    public List<userLoginDTO> findAllUsers() {

        Optional<List<Object[]>> result = usuarioDao.findAllUsers();

        List<userLoginDTO> users = result.map(resp -> {
            List<userLoginDTO> listUserDtos = new ArrayList<>();

            for (Object[] rs : resp) {
                userLoginDTO obj = new userLoginDTO();
                obj.setId((Long) Long.parseLong(rs[0].toString()));
                obj.setLogin((String) rs[1]);
                obj.setFirtsName((String) rs[2]);
                obj.setLastName((String) rs[3]);
                obj.setEmail((String) rs[4]);
                if ((Integer) rs[5] == 1) {
                    obj.setActivated(true);
                } else {
                    obj.setActivated(false);
                }
                obj.setLangKey((String) rs[6]);
                obj.setImageUrl((String) rs[7]);
                obj.setRol((String) rs[8]);
                listUserDtos.add(obj);
            }

            return listUserDtos;
        }).orElse(new ArrayList<>());

        return users;
    }

    @Override
    public List<userLoginDTO> findByName(String name) {
         Optional<List<Object[]>> result = usuarioDao.findByQuery(name);

         List<userLoginDTO> users = result.map( resp -> {
                List<userLoginDTO> user = new ArrayList<>();

                    for(Object[] rs : resp)
                    {
                        userLoginDTO obj = new userLoginDTO();
                        obj.setId((Long) Long.parseLong(rs[0].toString()));
                        obj.setLogin((String) rs[1]);
                        user.add(obj);
                    }

                return user;
         }).orElse(new ArrayList<>());

         return users;
    }

    @Override
    public List<ValorStringDTO> roles() {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = usuarioDao.findRoleAll();

        List<ValorStringDTO> roles = result.map(resp->{
            List<ValorStringDTO> role= new ArrayList<>();

                for(Object[] rs : resp)
                { ValorStringDTO obj = new ValorStringDTO();
                     
                    obj.setValor((String) rs[0]);
                    role.add(obj);
                }

            return role;
        }).orElse(new ArrayList<>());
         
        return roles;
    }

    @Transactional
    public void newUser(Integer id, String role) {
        // TODO Auto-generated method stub
        try {
                String sql = "INSERT INTO jhi_user_authority(user_id , authority_name) values(:id , :role)";

                em.createNativeQuery(sql)
                    .setParameter("id", id)
                    .setParameter("role", role)
                    .executeUpdate();

        } catch (Exception e) {

            throw new  ExceptionNullSql(new Date(), "Error insertando datos ", e.getMessage());
        }
    }
}
