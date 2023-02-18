package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Ciudad;
import org.springframework.data.repository.CrudRepository;

public interface ICiudadDao extends CrudRepository<Ciudad, Long> {}
