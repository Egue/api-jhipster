package com.comunicamosmas.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.service.IApiRBMikrotikService;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;

@Service
public class ApiRBMikrotikServiceImpl implements IApiRBMikrotikService {

    ApiConnection apiConnection;

    private void connection(String user, String password, String ip, long port) {
        try {

            apiConnection = ApiConnection.connect(SocketFactory.getDefault(), ip, (int) port, 2000);

            apiConnection.login(user, password);

        } catch (MikrotikApiException e) {
            throw new ExceptionNullSql(new Date(), "Error de Conexión", e.getMessage());
        }

    }

    private void logout() {

        try {
            apiConnection.close();
        } catch (ApiConnectionException e) {
            throw new ExceptionNullSql(new Date(), "Error de Desconexión", e.getMessage());
        }
    }

    @Override
    public String sendCommando(String comando, Estacion estacion) {

        try {
            connection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());

            List<Map<String, String>> result = apiConnection.execute(comando);

            String id = null;

            this.logout();

            if (result != null) {
                for (Map<String, String> rs : result) {

                    id = rs.get(".id");
                }
            }

            return id;
        } catch (MikrotikApiException e) {

            throw new ExceptionNullSql(new Date(), "Error consultando en Rb", e.getMessage()+" -"+comando);
        }
    }

    @Override
    public List<Map<String, String>> listSendCommand(String commando, Estacion estacion) {

        try {
            connection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
            List<Map<String, String>> result = apiConnection.execute(commando);
            this.logout();
            return result;
        } catch (MikrotikApiException e) {
            // TODO: handle exception
            throw new ExceptionNullSql(new Date(), "Error ejecutando script en Rb", e.getMessage() + " -"+commando);
        }
    }

}
