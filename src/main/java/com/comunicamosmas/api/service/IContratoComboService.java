package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.ContratoCombo;
import java.util.List;
import java.util.Optional;

public interface IContratoComboService {
    //listar todos
    public List<ContratoCombo> findAll();

    //guardar
    public ContratoCombo save(ContratoCombo contratoCombo);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public ContratoCombo findById(Long id);

    //
    public Optional<List<Object[]>> findByEmpresaAndIdCombo(Long empresa , Integer idCombo);
}
