package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.repository.IMikrotikHijoSimpleQueueDao;
import java.util.List;
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
	public List<MikrotikHijoSimpleQueue> findAllByidPadre(Integer idPadre) {
		
		return (List<MikrotikHijoSimpleQueue>)mikrotikHijoSimpleQueueDao.findAllByPadre(idPadre);
	}
}
