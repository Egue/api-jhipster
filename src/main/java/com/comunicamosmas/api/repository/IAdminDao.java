package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.Admin;
import org.springframework.data.repository.CrudRepository;

public interface IAdminDao extends CrudRepository<Admin, Long> {}
