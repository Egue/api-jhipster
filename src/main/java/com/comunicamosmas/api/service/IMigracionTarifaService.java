package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MigracionTarifa;
import com.comunicamosmas.api.service.dto.MigracionTarifaFindContratoDTO;
import com.comunicamosmas.api.service.dto.MigracionTarifasInfoDTO;

import java.util.List;

public interface IMigracionTarifaService {
    //listar todos
    public List<MigracionTarifa> findAll();

    //guardar
    public MigracionTarifa save(MigracionTarifa migracionTarifa);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public MigracionTarifa findById(Long id);
    
    public MigracionTarifasInfoDTO migracionTarifaInfo(Long idContrato);
    
    public MigracionTarifaFindContratoDTO migracionFindByContrato(Long idContrato);
}
