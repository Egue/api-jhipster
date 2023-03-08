package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.service.dto.SimpleQueueFindReusoDTO;

import java.util.List;

public interface IMikrotikPadreSimpleQueueService {
    public void save(MikrotikPadreSimpleQueue mikrotikPadreSimpleQueue);

    public MikrotikPadreSimpleQueue findByVelocidad(String namePadre, String velocidad, Long reuso, Long estacion);

    public MikrotikPadreSimpleQueue findByIdPlanAndReuso(Long idPlan, Long reuso, Long idEstacion);

    public Long newPadre(Long idTarifa, String name, String target, String comment, String limit, String max, Long idEstacion);

    public void updatedTargetReuso(String target, Long id);

    public MikrotikPadreSimpleQueue findById(Long id);
    
    public SimpleQueueFindReusoDTO simpleQueueInfoComponent(Long idContrato);
    
    public void eliminarTarget(Long idPadre, String ip);
}
