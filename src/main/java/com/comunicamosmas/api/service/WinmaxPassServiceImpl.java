package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.repository.IWinmaxPassDao;
import com.comunicamosmas.api.service.dto.ListWinmaxPassDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WinmaxPassServiceImpl implements IWinmaxPassService {

    @Autowired
    IWinmaxPassDao winmaxDao;

    @Override
    public List<WinmaxPass> findAll() {
        // TODO Auto-generated method stub
        return (List<WinmaxPass>) winmaxDao.findAll();
    }

    @Override
    public WinmaxPass save(WinmaxPass winmaxPass) {
        return winmaxDao.save(winmaxPass);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        winmaxDao.deleteById(id);
    }

    @Override
    public WinmaxPass findById(Long id) {
        // TODO Auto-generated method stub
        return winmaxDao.findById(id).orElse(null);
    }

    @Override
    public Long countFindByIdContrato(Long idContrato) {
        // TODO Auto-generated method stub
        return winmaxDao.countFindByIdContrato(idContrato);
    }

    @Override
    public WinmaxPass findByIdContrato(Long idContrato) {
        // TODO Auto-generated method stub
        return winmaxDao.findByIdContrato(idContrato);
    }

	@Override
	public List<ListWinmaxPassDTO> findByIdEstacionWithDatos(Long idEstacion) {
		
		List<Object[]> listDTO = winmaxDao.findByIdEstacionConDatos(idEstacion);
		List<ListWinmaxPassDTO> listWinmaxDTO = new ArrayList<>();
		
		
		for(Object[] row : listDTO)
		{
			ListWinmaxPassDTO winmaxDTO = new ListWinmaxPassDTO();
			winmaxDTO.setIdContrato((Integer) row[0]);
			winmaxDTO.setContratoEstado((String) row[1]);
			winmaxDTO.setTipoCliente((String) row[2]);
			winmaxDTO.setNombreCliente((String) row[3]);
			winmaxDTO.setUsuario((String) row[4]);
			winmaxDTO.setPass((String) row[5]);
			winmaxDTO.setMarca((Timestamp) row[6]);
			winmaxDTO.setNombreTecnologia((String) row[7]);
			winmaxDTO.setVelocidad((Integer) row[8]);
			winmaxDTO.setNombreTarifa((String) row[9]);
			winmaxDTO.setValor((Integer) row[10]);
			winmaxDTO.setCodigoMikrotik((String) row[11]);
			
			listWinmaxDTO.add(winmaxDTO);
		}
		return listWinmaxDTO;
	}
}
