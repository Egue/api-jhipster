package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.repository.IMikrotikHijoSimpleQueueDao;
import com.comunicamosmas.api.service.dto.MikrotikHijoSimpleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikHijoSimpleQueueServiceImpl implements IMikrotikHijoSimpleQueueService {

    @Autowired
    IMikrotikHijoSimpleQueueDao mikrotikHijoSimpleQueueDao;

    @Override
    public MikrotikHijoSimpleQueue save(MikrotikHijoSimpleQueue mikrotikHijoSimpleQueue) {
        return mikrotikHijoSimpleQueueDao.save(mikrotikHijoSimpleQueue);
    }

    @Override
    public List<MikrotikHijoSimpleQueue> findAll() {
        // TODO Auto-generated method stub
        return (List<MikrotikHijoSimpleQueue>) mikrotikHijoSimpleQueueDao.findAll();
    }

    @Override
    public MikrotikHijoSimpleQueue findById(Long id) {
        // TODO Auto-generated method stub
        return mikrotikHijoSimpleQueueDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        mikrotikHijoSimpleQueueDao.deleteById(id);
    }

    @Override
    public void crearHijo(Long idPadre, Long idIp, String limit, String max, Long name, Long contrato, String queue, String target) {
        MikrotikHijoSimpleQueue hijo = new MikrotikHijoSimpleQueue();
        hijo.setIdPadre(idPadre);
        hijo.setIdIp(idIp);
        hijo.setLimitAt(limit);
        hijo.setMaxLimit(max);
        String cus = name.toString();
        hijo.setName(cus);
        hijo.setIdContrato(contrato);
        hijo.setQueue(queue);
        hijo.setTarget(target);
        mikrotikHijoSimpleQueueDao.save(hijo);
    }

    @Override
    public MikrotikHijoSimpleQueue findByIdContrato(Long idContrato) {
        return mikrotikHijoSimpleQueueDao.findByIdContrato(idContrato);
    }

	@Override
	public List<MikrotikHijoSimpleDTO> findAllByidPadre(Integer idPadre) {
        Optional<List<Object[]>> result = mikrotikHijoSimpleQueueDao.findAllByPadre(idPadre);

        List<MikrotikHijoSimpleDTO> hijos = result.map( resp -> {

            List<MikrotikHijoSimpleDTO> hijo = new ArrayList<>();
            for(Object[] rs : resp)
            {
                MikrotikHijoSimpleDTO obj = new MikrotikHijoSimpleDTO();
                obj.setId((String) rs[0].toString());
                obj.setIdPadre((String) rs[1].toString());
                obj.setIdIp((String) rs[2].toString());
                obj.setLimitAt((String) rs[3]);
                obj.setMaxLimit((String) rs[4]);
                obj.setName((String) rs[5]);
                obj.setIdContrato((String) rs[6].toString());
                obj.setQueue((String)rs[7]);
                obj.setTarget((String) rs[8]);
                obj.setEstado((String) rs[9].toString());
                hijo.add(obj);
            }

            return hijo;
        }).orElse(new ArrayList<>());
        
		
		return hijos;
	}
}
