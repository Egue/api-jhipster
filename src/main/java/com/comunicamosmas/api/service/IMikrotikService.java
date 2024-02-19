package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.service.dto.ClassErrorDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPActiveDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPProfileDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPSecretDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimpleDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimplePadreDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;

import java.util.List;
import java.util.Map;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
 
public interface IMikrotikService {
    //pruebas
    public void conection(String user, String password, String ip, long port) throws MikrotikApiException;

    public void logout() throws ApiConnectionException;

    public void createPadre(MikrotikTarifaReuso mikrotikTarifaReuso) throws MikrotikApiException;

    public ClassErrorDTO instalacion(Long idPlan, Long idEstacion, Long idIp, Long contrato, Long tecnologia);

    public List<Map<String, String>> queueSimpleFindByContrato(Long idContrato, Long idEstacion) throws MikrotikApiException;

    public List<Map<String, String>> pppProfiles(Long idEstacion) throws MikrotikApiException;

    public ClassErrorDTO pppSecretsNew(Long idAppMaster, Long idContrato, Long idEstacion, String name, String nameSecrect, String pass);

    public void test(Long idEstacion, String ip, Long idContrato) throws MikrotikApiException;

    //nuevas instancias
    //crea solo el padre o busca uno nuevo
    /*Step 1 */
    public MikrotikPadreSimpleQueue padreQueuesimple(MikrotikQueueSimplePadreDTO create);

    //crea el secrect en el ppoe
    public WinmaxPass pppSecrect(Long idContrato, Long idEstacion, String profile, String nameSecrect, String pass)
        throws MikrotikApiException;

    //crea el hijo
    /*step 2 */
    public MikrotikHijoSimpleQueue hijoQueueSimple(MikrotikQueueSimplePadreDTO padre);

    //actualiza el target del hijo creado
    public MikrotikPadreSimpleQueue updatedTargetPadreInRB(Long idEstacion, Long idPadre) throws MikrotikApiException;

    //actualiza remote adddes
    public WinmaxPass updatedRemoteAddress(Long id, Long idEstacion, Long idIp) throws MikrotikApiException;
    /**QUEUESIMPLE*/
    public List<MikrotikQueueSimpleDTO> QueueSimpleAll(Long idEstacion) throws MikrotikApiException;
    //eliminar padre de la rb
    public void deletePadreRb(Long idEstacion , String namePadre);
    //actualizar target de padre
    //public void updatedTargetPadre(Long idEstacion , String namePadre , String target);
    
    /**
     * consulta los profiles del pppOE de cada estacion*/
    public List<MikrotikPPPProfileDTO> profileByIdEstacion(Long idEstacion) throws MikrotikApiException;
    //active ppp
    public List<MikrotikPPPActiveDTO> pppoeActive(Long idEstacion) throws MikrotikApiException;
    //active find name
    public ValorStringDTO pppoeActiveFindByName(Long idEstacion , Long idContrato)throws MikrotikApiException;
    //remove active
    public void pppoeActiveRemoveById(Long idEstacion , String idrb);
    //secret del pppoe
    public List<MikrotikPPPSecretDTO> pppoeSecrectFindAll(Long idEstacion)throws MikrotikApiException;
    //buscar por name un perfil
    public ValorStringDTO pppeoSecretFindByName(Long idContrato , Long idEstacion);
    //change progile
    public List<Map<String, String>> pppoeSecretChangeProfile(Long idContrato , Long idTarifa , String idrb , Long idUser , Long idMigracion);
    
    //cortar cliente por idOrden
    public String cortarClienteMoroso(Long idOrden);

    //removiendo el hijo de los padres
    public void removeHijoSimpleQueue(Long idContrato);

    //deleteFather por id
    public void removePadreSimpleQueue(Long idPadre);

    //actualizar profile
    public List<String> updatedProfileEstacion(Long idEstacion);

    /*Mirotik
     * Ip -> pool
     */
    public List<Map<String,String>> findInRBIPPool(Long idEstacion);
}
