package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.OrdenInternetSsid;
import java.util.List;

public interface IOrdenInternetSsidService {
    //listar todos
    public List<OrdenInternetSsid> findAll();

    //guardar
    public OrdenInternetSsid save(OrdenInternetSsid ordenInternetSsid);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public OrdenInternetSsid findById(Long id);
}
