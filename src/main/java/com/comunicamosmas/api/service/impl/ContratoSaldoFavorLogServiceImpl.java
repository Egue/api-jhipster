package com.comunicamosmas.api.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.ContratoSaldoFavorLog;
import com.comunicamosmas.api.repository.IContratoSaldoFavorLogDao;
import com.comunicamosmas.api.service.IContratoSaldoFavorLogService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.dto.ReporteMediosPagosDTO;

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

    @Override
    public List<ReporteMediosPagosDTO> findByMedioPago(List<Integer> payments, String first, String last) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = saLogDao.findByMedioPago(payments, first, last);
        List<ReporteMediosPagosDTO> saldo = result.map(resp -> resp.stream().map(rs->{
            ReporteMediosPagosDTO obj = new ReporteMediosPagosDTO();
            obj.setCodigo((int) rs[0]);
            obj.setRc((int) rs[1]);
            obj.setCiudad((String) rs[2]);
            obj.setServicio((String) rs[3]);
            obj.setCliente((String) rs[4]);
            obj.setCajero((String) rs[5]);
            BigInteger valor = (BigInteger) rs[6]; 
            obj.setValor((float) valor.floatValue());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String timestampAsString = dateFormat.format(rs[7]);
            obj.setMarca(timestampAsString);
            obj.setOrigen((String) rs[8]);
            obj.setContrato((int) rs[9]);
            obj.setPayments((String) rs[10]);

            return obj;
        }).collect(Collectors.toList())).orElse(new ArrayList<>());
       //return saLogDao.findByMedioPago(payments, first, last);

       return saldo;
    }
    
}
