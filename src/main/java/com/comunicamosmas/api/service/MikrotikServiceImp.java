package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.MigracionTarifa;
import com.comunicamosmas.api.domain.MikrotikHijoSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikIp;
import com.comunicamosmas.api.domain.MikrotikPadreSimpleQueue;
import com.comunicamosmas.api.domain.MikrotikTarifaReuso;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.domain.Tarifa;
import com.comunicamosmas.api.domain.WinmaxPass;
import com.comunicamosmas.api.service.dto.ClassErrorDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPActiveDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPProfileDTO;
import com.comunicamosmas.api.service.dto.MikrotikPPPSecretDTO;
import com.comunicamosmas.api.service.dto.MikrotikQueueSimpleDTO;
import com.comunicamosmas.api.service.dto.ValorStringDTO;
import com.comunicamosmas.api.service.dto.valorDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.net.SocketFactory;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

@Service
public class MikrotikServiceImp implements IMikrotikService {

    private String comment;

    @Autowired
    IMikrotikIpService mikrotikIpService;

    @Autowired
    IMikrotikTarifaReusoService tarifaReusoService;

    @Autowired
    IMikrotikPadreSimpleQueueService padreSimpleQueueService;

    @Autowired
    IMikrotikHijoSimpleQueueService mikrotikHijoSimpleQueueService;

    @Autowired
    IEstacionService estacionService;

    @Autowired
    IClienteService clienteService;

    @Autowired
    IWinmaxPassService winmaxPassService;

    @Autowired
    IContratoService contratoService;
    
    @Autowired
    ITarifaService tarifaService;
    
    @Autowired
    IMigracionTarifaService migracionTarifaService;
    
    @Autowired
    IOrdenService ordenService;
    
    ApiConnection apiConnection;

    @Override
    public void conection(String user, String password, String ip, long port) throws MikrotikApiException {
        apiConnection = ApiConnection.connect(SocketFactory.getDefault(), ip, (int) port, 2000);

        apiConnection.login(user, password);
    }

    @Override
    public void logout() throws ApiConnectionException {
        apiConnection.close();
    }

    @Override
    public void createPadre(MikrotikTarifaReuso mikrotikTarifaReuso) throws MikrotikApiException {
        //armar PAdee
        /*String command = "/queue/simple/add comment='"+mikrotikTarifaReuso.getComment()+"' limit-at="+
				mikrotikTarifaReuso.getLimitAt()+" max-limit="+mikrotikTarifaReuso.getMaxLimit()+
				" name='"+mikrotikTarifaReuso.getName()+"' target=0.0.0.0/24"; */
        //String command = "/queue/simple/print";
        //List<Map<String, String>> rs = apiConnection.execute(command);
        //System.out.print(rs);
    }

