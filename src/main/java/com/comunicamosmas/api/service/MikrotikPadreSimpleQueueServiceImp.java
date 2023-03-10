package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikSegmentoIp;
import com.comunicamosmas.api.repository.IMikrotikPadreSimpleQueueDao;
import com.comunicamosmas.api.repository.IMikrotikSegmentoIpDao;
import com.comunicamosmas.api.service.dto.SimpleQueueFindReusoDTO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MikrotikPadreSimpleQueueServiceImp implements IMikrotikPadreSimpleQueueService {

    @Autowired
    IMikrotikPadreSimpleQueueDao mikrotikPadreSimpleDao;

    @Override
    public void save(MikrotikPadreSimpleQueue mikrotikPadreSimpleQueue) {
        mikrotikPadreSimpleDao.save(mikrotikPadreSimpleQueue);
    }

    @Override
    public MikrotikPadreSimpleQueue findByVelocidad(String namePadre, String velocidad, Long reuso, Long estacion) {
        // TODO Auto-generated method stub
        return mikrotikPadreSimpleDao.findByVelocidad(namePadre, velocidad, reuso, estacion);
    }

    @Override
    public Long newPadre(Long idTarifa, String name, String target, String comment, String limit, String max, Long idEstacion) {
        MikrotikPadreSimpleQueue padreSimpleQueue = new MikrotikPadreSimpleQueue();
        padreSimpleQueue.setIdTarifaReuso(idTarifa);
        padreSimpleQueue.setName(name);
        padreSimpleQueue.setTarget(target);
        padreSimpleQueue.setComment(comment);
        padreSimpleQueue.setLimitAt(limit);
        padreSimpleQueue.setMaxLimit(max);
        padreSimpleQueue.setIdEstacion(idEstacion);
        padreSimpleQueue.setReuso(1L);
        mikrotikPadreSimpleDao.save(padreSimpleQueue);

        return padreSimpleQueue.getId();
    }

    @Override
    public void updatedTargetReuso(String target, Long id) {
        MikrotikPadreSimpleQueue padre = mikrotikPadreSimpleDao.findById(id).orElse(null);

        String targetAll = padre.getTarget() + "," + target;
        Long reusoMas = padre.getReuso() + 1L;

        padre.setTarget(targetAll);

        padre.setReuso(reusoMas);

        mikrotikPadreSimpleDao.save(padre);
    }

    @Override
    public MikrotikPadreSimpleQueue findById(Long id) {
        // TODO Auto-generated method stub
        return mikrotikPadreSimpleDao.findById(id).orElse(null);
    }

    @Override
    public MikrotikPadreSimpleQueue findByIdPlanAndReuso(Long idPlan, Long reuso, Long idEstacion) {
        return mikrotikPadreSimpleDao.findByIdPlanAndReuso(idPlan, reuso, idEstacion);
    }

	@Override
	public SimpleQueueFindReusoDTO simpleQueueInfoComponent(Long idContrato) {
		
		List<Object[]> result = mikrotikPadreSimpleDao.simpleQueueInfoComponent(idContrato);
		SimpleQueueFindReusoDTO simpleQueue = new SimpleQueueFindReusoDTO();
		
		for(Object[] rs : result)
		{
			simpleQueue.setIdContrato((Integer) rs[0]);
			simpleQueue.setIdEstacion((Integer) rs[1]);
			simpleQueue.setVelocidad((Integer) rs[2]);
			simpleQueue.setNombreTecnologia((String) rs[3]);
			simpleQueue.setIdWinmaxPass((Integer) rs[4]);
		}
		return simpleQueue;
	}

	@Override
	public void eliminarTarget(Long idPadre, String ip) {
		MikrotikPadreSimpleQueue result = mikrotikPadreSimpleDao.findById(idPadre).orElse(null);
		Long reduccion = result.getReuso() - 1;
		result.setReuso(reduccion);
		//pendiente eliminar de una target
		
	}
}
