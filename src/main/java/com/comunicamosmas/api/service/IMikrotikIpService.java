package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.service.dto.ClassErrorDTO; 
import java.util.List;

public interface IMikrotikIpService {
    public void save(MikrotikIp mikrotikIp);

    public List<MikrotikIp> findAllBySegmentoIp(Long idSegmentoIp);

    public void recorrer(String[] segmento, Long id);

    public List<MikrotikIp> findAllBySegmentoIpStatus(Long idSegmentoIp);

    public MikrotikIp findById(Long id);

    public void updatedStatus(Long id);

    public ClassErrorDTO deleteByIdSegmento(Long id);

    public void deleteById(Long idIp);

    public void saveAll(List<MikrotikIp> mikrotikIp);

    public List<MikrotikIp> findByIdPool(Long idPool);
}
