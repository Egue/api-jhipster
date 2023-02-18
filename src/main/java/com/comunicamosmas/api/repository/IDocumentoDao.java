package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Documento;
import org.springframework.data.repository.CrudRepository;

public interface IDocumentoDao extends CrudRepository<Documento, Long> {}
