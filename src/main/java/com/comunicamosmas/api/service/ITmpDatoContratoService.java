package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.TmpDatoContrato;
import java.util.List;

public interface ITmpDatoContratoService {
    //listar todos
    public List<TmpDatoContrato> findAll();

    //guardar
    public TmpDatoContrato save(TmpDatoContrato tmpDatoContrato);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public TmpDatoContrato findById(Long id);
}