    @SuppressWarnings("null")
    @Override
    public ClassErrorDTO instalacion(Long idPlan, Long idEstacion, Long idIp, Long contrato, Long tecnologia) {
        ClassErrorDTO error = new ClassErrorDTO();
        //consultar si ya existe el contrato o ip registrada
        //recuperar estacion
        Estacion estacion = estacionService.findById(idEstacion);
        //consultar recuperar bajada y subida del idPlan y reuso
        MikrotikTarifaReuso plan = tarifaReusoService.findById(idPlan);
        //recupera la ip
        MikrotikIp mikrotikIp = mikrotikIpService.findById(idIp);
        String ip = mikrotikIp.getIp();
        //validar si la ip no este registrada en la rb del simple queue
        try {
            this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
            String validarTarget = "/queue/simple/print where target=" + ip + "/32";
            String result = this.setCommando(validarTarget);
            if (result != null) {
                error.setError(true);
                error.setMsm("Ya existe la ip en la rb");
                return error;
            }
        } catch (MikrotikApiException e) {
            error.setError(true);
            error.setMsm(e.getMessage());
            e.printStackTrace();
            return error;
        }
        //crear el limit con subida y bajada
        String velocidad = plan.getSubida() + "/" + plan.getBajada();
        //validamos si existe un padre ya creado
        //System.out.print(plan.getNombrePadre()+ ","+velocidad+" - "+plan.getReuso()+ " - "+idEstacion);
        //consultamos si existe un padre libre menor del reuso del plan y con el nombre del plan
        //y velocidad por la estación creada
        MikrotikPadreSimpleQueue padre = padreSimpleQueueService.findByIdPlanAndReuso(idPlan, plan.getReuso(), idEstacion);
        //si el objecto recuperado es != de null,
        //entonces procedemos a actualizarlo con un nuevo hijo
        if (padre != null) {
            //System.out.print(padre.getId() + "id");
            //add hijo al padre
            String hijoLimit = "";
            try {
                this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
                String target = padre.getTarget() + "," + ip;
                this.addTarget(target, padre.getName());
                //anexa al target del padre la nueva ip
                //hijoLimit = this.crearHijo(plan.getSubida(), plan.getBajada(), contrato, padre.getName() , ip , plan.getReuso());
                if (tecnologia != 2) {
                    this.updatedRemoteAddres(contrato, idEstacion, ip);
                }
                //cierra la sesión en el winbox
                this.logout();
                //guarda el hijo en la base de datos
                mikrotikHijoSimpleQueueService.crearHijo(
                    padre.getId(),
                    idIp,
                    hijoLimit,
                    velocidad,
                    contrato,
                    contrato,
                    "pcq-upload-default/pcq-download-default",
                    ip
                );
                //actualiza el estado de la ip a 1 (ocupado)
                mikrotikIpService.updatedStatus(idIp);
                //actualizar el target en la base de datos para el padre
                padreSimpleQueueService.updatedTargetReuso(ip, padre.getId());
            } catch (MikrotikApiException e) {
                error.setError(true);
                error.setMsm(e.getMessage());
                e.printStackTrace();
                return error;
            }
        } else {
            UUID uuid = UUID.randomUUID();
            String uuidasString = uuid.toString();
            String newName = plan.getNombrePadre() + "#" + uuidasString;
            //System.out.print("vacion");
            String hijoLimit = "";
            try {
                this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
                //comando
                this.newPadre(plan.getComment(), velocidad, velocidad, newName, ip);
                //hijoLimit = this.crearHijo(plan.getSubida(), plan.getBajada(), contrato, newName , ip, plan.getReuso());
                if (tecnologia != 2) {
                    this.updatedRemoteAddres(contrato, idEstacion, ip);
                }
                this.logout();
                //registrar en base de datos
                Long idPadre = padreSimpleQueueService.newPadre(idPlan, newName, ip, plan.getComment(), velocidad, velocidad, idEstacion);
                mikrotikHijoSimpleQueueService.crearHijo(
                    idPadre,
                    idIp,
                    hijoLimit,
                    velocidad,
                    contrato,
                    contrato,
                    "pcq-upload-default/pcq-download-default",
                    ip
                );
                mikrotikIpService.updatedStatus(idIp);
            } catch (MikrotikApiException e) {
                // TODO Auto-generated catch block
                error.setError(true);
                error.setMsm(e.getMessage());
                e.printStackTrace();
                return error;
            }
        }

        error.setError(false);
        return error;
    }

    /**
     *
     * */

    private void updatedRemoteAddres(Long idContrato, Long idEstacion, String ip) throws MikrotikApiException {
        //WinmaxPass winmaxPass= winmaxPassService.findByIdContrato(idContrato);
        Estacion estacion = estacionService.findById(idEstacion);
        WinmaxPass winmaxPass = winmaxPassService.findByIdContrato(idContrato);
        String command = "/ppp/secret/set .id=" + winmaxPass.getUsuario() + " remote-address=" + ip;
        //this.conection(estacion.getApiUser(), estacion.getApiPass(),  estacion.getApiIp(), estacion.getApiPort());
        apiConnection.execute(command);
        //this.logout();
    }

    private void newPadre(String comment, String limit, String max, String name, String ip) throws MikrotikApiException {
        String command =
            "/queue/simple/add comment='" +
            comment +
            "' limit-at=" +
            limit +
            " max-limit=" +
            max +
            " name='" +
            name +
            "' target=" +
            ip +
            "/32";

        apiConnection.execute(command);
    }

    private void addTarget(String target, String padre) throws MikrotikApiException {
        String command = "/queue/simple/set .id=" + padre + " target=" + target + "";
        apiConnection.execute(command);
    }

    //crear el hijo cuan
    //private String crearHijo(String subida, String bajada, Long contrato, String padre , String target , Long reuso )throws MikrotikApiException

