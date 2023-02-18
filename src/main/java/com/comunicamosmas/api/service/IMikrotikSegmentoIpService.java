package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikSegmentoIp;
import com.comunicamosmas.api.web.rest.vm.ISegmentoWithPool;
import java.util.List;

public interface IMikrotikSegmentoIpService {
    public MikrotikSegmentoIp findById(Long id);

    public List<MikrotikSegmentoIp> findByIdEstacion(Long id);

    public void save(MikrotikSegmentoIp mikrotikSegmentoIp);

    public String[] splitSegment(MikrotikSegmentoIp mikrotikSegmentoIp);

    public String[] validarSegmento(MikrotikSegmentoIp mikrotikSegmentoIp);

    public int countFindByIdEstacionAndName(Long idEstacion, String segmento);

    public int countDFindIpByPublic(String ipPublic, String segmento);

    public List<MikrotikSegmentoIp> findByIdPool(Long idPool);

    public List<ISegmentoWithPool> findByidPoolAndEstado(Long idPool);

    public void deleteById(Long id);
}
