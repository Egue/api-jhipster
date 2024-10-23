package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ClausulaPermanencia;
import java.util.List;

public interface IClausulaPermanenciaService {
    //listar todos
    public List<ClausulaPermanencia> findAll();

    //guardar
    public ClausulaPermanencia save(ClausulaPermanencia clausulaPermanencia);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ClausulaPermanencia findById(Long id);

    public ClausulaPermanencia findByIdContrato(Long idContrato);
}
