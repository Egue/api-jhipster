package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.SmsEnvio;
import org.springframework.data.repository.CrudRepository;

public interface ISmsEnvioDao extends CrudRepository<SmsEnvio, Long> {}
