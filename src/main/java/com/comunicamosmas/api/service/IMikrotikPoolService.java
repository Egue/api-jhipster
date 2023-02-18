package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikPool;
import java.util.List;

public interface IMikrotikPoolService {
    public void save(MikrotikPool mikrotikPool);

    public List<MikrotikPool> findByIdEstacion(Long idEstacion);

    public MikrotikPool findById(Long id);
}
