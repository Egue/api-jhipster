package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.GrupoMailDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Autowired
    ISystemConfigDao systemDao;

    @Override
    public void save(SystemConfig system) {
        // TODO Auto-generated method stub
        systemDao.save(system);
    }

    @Override
    public List<SystemConfig> findAll() {
        // TODO Auto-generated method stub
        return (List<SystemConfig>) systemDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        systemDao.deleteById(id);
    }

    @Override
    public SystemConfig findByOrigen(String origen) {
        // TODO Auto-generated method stub
        return (SystemConfig) systemDao.findByOrigen(origen);
    }

    @Override
    public List<ValorStringDTO> findTipoPqr() {

        Optional<Object[]> result = systemDao.findTipoPqr();

        List<ValorStringDTO> tipo = result.map(rs -> {

            List<ValorStringDTO> tipoList = new ArrayList<>();

            for (Object res : rs) {
                String[] partes = ((String) res).split(",");
                 
                for (String val : partes) {
                    ValorStringDTO obj = new ValorStringDTO();

                    obj.setValor(val);

                    tipoList.add(obj);
                }
            }
            return tipoList;            

        }).orElse(new ArrayList<>());
 

        return tipo;

    }

    @Override
    public GrupoMailDTO grupoMail() {
        // TODO Auto-generated method stub
        SystemConfig result = systemDao.findByOrigen("user_mail_retiros");
        
        try {
             ObjectMapper objetMapper = new ObjectMapper();

             GrupoMailDTO group = objetMapper.readValue(result.getComando() , GrupoMailDTO.class);

             return group;

        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Convirtiendo Mail", e.getMessage());
        }

 
         
    }

}
