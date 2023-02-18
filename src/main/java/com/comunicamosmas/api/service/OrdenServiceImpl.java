package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Admin;
import com.comunicamosmas.api.domain.EnumOrdenInstalacion;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.repository.IAdminDao;
import com.comunicamosmas.api.repository.IOrdenDao;
import com.comunicamosmas.api.web.rest.vm.OrdenForInstalacionFindByIdOrden;
import com.comunicamosmas.api.web.rest.vm.OrdenInstalacion;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    IOrdenDao ordenDao;

    @Override
    public List<Orden> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Orden save(Orden orden) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Orden findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrdenInstalacion> ordenInstalacion() {
        return (List<OrdenInstalacion>) ordenDao.ordenInstalacion();
    }

    @Override
    public OrdenForInstalacionFindByIdOrden ordenForInstalacionFindByIdOrden(Long id) {
        // TODO Auto-generated method stub
        return (OrdenForInstalacionFindByIdOrden) ordenDao.ordenForInstalacionFindByIdOrden(id);
    }

    @Override
    public String findTelefonoByIdOrden(Long idOrden) {
        // TODO Auto-generated method stub
        return ordenDao.findTelefonoByIdOrden(idOrden);
    }

    @Override
    public List<OrdenInstalacion> getListFindBetwee(String valor1, String valor2) {
        // TODO Auto-generated method stub
        return (List<OrdenInstalacion>) ordenDao.getListFindBetwee(valor1, valor2);
    }
}
