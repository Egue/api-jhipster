package com.comunicamosmas.api.service;

import java.util.List;

import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.dto.GrupoMailDTO;
import com.comunicamosmas.api.service.dto.NomenclaturaDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;

public interface ISystemConfigService {
    
    public void save(SystemConfig system);

    public List<SystemConfig> findAll();

    public void delete(Integer id);

    public SystemConfig findByOrigen(String origen);


    public List<ValorStringDTO> findTipoPqr();

    public GrupoMailDTO grupoMail();

    //nomenclatura
    public void saveNomenclatura(List<NomenclaturaDTO> nomenclaturaDTOs);

    public List<NomenclaturaDTO> listNomenclatura();
}
