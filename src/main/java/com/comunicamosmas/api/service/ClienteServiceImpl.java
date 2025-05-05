package com.comunicamosmas.api.service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.repository.ISystemConfigDao;
import com.comunicamosmas.api.service.dto.ClienteDTO;
import com.comunicamosmas.api.service.dto.ClientePortalWebDTO;
import com.comunicamosmas.api.service.dto.ClientesDeclineClausuraDTO;
 

import java.io.ByteArrayOutputStream; 
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final Logger log = LoggerFactory.getLogger(Cliente.class);
    private final IClienteDao clienteDao;
    private final ISystemConfigDao systemConfigDao;
    private final EmailCampaignApiService emailCampaignApiService;
    private final IMailRelayService mailRelayService;
    public ClienteServiceImpl(IClienteDao clienteDao , ISystemConfigDao systemConfigDao , EmailCampaignApiService emailCampaignApiService , IMailRelayService mailRelayService)
    {
        this.clienteDao = clienteDao;
        this.systemConfigDao = systemConfigDao;
        this.emailCampaignApiService = emailCampaignApiService;
        this.mailRelayService = mailRelayService;
    }

    @Override
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente save(Cliente cliente) {        
        
        if(cliente.getNombreComercial() == null)
        {
            cliente.setNombreComercial(" ");
        }         
        
        if(cliente.getTipoCliente().equals("J"))
        {
            cliente.setApellidoMaterno(" ");
            cliente.setApellidoPaterno(" ");
            cliente.setNombrePrimer(" ");
            cliente.setNombreSegundo(" ");
            cliente.setGenero("E");
        }else{
            cliente.setRazonSocial(" ");
            cliente.setNombresRep(" ");
            cliente.setApellidosRep(" ");
        }
        // TODO Auto-generated method stub
        cliente.setTelefono(0L);
        cliente.setAutorizaSms(0L);
        cliente.setBomberil(0L);
        cliente.setEstadoCivil(" ");
        cliente.setEstrato(0);
        cliente.setfNacimiento(0);
        cliente.setIdOperaA(1L);
        cliente.setIdOperaB(1L);
        cliente.setObservaciones(" ");
        cliente.setSha(" ");
        cliente.setTipoVivienda(" ");
        return clienteDao.save(cliente);
    }

    @Override
    public Long deleteById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Cliente getClientByIdContrato(Long idContrato) {
        return clienteDao.getClientByIdContrato(idContrato);
    }

    @Override
    public List<Cliente> findByDocumento(String documento) {
        // TODO Auto-generated method stub
        return (List<Cliente>) clienteDao.findByDocumento(documento);
    }

    @Override
    public List<ClienteDTO> findByName(String nombre) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'findByName'");
        Optional<List<Object[]>> result = clienteDao.findByName(nombre);

        List<ClienteDTO> clientes = result.map(resp -> {

            List<ClienteDTO> clie = new ArrayList<>();

            for (Object[] rs : resp) {
                ClienteDTO obj = new ClienteDTO();
                if (rs[1].equals("N")) {
                    String concat = rs[3] + " " + rs[4] + " " + rs[5] + " " + rs[6] + " | " + rs[7].toString();
                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar(concat);
                } else {

                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar((String) rs[2]);
                }

                clie.add(obj);
            }

            return clie;

        }).orElse(null);

        return clientes;

    }

    @Override
    public List<ClienteDTO> findByCus(Long cus) {
        // TODO Auto-generated method stub
         Optional<List<Object[]>> result = clienteDao.findByCus(cus);

        List<ClienteDTO> clientes = result.map(resp -> {

            List<ClienteDTO> clie = new ArrayList<>();

            for (Object[] rs : resp) {
                ClienteDTO obj = new ClienteDTO();
                if (rs[1].equals("N")) {
                    String concat = rs[3] + " " + rs[4] + " " + rs[5] + " " + rs[6] + " | " + rs[7].toString();
                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar(concat);
                } else {

                    obj.setId((Integer) rs[0]);
                    obj.setConcatenar((String) rs[2]);
                }

                clie.add(obj);
            }

            return clie;

        }).orElse(null);

        return clientes;
    }

    @Override
    public List<ClienteDTO> validExisteCliente(String documento) {
        // TODO Auto-generated method stub
        Optional<List<Object[]>> result = clienteDao.validExiste(documento);

        List<ClienteDTO> cliente = result.map(resp->{

            List<ClienteDTO> cli = new ArrayList<>();
            for(Object[] rs : resp)
            {   ClienteDTO obj = new ClienteDTO();

                obj.setId((Integer) rs[0]);
                cli.add(obj);
            }

            return cli;

        }).orElse(null);

        return cliente;
    }

    @Override
    public Page<ClientePortalWebDTO> pageClienteSyncronicePortalWeb(Pageable page) {
        // TODO Auto-generated method stub
        Page<Cliente> clientes = clienteDao.listClientBySincronicePortalWeb( page);

        return clientes.map(this::converterClienteToClientesPortalWebDTO);
    }

    private ClientePortalWebDTO converterClienteToClientesPortalWebDTO(Cliente obj){

        ClientePortalWebDTO client = new ClientePortalWebDTO();
        client.setIdCliente((Long) obj.getId() );
        client.setTipoCliente((String) obj.getTipoCliente());
        client.setRazonSocial((String) obj.getRazonSocial());
        client.setNombreComercial((String) obj.getNombreComercial());
        client.setApellidoPaterno((String) obj.getApellidoPaterno());
        client.setApellidoMaterno((String) obj.getApellidoMaterno());
        client.setNombrePrimer((String) obj.getNombrePrimer());
        client.setNombreSegundo((String) obj.getNombreSegundo());
        client.setNombresRep((String) obj.getNombresRep());
        client.setApellidosRep((String) obj.getApellidosRep());
        client.setIdDocumento((String) obj.getIdDocumento() );
        client.setDocumento((Long) obj.getDocumento());
        client.setDv((Long) obj.getDv());
        client.setTelefono((Long) obj.getTelefono());
        client.setCelularA((String) obj.getCelularA());
        client.setIdOperaA((Long) obj.getIdOperaA());
        client.setCelularB((String) obj.getCelularB());
        client.setIdOperaB((Long) obj.getIdOperaB());
        client.setMail((String) obj.getMail());

        return client;
    }

    //@Scheduled(cron = "0 0 6 * * *")
    @Override
    public void clientesDeclineClausura() {
        // TODO Auto-generated method stub
         List<Object[]> client = clienteDao.findClientesDeclineClausura();
         
         

         List<ClientesDeclineClausuraDTO> list =  client.stream().map(this::converteDeclineClausuraDTO).collect(Collectors.toList());

         if(list.size() > 0)
         {
            Workbook excel = new XSSFWorkbook();

            Sheet sheet = excel.createSheet("Clientes");

            Row tittle = sheet.createRow(0);
            tittle.createCell(0).setCellValue("Tipo cliente");
            tittle.createCell(1).setCellValue("Documento");
            tittle.createCell(2).setCellValue("Contrato");
            tittle.createCell(3).setCellValue("Registrado");
            tittle.createCell(4).setCellValue("Nombre Cliente");
            tittle.createCell(5).setCellValue("Servicio");
            int[] row = {1};

            list.stream().forEach(cliente -> {
                Row campo = sheet.createRow(row[0]++);

                campo.createCell(0).setCellValue(cliente.getTipo_cliente());
                campo.createCell(1).setCellValue(cliente.getDocument().toString());
                campo.createCell(2).setCellValue(cliente.getId_contrato());
                campo.createCell(3).setCellValue(cliente.getRegistrado());
                campo.createCell(4).setCellValue(cliente.getName_cliente());
                campo.createCell(5).setCellValue(cliente.getNombre_servicio());
            });
            String base64Excel = null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
                excel.write(bos);
                byte[] excelBytes = bos.toByteArray();
                base64Excel = Base64.getEncoder().encodeToString(excelBytes);
                log.debug("Generando excel en base 64");
                SystemConfig system = systemConfigDao.findByOrigen("mail_decline_clausuras");
                EmailCampaignApi datos = emailCampaignApiService.findApiMailRelay(1);
                if (system != null) {
                    String[] splitTo = system.getComando().split(",");
                    List<String> listToString = new ArrayList<>(Arrays.asList(splitTo));
                    MailRelaySendMail mail = new MailRelaySendMail();
                    MailRelaySendMail.Send send = mail.new Send();
                    MailRelaySendMail.From from = mail.new From();
                    from.setEmail(datos.getMail_envio());
                    from.setName(datos.getMail_envio());
                    send.setFrom(from);
                    List<MailRelaySendMail.To> listTo = listToString.stream().map(rs ->{

                        MailRelaySendMail.To obj = mail.new To();
                        obj.setEmail(rs);
                        obj.setName(rs);

                        return obj;
                    })
                    .collect(Collectors.toList());
                    send.setTo(listTo);
                    send.setSubject("Lista de Cliente por finalizar contrato");

                    send.setHtml_part("<html><head></head><body><p>Enviado desde InConnection..</p></body></html>");
                    send.setText_part("Notificación");
                    send.setTxt_part_auto(false);
                    MailRelaySendMail.Attachment attachment = mail.new Attachment();
                    attachment.setContent(base64Excel);
                    attachment.setFile_name("clientes.xls");
                    attachment.setContent_type("application/octet-stream");
                    List<MailRelaySendMail.Attachment> listAttachments = new ArrayList<>();
                    listAttachments.add(attachment);
                    send.setAttachments(listAttachments);
                    
                    log.debug("enviar msm");
                    String url = datos.getUrl()+"send_emails";
                    log.debug("enviar msm");
                    mailRelayService.mailRelaySend(send, datos.getToken(), url);
                }else{
                    log.debug("sin datos en system_config");
                }
                
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }finally{
                try {
                    excel.close();
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
         }




    }

    private ClientesDeclineClausuraDTO converteDeclineClausuraDTO(Object[] obj)
    {
        ClientesDeclineClausuraDTO dto = new ClientesDeclineClausuraDTO();

        dto.setTipo_cliente((String) obj[0]);
        dto.setDocument((BigInteger) obj[1]);
        dto.setId_contrato((Integer) obj[2]);
        dto.setRegistrado((String) obj[3].toString());
        dto.setName_cliente((String) obj[4]);
        dto.setNombre_servicio((String) obj[5]);

        return dto;
    }

    @Override
    public void updatedClientPortalWebSincronice(Long idCliente) {
        // TODO Auto-generated method stub
        this.clienteDao.findById(idCliente).map(exist->{

            exist.setPortalweb("Sincronizado");
            this.clienteDao.save(exist);
            return exist;
        });
    }

    @Override
    public Page<Cliente> ClienteQuery(String query) {
        query = query.trim();
        Pageable page = PageRequest.of(1, 10);
        // Check if query is numeric
        if (query.matches("\\d+")) { // Only digits
            Long documentNumber = Long.parseLong(query);
            return clienteDao.findByRazonSocialOrNombrePrimerOrDocumento(
                query, query, documentNumber, page);
        } else {
            // For non-numeric queries, set documentNumber to null or impossible value
            return clienteDao.findByRazonSocialOrNombrePrimerOrDocumento(
                query, query, null ,page); // Assuming -1 won't match any real document
        }
    }

     
}
