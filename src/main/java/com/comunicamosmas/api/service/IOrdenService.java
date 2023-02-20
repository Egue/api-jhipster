package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.dto.OrdenForInstalacionFindByIdOrdenDTO;
import com.comunicamosmas.api.service.dto.OrdenInstalacionDTO; 
import java.util.List;

public interface IOrdenService {
    //listar todos
    public List<Orden> findAll();

    //guardar
    public Orden save(Orden orden);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public Orden findById(Long id);

    public List<OrdenInstalacionDTO> ordenInstalacion();

    public OrdenForInstalacionFindByIdOrdenDTO ordenForInstalacionFindByIdOrden(Long id);

    public String findTelefonoByIdOrden(Long idOrden);

    public List<OrdenInstalacionDTO> getListFindBetwee(String valor1, String valor2);
}
