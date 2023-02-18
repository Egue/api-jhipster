package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.InventarioArticuloItem;
import org.springframework.data.repository.CrudRepository;

public interface IInventarioArticuloItemDao extends CrudRepository<InventarioArticuloItem, Long> {}
