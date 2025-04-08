package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import java.util.List;

public interface IMikrotikTarifaReusoService {
    public void save(MikrotikTarifaReuso mikrotikTarifaReuso);

    public MikrotikTarifaReuso updated(MikrotikTarifaReuso tarifaReuso);

    public MikrotikTarifaReuso findById(Long id);

    public List<MikrotikTarifaReuso> findAll();

    public void delete(Long id);

    public List<MikrotikTarifaReuso> findByIdEstacion(Long idEstacion);

    public List<MikrotikTarifaReuso> findByEstado(String estado);

    public List<MikrotikTarifaReuso> findByLikeEstado(String name, Long idEstacion);

    public List<MikrotikTarifaReuso> findByidTipoAndIdEstacionAndName(Long tipo, Long idEstacion, String name);
}
