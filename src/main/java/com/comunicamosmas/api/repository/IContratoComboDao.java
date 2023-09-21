package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.ContratoCombo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IContratoComboDao extends CrudRepository<ContratoCombo, Long> {

    @Query(value="SELECT contratos_combos.id_contrato  \n" + //
            "\t from contratos_combos WHERE contratos_combos.id_empresa = :empresa and contratos_combos.combo = :idCombo", nativeQuery=true)
    public Optional<List<Object[]>> findByEmpresaAndIdCombo(@Param("empresa") Long empresa , @Param("idCombo") Integer idCombo);
}
