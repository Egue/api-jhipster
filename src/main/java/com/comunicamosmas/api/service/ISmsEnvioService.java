package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.SmsEnvio;
import java.util.List;

public interface ISmsEnvioService {
    //listar todos
    public List<SmsEnvio> findAll();

    //guardar
    public SmsEnvio save(SmsEnvio smsEnvio);

    //eliminar
    public Long deleteById(Long id);

    //buscar por id
    public SmsEnvio findById(Long id);
}
