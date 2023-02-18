package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikPool;
import com.comunicamosmas.api.repository.IMikrotikPoolDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikPoolServiceImpl implements IMikrotikPoolService {

    @Autowired
    private IMikrotikPoolDao mikrotikPoolDao;

    @Override
    public void save(MikrotikPool mikrotikPool) {
        mikrotikPoolDao.save(mikrotikPool);
    }

    @Override
    public List<MikrotikPool> findByIdEstacion(Long idEstacion) {
        // TODO Auto-generated method stub
        return (List<MikrotikPool>) mikrotikPoolDao.findByIdEstacion(idEstacion);
    }

    @Override
    public MikrotikPool findById(Long id) {
        // TODO Auto-generated method stub
        return mikrotikPoolDao.findById(id).orElse(null);
    }
}
