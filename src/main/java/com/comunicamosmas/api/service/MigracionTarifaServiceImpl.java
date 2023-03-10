package com.comunicamosmas.api.service;
 
import com.comunicamosmas.api.domain.MigracionTarifa; 
import com.comunicamosmas.api.repository.IMigracionTarifaDao;
import com.comunicamosmas.api.service.dto.MigracionTarifaFindContratoDTO;
import com.comunicamosmas.api.service.dto.MigracionTarifasInfoDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MigracionTarifaServiceImpl implements IMigracionTarifaService {

	@Autowired
	IMigracionTarifaDao migracionTarifaDao;
	
	@Override
	public List<MigracionTarifaFindContratoDTO> findAll(Long idContrato) {
		List<Object[]> result = migracionTarifaDao.findAllmigracionByContrato(idContrato);
		List<MigracionTarifaFindContratoDTO> migracionTarifa = new ArrayList<>();
		for(Object[] rs : result)
		{
			MigracionTarifaFindContratoDTO migracion = new MigracionTarifaFindContratoDTO();
			migracion.setIdMigracion((Integer) rs[0]);
			migracion.setIdContrato((Integer) rs[1]);
			migracion.setIdGeneralAntes((Integer) rs[2]);
			migracion.setIdPromoAntes((Integer) rs[3]);
			migracion.setIdGeneralNew((Integer) rs[4]);
			migracion.setIdPromoNew((Integer) rs[5]);
			migracion.setIdServicio((Integer) rs[6]);
			migracion.setIdEmpresa((Integer) rs[7]);
			migracion.setTarifaAnteriorGeneral((String) rs[8]);
			migracion.setTarifaAnteriorPromo((String) rs[9]);
			migracion.setTarifaNewGeneral((String) rs[10]);
			migracion.setTarifaNewPro((String) rs[11]);
			migracion.setUsuario((String) rs[12]);
			migracion.setJustificacion((String) rs[13]);
			migracionTarifa.add(migracion);
		}
		return migracionTarifa;
	}

	@Override
	public MigracionTarifa save(MigracionTarifa migracionTarifa) {
		
		MigracionTarifa save = migracionTarifaDao.save(migracionTarifa);
		
		return save;
	}

	@Override
	public Long deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MigracionTarifa findById(Long id) {
		
		return migracionTarifaDao.findById(id).orElse(null);
	}

	@Override
	public MigracionTarifasInfoDTO migracionTarifaInfo(Long idContrato) {
		List<Object[]> result = migracionTarifaDao.infoContratoMigracion(idContrato);
		//creamos para mappear
		MigracionTarifasInfoDTO object = new MigracionTarifasInfoDTO();
		//mapeamos
		for(Object[] rs : result)
		{
			//oobject
			
			object.setIdContrato((Integer) rs[0]);
			object.setNombreCliente((String) rs[1]);
			object.setDocumento((BigInteger) rs[2]); 
			object.setNombreTarifaPromo((String) rs[3]);
			object.setValorTarifaPromo((Integer) rs[4]);
			object.setVelocidadTarifaPromo((Integer) rs[5]);
			object.setNombreTarifaGeneral((String) rs[6]);
			object.setValorTarifaGeneral((Integer) rs[7]);
			object.setVelocidadTarifaGeneral((Integer) rs[8]);
			object.setNombreTecnologia((String) rs[9]);
			object.setIdServicio((Integer) rs[10]);
			object.setTipoCliente((String) rs[11]);
			object.setIdTecnolgia((Integer) rs[12]);
			object.setIdEmpresa((Integer) rs[13]);
			object.setIdCiudad((Integer) rs[14]);
			object.setIdCliente((Integer) rs[15]);
			object.setLugar((String) rs[16]);
			object.setIdTarifaPromo((Integer) rs[17]);
			object.setIdTarifa((Integer) rs[18]);
		 
			
			
		}
		return object;
	}

	@Override
	public MigracionTarifaFindContratoDTO migracionFindByContrato(Long idContrato) {
		List<Object[]> result = migracionTarifaDao.findMigraByIdContrato(idContrato);
		if(result != null)
		{
			MigracionTarifaFindContratoDTO migracion = new MigracionTarifaFindContratoDTO();
			for(Object[] rs : result)
			{
				migracion.setIdMigracion((Integer) rs[0]);
				migracion.setIdContrato((Integer) rs[1]);
				migracion.setIdGeneralAntes((Integer) rs[2]);
				migracion.setIdPromoAntes((Integer) rs[3]);
				migracion.setIdGeneralNew((Integer) rs[4]);
				migracion.setIdPromoNew((Integer) rs[5]);
				migracion.setIdServicio((Integer) rs[6]);
				migracion.setIdEmpresa((Integer) rs[7]);
				migracion.setTarifaAnteriorGeneral((String) rs[8]);
				migracion.setTarifaAnteriorPromo((String) rs[9]);
				migracion.setTarifaNewGeneral((String) rs[10]);
				migracion.setTarifaNewPro((String) rs[11]);
				migracion.setUsuario((String) rs[12]);
				migracion.setJustificacion((String) rs[13]);
				
			
			}
			return migracion;
		}else {
			return null;
		}
	}
 
}
