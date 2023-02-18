package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.web.rest.vm.ClassError;
import java.util.List;
import java.util.Map;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;

public interface IMikrotikService {
    //pruebas
    public void conection(String user, String password, String ip, long port) throws MikrotikApiException;

    public void logout() throws ApiConnectionException;

    public void createPadre(MikrotikTarifaReuso mikrotikTarifaReuso) throws MikrotikApiException;

    public ClassError instalacion(Long idPlan, Long idEstacion, Long idIp, Long contrato, Long tecnologia);

    public List<Map<String, String>> queueSimpleFindByContrato(Long idContrato, Long idEstacion) throws MikrotikApiException;

    public List<Map<String, String>> pppProfiles(Long idEstacion) throws MikrotikApiException;

    public ClassError pppSecretsNew(Long idAppMaster, Long idContrato, Long idEstacion, String name, String nameSecrect, String pass);

    public void test(Long idEstacion, String ip, Long idContrato) throws MikrotikApiException;

    //nuevas instancias
    //crea solo el padre
    public MikrotikPadreSimpleQueue padreQueuesimple(Long idPlan, Long idEstacion, Long idIp) throws MikrotikApiException;

    //crea el secrect en el ppoe
    public WinmaxPass pppSecrect(Long idContrato, Long idEstacion, String profile, String nameSecrect, String pass)
        throws MikrotikApiException;

    //crea el hijo
    public MikrotikHijoSimpleQueue hijoQueueSimple(Long idPlan, Long idIp, Long idPadre, Long idContrato, Long idEstacion)
        throws MikrotikApiException;

    //actualiza el target del hijo creado
    public MikrotikPadreSimpleQueue updatedTargetPadreInRB(Long idEstacion, Long idPadre) throws MikrotikApiException;

    //actualiza remote adddes
    public WinmaxPass updatedRemoteAddress(Long id, Long idEstacion, Long idIp) throws MikrotikApiException;
}
