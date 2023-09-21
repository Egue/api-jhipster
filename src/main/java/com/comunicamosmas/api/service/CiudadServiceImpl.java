package com.comunicamosmas.api.service;
  
import com.comunicamosmas.api.domain.Ciudad; 
import com.comunicamosmas.api.repository.ICiudadDao;
import com.comunicamosmas.api.service.dto.CiudadesDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadServiceImpl implements ICiudadService {

    @Autowired
    ICiudadDao ciudadDao;

    @Override
    public List<Ciudad> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Ciudad save(Ciudad ciudad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Ciudad findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<CiudadesDTO> findByUser(Long id) {
        Optional<List<Object[]>> result = ciudadDao.findByIdUser(id);

        List<CiudadesDTO> ciudades = result.map(resp -> {
            List<CiudadesDTO> ciudad = new ArrayList<>();

            for(Object[] rs : resp)
            {
                CiudadesDTO obj = new CiudadesDTO();

                obj.setId((Integer) rs[0]);
                obj.setNombre((String) rs[1]);

                ciudad.add( obj);
            }

            return ciudad;
        }).orElse(new ArrayList<>());

        return ciudades;
    }
 
}
