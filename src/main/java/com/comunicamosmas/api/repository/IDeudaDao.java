package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Deuda;
import org.springframework.data.repository.CrudRepository;

public interface IDeudaDao extends CrudRepository<Deuda, Long> {}