    private String generarLimit(String subida, String bajada, Long reuso) {
        String[] velocidadSubida = subida.split("M");
        String[] velocidadBajada = bajada.split("M");
        int numberSubida = Integer.parseInt(velocidadSubida[0]);
        int numberBajada = Integer.parseInt(velocidadBajada[0]);
        //
        int limitSubida = 0;
        int limitBajada = 0;
        String sub;
        String baj;
        //conversion subida
        if (numberSubida % reuso == 0) {
            limitSubida = (int) (numberSubida / reuso);
            sub = limitSubida + "M";
        } else {
            float temp = Float.parseFloat(velocidadSubida[0]);
            //float result = (temp / reuso) * 1000;
            float result = (temp * 1024) / reuso;
            limitSubida = (int) result;
            sub = limitSubida + "k";
        }
        //conversión bajada;
        if (numberBajada % reuso == 0) {
            limitBajada = (int) (numberBajada / reuso);
            baj = limitBajada + "M";
        } else {
            float temp = Float.parseFloat(velocidadBajada[0]);
           // float result = (temp / reuso) * 1000;
            float result = (temp * 1024) / reuso;
            limitBajada = (int) result;
            baj = limitBajada + "k";
        }
        String limitAt = sub + "/" + baj;

        /*String command ="/queue/simple/add limit-at="+limitAt+" max-limit="+subida+"/"+bajada+
					" name="+contrato+" parent="+padre+" queue=pcq-upload-default/pcq-download-default target="+
					target+"/32";
			
			apiConnection.execute(command);*/

        return limitAt;
    }

    @Override
    public List<Map<String, String>> queueSimpleFindByContrato(Long idContrato, Long idEstacion) throws MikrotikApiException {
        // TODO Auto-generated method stub
        Estacion estacion = estacionService.findById(idEstacion);
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        String command = "/queue/simple/print where name=" + idContrato;
        List<Map<String, String>> result = apiConnection.execute(command);
        this.logout();
        return result;
    }

    @Override
    public List<Map<String, String>> pppProfiles(Long idEstacion) throws MikrotikApiException {
        Estacion estacion = estacionService.findById(idEstacion);
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        String command = "/ppp/profile/print";
        List<Map<String, String>> result = apiConnection.execute(command);

        this.logout();

        return result;
    }

    @Override
    public ClassErrorDTO pppSecretsNew(Long idAppMaster, Long idContrato, Long idEstacion, String name, String nameSecrect, String pass) {
        ClassErrorDTO error = new ClassErrorDTO();
        //consultar si ya existe el contrato
        Long count = winmaxPassService.countFindByIdContrato(idContrato);
        if (count > 0) {
            error.setMsm("Ya existe el contrato con perfil");
            error.setError(true);
            return error;
        }

        UUID uuid = UUID.randomUUID();
        String uuidasString = uuid.toString();
        String newName = uuidasString;
        String[] codeApi = newName.split("-");
        //datos de estacion
        Estacion estacion = estacionService.findById(idEstacion);
        //datos de cliente
        Cliente cliente = clienteService.getClientByIdContrato(idContrato);

        if (cliente.getTipoCliente() == "J") {
            this.comment =
                codeApi[0] +
                "-" +
                idContrato +
                "-" +
                cliente.getId() +
                "-" +
                cliente.getDocumento() +
                "-" +
                cliente.getRazonSocial() +
                "- creo Api";
        } else {
            this.comment =
                codeApi[0] +
                "-" +
                idContrato +
                "-" +
                cliente.getId() +
                "-" +
                cliente.getDocumento() +
                "-" +
                cliente.getNombrePrimer() +
                " " +
                cliente.getNombreSegundo() +
                " " +
                cliente.getApellidoPaterno() +
                "- creo Api";
        }
        try {
            //conectar a estacion //
            this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
            //comando para enviar
            String commando =
                "/ppp/secret/add name=" +
                nameSecrect +
                " password=" +
                pass +
                " profile=" +
                name +
                " comment='" +
                comment +
                "' service=pppoe";
            //ejecutar comando
            apiConnection.execute(commando);
            this.logout();
        } catch (MikrotikApiException e) {
            error.setError(true);
            error.setMsm(e.getMessage());
            return error;
        }
        //apiConnection.cancel(tag);
        WinmaxPass winboxPass = new WinmaxPass();
        winboxPass.setComentario(this.comment);
        winboxPass.setIdContrato(idContrato);
        winboxPass.setIdEstacion(idEstacion);
        winboxPass.setPass(pass);
        winboxPass.setUsuario(nameSecrect);
        winmaxPassService.save(winboxPass);

        error.setError(false);
        return error;
    }

