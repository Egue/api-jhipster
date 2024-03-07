package com.comunicamosmas.api.service;

import java.util.List;
import java.util.Optional;

import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.service.dto.ClassErrorDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimplePadreDTO;
import com.comunicamosmas.api.service.dto.SegmentoDTO; 

public interface IMikrotikIpService {
    public void save(MikrotikIp mikrotikIp);

    public List<MikrotikIp> findAllBySegmentoIp(Long idSegmentoIp);

    public void recorrer(String[] segmento, Long id);

    public List<MikrotikIp> findAllBySegmentoIpStatus(Long idSegmentoIp);

    public MikrotikIp findById(Long id);

    public MikrotikIp updatedStatus(SegmentoDTO id);

    public ClassErrorDTO deleteByIdSegmento(Long id);

    public void deleteById(Long idIp);

    public void saveAll(List<MikrotikIp> mikrotikIp);

    public List<MikrotikIp>  findByIdPool(Long idPool);
}
