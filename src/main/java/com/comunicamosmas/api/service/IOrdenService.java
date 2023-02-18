package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.web.rest.vm.OrdenForInstalacionFindByIdOrden;
import com.comunicamosmas.api.web.rest.vm.OrdenInstalacion;
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

    public List<OrdenInstalacion> ordenInstalacion();

    public OrdenForInstalacionFindByIdOrden ordenForInstalacionFindByIdOrden(Long id);

    public String findTelefonoByIdOrden(Long idOrden);

    public List<OrdenInstalacion> getListFindBetwee(String valor1, String valor2);
}
