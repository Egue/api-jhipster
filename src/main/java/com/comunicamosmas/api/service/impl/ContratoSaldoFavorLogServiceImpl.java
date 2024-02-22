package com.comunicamosmas.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.ContratoSaldoFavorLog;
import com.comunicamosmas.api.repository.IContratoSaldoFavorLogDao;
import com.comunicamosmas.api.service.IContratoSaldoFavorLogService;
import com.comunicamosmas.api.service.IContratoService;

@Service
public class ContratoSaldoFavorLogServiceImpl implements IContratoSaldoFavorLogService{

    @Autowired
    IContratoSaldoFavorLogDao saLogDao;

    @Autowired
    IContratoService contratoService;

    @Override
    public List<ContratoSaldoFavorLog> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void save(ContratoSaldoFavorLog contratoSaldoFavorLog) {
        saLogDao.save(contratoSaldoFavorLog);
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public ContratoSaldoFavorLog findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void addSaldoBySupergiros(Contrato contrato, Float valor , String turno , String fecha) {
        // TODO Auto-generated method stub
        ContratoSaldoFavorLog log = new ContratoSaldoFavorLog();

        log.setIdContrato(contrato.getId());
        log.setIdCajero(2L);
        log.setValor(valor.longValue());
        log.setTipo(1L);
        log.setDetalle("Registro de saldo durante pago - Turno " + turno);
        log.setFechaf(fecha);
        log.setIdMedioPago(32L);
        log.setIdCiudad(contrato.getIdCiudad());
        log.setIdEmpresa(contrato.getIdEmpresa());
        log.setIdDeuda(0L);
        log.setIdServicio(contrato.getIdServicio());
        log.setLugar(contrato.getGrupo());
        
        saLogDao.save(log);
        this.contratoService.updateSaldoFavor(contrato, valor);

    }
    
}
