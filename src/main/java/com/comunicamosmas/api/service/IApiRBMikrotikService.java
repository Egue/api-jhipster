package com.comunicamosmas.api.service;

import java.util.List;
import java.util.Map;

import com.comunicamosmas.api.domain.Estacion;

public interface IApiRBMikrotikService {

    public String sendCommando(String comando , Estacion estacion );

    public List<Map<String, String>> listSendCommand(String commando , Estacion estacion);
}
