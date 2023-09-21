package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.CacheContratoSaldo;
import com.comunicamosmas.api.repository.ICacheContratoSaldoDao;
import com.comunicamosmas.api.service.dto.ReciboCajaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheContratoSaldoServiceImpl implements ICacheContratoSaldoService {

    @Autowired
    ICacheContratoSaldoDao cacheContratoSaldoDao;

    @Override
    public List<CacheContratoSaldo> findAll() {
        // TODO Auto-generated method stub
        return (List<CacheContratoSaldo>) cacheContratoSaldoDao.findAll();
    }

    @Override
    public void save(CacheContratoSaldo cacheContratoSaldo) {
        // TODO Auto-generated method stub
        cacheContratoSaldoDao.save(cacheContratoSaldo);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public CacheContratoSaldo findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ReciboCajaDTO> reporte_saldo_favor(List<Integer> ciudad, Integer fecha_inicio, Integer fecha_final,
            List<String> origen) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = cacheContratoSaldoDao.reporteReciboCaja(ciudad , fecha_inicio, fecha_final , origen);

        List<ReciboCajaDTO> saldo = result.map(resp -> {
            List<ReciboCajaDTO> favor = new ArrayList<>();
            for(Object[] rs : resp)
            {
                ReciboCajaDTO obj = new ReciboCajaDTO();
                //armar la lista
                obj.setRc((String) rs[0].toString());
                obj.setMarca((String) rs[1].toString());
                obj.setTipo("Sal Fav");
                obj.setContrato((String) rs[2].toString());
                obj.setCliente((String) rs[3]);
                obj.setEstrato((String) rs[4].toString());
                obj.setID((String) rs[5].toString());
                obj.setServicio((String) rs[6]);
                obj.setValor((String) rs[7].toString());
                obj.setM_Pago((String) rs[8]);
                obj.setComprobante((String) rs[9].toString());
                obj.setCajero((String) rs[10]);
                String grupo =  rs[11].toString().equals("B") ? "**" : "";
                obj.setGrupo(grupo);
                obj.setBarrio((String) rs[12]);

                favor.add(obj);
            }

            return favor;

        }).orElse(new ArrayList<>());


        return saldo;
    }
}
