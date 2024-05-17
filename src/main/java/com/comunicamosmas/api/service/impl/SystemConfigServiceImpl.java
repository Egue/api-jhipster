package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.record.ObjRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.GrupoMailDTO;
import com.comunicamosmas.api.service.dto.NomenclaturaDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

    @Override
    public void saveNomenclatura(List<NomenclaturaDTO> nomenclaturaDTOs){
        // TODO Auto-generated method stub
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(nomenclaturaDTOs);

            SystemConfig system = new SystemConfig();

            system.setOrigen("nomenclatura");

            system.setComando(json);

            systemDao.save(system);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public List<NomenclaturaDTO> listNomenclatura() {
        // TODO Auto-generated method stub 
        SystemConfig result = systemDao.findByOrigen("nomenclatura");

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(result.getComando(), new TypeReference<List<NomenclaturaDTO>>(){});
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    

}
