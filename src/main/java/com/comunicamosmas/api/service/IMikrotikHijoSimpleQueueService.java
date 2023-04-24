package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import java.util.List;

public interface IMikrotikHijoSimpleQueueService {
    public MikrotikHijoSimpleQueue save(MikrotikHijoSimpleQueue mikrotikHijoSimpleQueue);

    public List<MikrotikHijoSimpleQueue> findAll();

    public MikrotikHijoSimpleQueue findById(Long id);
    
    public List<MikrotikHijoSimpleQueue> findAllByidPadre(Integer idPadre);

    public MikrotikHijoSimpleQueue findByIdContrato(Long idContrato);

    public void crearHijo(Long idPadre, Long idIp, String limit, String max, Long name, Long contrato, String queue, String target);

    void deleteById(Long id);
}