    @Override
    public void test(Long idEstacion, String ip, Long idContrato) throws MikrotikApiException {
        Estacion estacion = estacionService.findById(idEstacion);
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        String command = "/queue/simple/print where target=" + ip + "/32";
        String execute = this.setCommando(command);
        System.out.print("comando:" + command + " - result:" + execute);
        //WinmaxPass winmaxPass = winmaxPassService.findByIdContrato(idContrato);
        //String command = "/ppp/secret/set .id="+winmaxPass.getUsuario()+" remote-address="+ip;
        //
        //apiConnection.execute(command);
        this.logout();
    }

    public String setCommando(String commando) throws MikrotikApiException {
        List<Map<String, String>> result = apiConnection.execute(commando);
        Iterator<Map<String, String>> iterator = result.iterator();
        if (iterator.hasNext()) {
            Map<String, String> r = iterator.next();

            return r.get(".id");
        }

        return null;
    }

    public String setnewCommando(String comando) throws MikrotikApiException {
        List<Map<String, String>> rs = apiConnection.execute(comando);
        for (Map<String, String> r : rs) {
            System.out.println(r.get("name"));
        }

        return null;
    }

    ////////////////////////////////////////////////////////////nueva/////////////////////////MikrotikApiException

    @SuppressWarnings("null")
    @Override
    public MikrotikPadreSimpleQueue padreQueuesimple(Long idPlan, Long idEstacion, Long idIp)   {
        //consultar recuperar bajada y subida del idPlan y reuso
        try
        {

        MikrotikTarifaReuso plan = tarifaReusoService.findById(idPlan);
        //
        MikrotikIp mikrotikIp = mikrotikIpService.findById(idIp);

        String ip = mikrotikIp.getIp();

        //consultar si ya existe padre libre
        MikrotikPadreSimpleQueue padre = padreSimpleQueueService.findByIdPlanAndReuso(idPlan, plan.getReuso(), idEstacion);
        if (padre != null) {
            //existe padre disponible retornamos el padre
            Long reuso = padre.getReuso() + 1;
            padre.setReuso(reuso);
            String target = padre.getTarget();
            padre.setTarget(target + "," + ip);
            padreSimpleQueueService.save(padre);
            return padre;
        } else {
            //creamos el padre en la rb
            Estacion estacion = estacionService.findById(idEstacion);

            UUID uuid = UUID.randomUUID();

            String uuidasString = uuid.toString();

            String namePadre = plan.getNombrePadre() + "#" + uuidasString;

            String limit = plan.getSubida() + "/" + plan.getBajada();

            String max = limit;

            this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());

            String command =
                "/queue/simple/add comment='" +
                plan.getComment() +
                "' limit-at=" +
                limit +
                " max-limit=" +
                max +
                " name='" +
                namePadre +
                "' target=" +
                ip +
                "/32";

            apiConnection.execute(command);

            this.logout();

            MikrotikPadreSimpleQueue newPadre = new MikrotikPadreSimpleQueue();
            newPadre.setComment(plan.getComment());
            newPadre.setIdEstacion(idEstacion);
            newPadre.setIdTarifaReuso(plan.getId());
            newPadre.setLimitAt(limit);
            newPadre.setMaxLimit(max);
            newPadre.setName(namePadre);
            newPadre.setReuso(1L);
            newPadre.setTarget(ip);
            padreSimpleQueueService.save(newPadre);

            return newPadre;
        }
    }catch(MikrotikApiException e){

        throw new ExceptionNullSql(new Date(), "Error en Rb", e.getMessage());

    }catch(Exception e)
    {
        throw new ExceptionNullSql(new Date() , "Error base de datos" , e.getMessage());
    }
    
    }

    @Override
    public WinmaxPass pppSecrect(Long idContrato, Long idEstacion, String profile, String nameSecrect, String pass)
        throws MikrotikApiException {
        //consultamos x contratos si ya existe el secrets creado
        WinmaxPass winmaxPass = winmaxPassService.findByIdContrato(idContrato);
        if (winmaxPass != null) {
            return winmaxPass;
        } else {
            //estacion
            Estacion estacion = estacionService.findById(idEstacion);
            //random de digitos
            UUID uuid = UUID.randomUUID();
            //pasar a string
            String uuidasString = uuid.toString();
            //nombre
            String newName = uuidasString;
            //seprar por -
            String[] codeApi = newName.split("-");
            //datos de cliente
            Cliente cliente = clienteService.getClientByIdContrato(idContrato);
            String comment = "";
            if (cliente.getTipoCliente() == "J") {
                comment =
                    codeApi[0] +
                    "-" +
                    idContrato +
                    "-" +
                    cliente.getId() +
                    "-" +
                    cliente.getDocumento() +
                    "-" +
                    cliente.getRazonSocial() +
                    "- creo Api";
            } else {
                comment =
                    codeApi[0] +
                    "-" +
                    idContrato +
                    "-" +
                    cliente.getId() +
                    "-" +
                    cliente.getDocumento() +
                    "-" +
                    cliente.getNombrePrimer() +
                    " " +
                    cliente.getNombreSegundo() +
                    " " +
                    cliente.getApellidoPaterno() +
                    "- creo Api";
            }
            /////////////////////Mikrotik
            //conectar a estacion //
            this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
            //comando para enviar
            String commando =
                "/ppp/secret/add name=" +
                nameSecrect +
                " password=" +
                pass +
                " profile=" +
                profile +
                " comment='" +
                comment +
                "' service=pppoe";
            //ejecutar comando
            apiConnection.execute(commando);
            this.logout();
            //almacena información
            WinmaxPass winmaxPassnew = new WinmaxPass();
            winmaxPassnew.setComentario(comment);
            winmaxPassnew.setIdContrato(idContrato);
            winmaxPassnew.setIdEstacion(idEstacion);
            winmaxPassnew.setPass(pass);
            winmaxPassnew.setUsuario(nameSecrect);
            winmaxPassService.save(winmaxPassnew);

            return winmaxPassnew;
        }
        // TODO Auto-generated method stub

    }

    @Override
    public MikrotikHijoSimpleQueue hijoQueueSimple(Long idPlan, Long idIp, Long idPadre, Long idContrato, Long idEstacion)
    {
        try{

        //consultar recuperar bajada y subida del idPlan y reuso
        MikrotikTarifaReuso plan = tarifaReusoService.findById(idPlan);
        String limit = this.generarLimit(plan.getSubida(), plan.getBajada(), plan.getReuso());
        //consultamos la ip
        MikrotikIp ip = mikrotikIpService.findById(idIp);
        MikrotikPadreSimpleQueue padre = padreSimpleQueueService.findById(idPadre);
        String command =
            "/queue/simple/add limit-at=" +
            limit +
            " max-limit=" +
            plan.getSubida() +
            "/" +
            plan.getBajada() +
            " name=" +
            idContrato +
            " parent=" +
            padre.getName() +
            " queue=pcq-upload-default/pcq-download-default target=" +
            ip.getIp() +
            "/32";
        //consulta de la estacion
        Estacion estacion = estacionService.findById(idEstacion);
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        apiConnection.execute(command);
        this.logout();
        //crear el simplequeue
        MikrotikHijoSimpleQueue hijo = new MikrotikHijoSimpleQueue();
        hijo.setIdContrato(idContrato);
        hijo.setIdIp(idIp);
        hijo.setIdPadre(idPadre);
        hijo.setLimitAt(limit);
        hijo.setMaxLimit(plan.getSubida() + "/" + plan.getBajada());
        hijo.setName(Long.toString(idContrato));
        hijo.setQueue("pcq-upload-default/pcq-download-default");
        hijo.setTarget(ip.getIp());
        mikrotikHijoSimpleQueueService.save(hijo);
        //actualizar el padre
       /* Long reuso = padre.getReuso() + 1;
        String target = padre.getTarget() + "," + idIp;
        padre.setTarget(target);
        padre.setReuso(reuso);
        padreSimpleQueueService.save(padre);*/
        
        return hijo;

        }catch(MikrotikApiException e)
        {
            //no se pudo crear el hijo, por tal motivo se debe validar eliminar el target creado en el anterior 
            
            throw new ExceptionNullSql(new Date(), "Error creando hijo", e.getMessage());
        } 
    }

    private String String(Long idContrato) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WinmaxPass updatedRemoteAddress(Long id, Long idEstacion, Long idIp) throws MikrotikApiException {
        //consultar estacion
        Estacion estacion = estacionService.findById(idEstacion);
        //consultar ip
        MikrotikIp ip = mikrotikIpService.findById(idIp);
        //consultamos el user del ppp/secret
        WinmaxPass winmaxPass = winmaxPassService.findById(id);

        String command = "/ppp/secret/set .id=" + winmaxPass.getUsuario() + " remote-address=" + ip.getIp();
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        System.out.print(command);
        apiConnection.execute(command);
        this.logout();
        return winmaxPass;
    }

    @Override
    public MikrotikPadreSimpleQueue updatedTargetPadreInRB(Long idEstacion, Long idPadre) throws MikrotikApiException {
        // TODO Auto-generated method stub
        Estacion estacion = estacionService.findById(idEstacion);
        //padre
        MikrotikPadreSimpleQueue padre = padreSimpleQueueService.findById(idPadre);

        //System.out.print(padre.getTarget() +":"+padre.getName());
        this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
        //String id = this.setnewCommando("/queue/simple/print");// where name="+padre.getName()+"");
        //System.out.print(id);
        String name = padre.getName();
        String target = padre.getTarget();
        String command = "/queue/simple/set .id=" + name + " target=" + target;
        //this.addTarget(target, name);
        //System.out.print(command);
        apiConnection.execute(command);
        this.logout();
        return padre;
    }

	@Override
	public List<MikrotikPPPProfileDTO> profileByIdEstacion(Long idEstacion) throws MikrotikApiException{
		//informacion de estacion
		Estacion estacion = estacionService.findById(idEstacion);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/ppp/profile/print";
		List<Map<String, String>> result =  apiConnection.execute(command);
		this.logout();
		 //recorrer lista y mappear result en MikrotikPPPProfileDTO
		List<MikrotikPPPProfileDTO> pppoe = new ArrayList<>();
		for(Map<String, String> map : result)
		{
			MikrotikPPPProfileDTO object = new MikrotikPPPProfileDTO();
			object.setId((String) map.get(".id"));
			object.setLocalAddress((String) map.get("local-address"));
			object.setName((String) map.get("name"));
			object.setRateLimit((String) map.get("rate-limit"));
			object.setRemoteAddress((String) map.get("remote-address"));
			pppoe.add(object);
		}
		return pppoe;
	}

	@Override
	public List<MikrotikPPPActiveDTO> pppoeActive(Long idEstacion)throws MikrotikApiException {
		// TODO Auto-generated method stub
		Estacion estacion = estacionService.findById(idEstacion);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/ppp/active/print";
		List<Map<String, String>> result =  apiConnection.execute(command); 
		this.logout();
		//mapear resultados
		List<MikrotikPPPActiveDTO> pppoeActive = new ArrayList<>();
		for(Map<String, String> map : result)
		{
			MikrotikPPPActiveDTO object = new MikrotikPPPActiveDTO();
			
			object.setAddress((String) map.get("address"));
			object.setCallerId((String) map.get("caller-id"));
			object.setComment((String) map.get("comment"));
			object.setId((String) map.get(".id"));
			object.setName((String) map.get("name"));
			object.setService((String) map.get("service"));
			object.setUptime((String) map.get("uptime"));
			
			pppoeActive.add(object);
		}
		
		return pppoeActive;
	}

	@SuppressWarnings("unused")
	@Override
	public ValorStringDTO pppeoSecretFindByName(Long idContrato , Long idEstacion)
			throws MikrotikApiException {
		//buscar informacion de la estacion
		Estacion estacion = estacionService.findById(idEstacion);
		//buscar name en winmapas
		WinmaxPass winmax = winmaxPassService.findByIdContrato(idContrato);
		//
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		
		String command = "/ppp/secret/print where name="+ winmax.getUsuario();
		
		List<Map<String, String>> result =  apiConnection.execute(command);
		
		this.logout();
		
		ValorStringDTO valor = new  ValorStringDTO();
		
		if(valor == null)
		{
			return null;
			
		}else{
			
			for(Map<String , String > rs : result)
			{
				valor.setValor((String) rs.get(".id"));
			}
			
			return valor;
		}
		
	}

	@Override
	public List<Map<java.lang.String, java.lang.String>> pppoeSecretChangeProfile(Long idContrato,
			Long idTarifa, java.lang.String idrb , Long idUser , Long idMigracion) throws MikrotikApiException {
		//estaicon
		Contrato contrato = contratoService.findById(idContrato);
		
		Estacion estacion = estacionService.findById(contrato.getIdEstacion());
		
		Tarifa tarifa = tarifaService.findById(idTarifa);
		
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		
		String command = "/ppp/secret/set .id="+idrb+" profile="+tarifa.getCodigoMikrotik();
		List<Map<String, String>> result =  apiConnection.execute(command);
		this.logout();
		
		
		
		MigracionTarifa  migracion = migracionTarifaService.findById(idMigracion);
		migracion.setIdAdmin(idUser);
		migracion.setEstado(1L);
		migracionTarifaService.save(migracion);
		
		//actualizar contrato
				contrato.setIdTarifa(idTarifa);
				contrato.setIdTarifaPromo(idTarifa);
				
				Contrato save = contratoService.save(contrato);
		
		return result;
	}

	@Override
	public ValorStringDTO pppoeActiveFindByName(Long idEstacion, Long idContrato) throws MikrotikApiException {
		// TODO Auto-generated method stub
		Estacion estacion = estacionService.findById(idEstacion);
		WinmaxPass winmax = winmaxPassService.findByIdContrato(idContrato);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/ppp/active/print where name="+winmax.getUsuario();
		List<Map<String, String>> result =  apiConnection.execute(command);
		this.logout();
		
		//
		ValorStringDTO valor = new ValorStringDTO();
		for(Map<String, String> rs : result)
		{
			valor.setValor((String) rs.get(".id"));
		}
		return valor;
	}

	@Override
	public void pppoeActiveRemoveById(Long idEstacion, java.lang.String idrb) throws MikrotikApiException {
		
		Estacion estacion = estacionService.findById(idEstacion);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/ppp/active/remove .id="+idrb;
		apiConnection.execute(command);
		this.logout();
	}

	@Override
	public List<MikrotikPPPSecretDTO>  pppoeSecrectFindAll(Long idEstacion)
			throws MikrotikApiException {
		Estacion estacion = estacionService.findById(idEstacion);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/ppp/secret/print";
		List<Map<String, String>> result =  apiConnection.execute(command);
		this.logout();
		List<MikrotikPPPSecretDTO> secret = new ArrayList<>();
		for(Map<String , String> rs : result)
		{
			MikrotikPPPSecretDTO object = new MikrotikPPPSecretDTO();
			
			object.setId((String) rs.get(".id"));
			object.setCallerId((String) rs.get("caller-id"));;
			object.setComment((String) rs.get("comment"));
			object.setDisable((String) rs.get("disabled"));
			object.setIpv6((String) rs.get("ipv6-routes)"));
			object.setLastCallerId((String) rs.get("last-caller-id"));;
			object.setLastLoggedOut((String) rs.get("last-logged-out"));
			object.setName((String) rs.get("name"));
			object.setProfile((String) rs.get("profile"));
			object.setService((String) rs.get("service"));
			
			secret.add(object);
		}
		return secret;
	}

	@Override
	public List<MikrotikQueueSimpleDTO> QueueSimpleAll(Long idEstacion) throws MikrotikApiException {
		Estacion estacion = estacionService.findById(idEstacion);
		this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
		String command = "/queue/simple/print";
		
		List<Map<String, String>> result =  apiConnection.execute(command);
		this.logout();
		List<MikrotikQueueSimpleDTO> simple = new ArrayList<>();
		for(Map<String, String> rs: result)
		{
			MikrotikQueueSimpleDTO object = new MikrotikQueueSimpleDTO();
			object.setId((String) rs.get(".id"));
			object.setComment((String) rs.get("comment"));
			object.setDisable((String) rs.get("disabled"));
			object.setDroppend((String) rs.get("dropped"));
			object.setLimitAt((String) rs.get("limit-at"));
			object.setMaxLimit((String) rs.get("max-limit"));
			object.setName((String) rs.get("name"));
			object.setParent((String) rs.get("parent"));
			object.setPriory((String) rs.get("prioryty"));
			object.setQueue((String) rs.get("queue"));
			object.setTarget((String) rs.get("target"));
			simple.add(object);
		}
		return simple;
	}

	@Override
	/**
	 * 1. buscar el secret en el pppOE*/
	public java.lang.String cortarClienteMoroso(Long idOrden) {
		//buscar orden
		Contrato contrato = contratoService.findById(idOrden);
		WinmaxPass winmaxpass = winmaxPassService.findByIdContrato(idOrden);
		//retornar estacion
		
		Estacion estacion = estacionService.findById(contrato.getIdEstacion());
		String respuesta = "vacio";
		String id = "";
		try
		{
			this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
			String commando = "/ppp/secret/print where name="+winmaxpass.getUsuario();
			List<Map<String, String>> result =  apiConnection.execute(commando);
			
			 
			for(Map<String, String> rs : result)
			{
				String nameSecret =  rs.get("name");
				id = rs.get(".id");
				if(nameSecret.equals(winmaxpass.getUsuario())) {
					respuesta += "Secret encontrado;";
				}else{
					respuesta += "No se ha encontrado el secret;";
				}
			}
			if(!id.isEmpty())
			{
				try {
					
					commando = "/ppp/secret/set .id="+id+" profile=MOROSOS";
					apiConnection.execute(commando);
					
					respuesta +="Nuevo perfil MOROSOS;";
					
					ValorStringDTO idActive =  this.pppoeActiveFindByName(estacion.getId() , idOrden);
					
					if(idActive.getValor().isEmpty())
					{
						respuesta += "Id en active connections no encontrado";
					}else {
						
						try {
							
							commando = "/ppp/active/remove .id="+idActive.getValor();
							apiConnection.execute(commando);
							
						}catch(MikrotikApiException h)
						{
							respuesta += h.getMessage();
						}
					}
					
				}catch(MikrotikApiException j)
				{
					respuesta += j.getMessage();
				}
				 
			}
			this.logout();
			
		}catch(MikrotikApiException e)
		{
			respuesta += e.getMessage()+";";
		}
		return respuesta;
	}

	/**
	 * ELIMINAD UNA PADRE DE LA RB CUANDO SE CREA VACIO*/
	@Override
	public void deletePadreRb(Long idEstacion, java.lang.String namePadre) {
		
		Estacion estacion = estacionService.findById(idEstacion);
		
		try {
			this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
			String commando = "/queue/simple/print where name="+namePadre;
			List<Map<String, String>>  result = apiConnection.execute(commando);
			//extraemos el .id
			String id = "";
			for(Map<String , String > rs : result)
			{
				id = rs.get(".id");
			}
			
			if(!id.isEmpty())
			{
				commando = "/queue/simple/remove .id="+id;
				apiConnection.execute(commando);
			}
			this.logout();
		}catch(MikrotikApiException e) {
			System.out.print("aliminando Padre : " + e.getMessage());
		}
		
	}
	/*
	 * ACTUALIZAR EL NUEVO TARGET AL PADRE
	 * **/

	/*@Override
	public void updatedTargetPadre(Long idEstacion, java.lang.String namePadre, java.lang.String target) {
		// TODO Auto-generated method stub
		Estacion estacion = estacionService.findById(idEstacion);
		try {
			this.conection(estacion.getApiUser(), estacion.getApiPass(), estacion.getApiIp(), estacion.getApiPort());
			String commando = "/queue/simple/print where name="+namePadre;
			List<Map<String, String>>  result = apiConnection.execute(commando);
			//extraemos el .id
			String id = "";
			for(Map<String , String > rs : result)
			{
				id = rs.get(".id");
			}
			
			if(!id.isEmpty())
			{ 
				commando = "/queue/simple/set .id="+id+" target="+target;
				apiConnection.execute(commando);
			}
			this.logout();
		}catch(MikrotikApiException e)
		{
			System.out.print("actualizando target +" + e.getMessage());
		}
	}*/
}
