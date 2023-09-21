package com.comunicamosmas.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.domain.HanRetiros;
import com.comunicamosmas.api.domain.MailRelaySendMail;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.repository.IHanRetirosDao;
import com.comunicamosmas.api.service.EmailCampaignApiService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IHanRetirosService;
import com.comunicamosmas.api.service.IMailRelayService;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.HanRetirosCommentsDTO;
import com.comunicamosmas.api.service.dto.HanRetirosDTO;
import com.comunicamosmas.api.service.dto.MeseCountDTO;
import com.comunicamosmas.api.service.dto.ReporteHanRetirosDTO;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HanRetirosServiceImp implements IHanRetirosService {

    @Autowired
    IHanRetirosDao retirosDao;

    @Autowired
    ISystemConfigService systemService;

    @Autowired
    IContratoService contratoService;

    @Autowired
    EmailCampaignApiService emailCampaignApiService;

    @Autowired
    IMailRelayService mailRelayService;

    @Override
    public HanRetiros save(HanRetiros retiros) {
        if (retiros.getIdContrato() != null) {
            Contrato contrato = contratoService.findById(retiros.getIdContrato());
            retiros.setIdServicio(contrato.getIdServicio());
        }

        // TODO Auto-generated method stub

        retiros.setCreatedAt();
        retiros.setCierreFecha(null);
        
        return retirosDao.save(retiros);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        retirosDao.deleteById(id);
    }

    @Override
    public List<HanRetiros> findAll() {
        return (List<HanRetiros>) retirosDao.findAll();
    }

    @Override
    public List<HanRetirosDTO> findByIdContrato(Long idContrato) {

        try {

            Optional<List<Object[]>> result = retirosDao.findByIdContrato(idContrato);

            List<HanRetirosDTO> list = result.map(resp -> {

                List<HanRetirosDTO> retiros = new ArrayList<>();

                for (Object[] rs : resp) {
                    HanRetirosDTO obj = new HanRetirosDTO();

                    obj.setId((Integer) rs[0]);
                    obj.setTipo((String) rs[1]);
                    obj.setDescripcion((String) rs[2]);
                    obj.setId_user((Integer) rs[3]);
                    obj.setCreated_at((String) rs[4].toString());
                    obj.setId_contrato((Integer) rs[5]);
                    obj.setEstado((String) rs[6]);
                    obj.setCierre_fecha(rs[7] != null ? (String) rs[7].toString() : "");
                    obj.setUrl_documento((String) rs[8]);
                    obj.setReportado((String) rs[11]);
                    obj.setNameUser((String) rs[12]);
                    obj.setCliente((String) rs[13]);

                    retiros.add(obj);
                }

                return retiros;
            }).orElse(new ArrayList<>());

            return list;
        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Consultando Padres", e.getMessage());
        }

    }

    @Override
    public List<HanRetirosCommentsDTO> messageRetiros(Integer idPadre) {
        try {
            Optional<List<Object[]>> result = retirosDao.findMessageByidPadre(idPadre);

            // SystemConfig ruta = systemService.findByOrigen("retiro_img");

            List<HanRetirosCommentsDTO> comments = result.map(resp -> {

                List<HanRetirosCommentsDTO> comm = new ArrayList<>();

                for (Object[] rs : resp) {
                    HanRetirosCommentsDTO obj = new HanRetirosCommentsDTO();

                    obj.setId((Integer) rs[0]);
                    obj.setDescripcion((String) rs[1]);
                    obj.setId_user((Integer) rs[2]);
                    obj.setCreated_at((String) rs[3].toString());
                    obj.setUrl_documento((String) rs[4]);
                    obj.setIdPadre((Integer) rs[5]);
                    obj.setUserName((String) rs[6]);
                    String avatar = "http://10.111.39.3/control/archivos/" + rs[7].toString();
                    obj.setFoto(avatar);

                    comm.add(obj);
                }

                return comm;

            }).orElse(new ArrayList<>());

            return comments;
        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Consultado Messages", e.getMessage());
        }
    }

    @Override
    public void uploadImg(MultipartFile file, HanRetiros retiros) {

        try {

            SystemConfig ruta = systemService.findByOrigen("retiro_img");

            File directory = new File(ruta.getComando());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            Path filePath = Path.of(ruta.getComando(), fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            retiros.setUrlDocumento(fileName);

            this.save(retiros);

        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Cargando imagen", e.getMessage());
        }
    }

    private String base64(String path) {
        try {

            byte[] imagen = Files.readAllBytes(Path.of(path));

            String base64Img = Base64.getEncoder().encodeToString(imagen);
            System.out.println("path:" + "-----" + base64Img);
            return base64Img;

        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Convirtiendo a base 64", e.getMessage());
        }

    }

    @Override
    public void changeCerrado(Long id) {

        try {

            HanRetiros result = this.findById(id);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String cierre = LocalDateTime.now().format(formatter);

            result.setCierreFecha(cierre);

            result.setEstado("Cerrado");

            retirosDao.save(result);

        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Actualizado", e.getMessage());
        }

    }

    @Override
    public HanRetiros findById(Long id) {

        return retirosDao.findById(id).orElse(null);
    }

    @Override
    public ChartDataLineDTO chartLine(Integer ano, List<Integer> servicios) {

        try {

            Optional<List<Object[]>> result = retirosDao.chartLine(ano, servicios);

            String[] meses = { "Ene", "Feb", "Mar", "Abr", "May", "Jun",
                    "Jul", "Ago", "Sep", "Oct", "Nov", "Dic" };

            Integer[] datos = new Integer[12];

            if (result.isPresent()) {

                List<MeseCountDTO> resulset = result.map(resp -> {

                    List<MeseCountDTO> dto = new ArrayList<>();

                    for (Object[] rs : resp) {
                        MeseCountDTO obj = new MeseCountDTO();
                        obj.setId(rs[0].toString());
                        obj.setCantidad(rs[1].toString());
                        dto.add(obj);
                    }

                    return dto;
                }).orElse(new ArrayList<>());

                if (resulset.isEmpty()) {
                    // throw new ExceptionNullSql(new Date(), "Iterando Resulset", "Result vacio");
                    for (int i = 0; i < datos.length; i++) {
                        datos[i] = 0;
                    }

                } else {

                    for (int i = 0; i < meses.length; i++) {
                        int count = i + 1;
                        boolean encontrato = false;

                        for (MeseCountDTO rs : resulset) {
                            Integer validar = Integer.parseInt(rs.getId());

                            if (validar.equals(count)) {
                                datos[i] = Integer.parseInt(rs.getCantidad());
                                encontrato = true;
                                break;
                            }
                        }
                        // Si no se encuentra el valor en la consulta, lo pongo a cero
                        if (!encontrato) {

                            datos[i] = 0;

                        }

                    }

                }

            }
            List<String> listaMeses = new ArrayList<>(Arrays.asList(meses));
            List<Integer> listaDatos = new ArrayList<>(Arrays.asList(datos));

            ChartDataLineDTO chart = new ChartDataLineDTO();
            chart.setLabels(listaMeses);
            ChartDataLineDTO.Datasets datasets = new ChartDataLineDTO.Datasets();
            datasets.setLabel("Retiros por mes");
            datasets.setData(listaDatos);
            datasets.setFill(false);
            datasets.setBorderColor("#42A5F5");
            datasets.setTension(.4);
            List<ChartDataLineDTO.Datasets> listDatasets = new ArrayList<>();
            listDatasets.add(datasets);
            chart.setDatasets(listDatasets);

            return chart;

        } catch (Exception e) {
            throw new ExceptionNullSql(new Date(), "Chart Line", e.getMessage());
        }

    }

    @Override
    public List<ReporteHanRetirosDTO> reporteHanRetiros(List<Integer> servicios, String estado) {
        // TODO Auto-generated method stub

        Optional<List<Object[]>> result = retirosDao.reporteRetiros(servicios, estado);

        try {

            List<ReporteHanRetirosDTO> reporte = result.map(resp -> {
                List<ReporteHanRetirosDTO> repo = new ArrayList<>();

                for (Object[] rs : resp) {

                    ReporteHanRetirosDTO obj = new ReporteHanRetirosDTO();

                    obj.setId((Integer) rs[0]);
                    obj.setContrato((String) rs[1].toString());
                    obj.setServicio((String) rs[2]);
                    obj.setCliente((String) rs[3]);
                    obj.setDocumento((String) rs[4].toString());
                    obj.setBarrio((String) rs[5]);
                    obj.setDireccion((String) rs[6]);
                    repo.add(obj);
                }
                return repo;

            }).orElse(new ArrayList<>());

            return reporte;

        } catch (Exception e) {

            throw new ExceptionNullSql(new Date(), "Error generando reporte", e.getMessage());
        }
    }

    @Override
    public void sendMailRetiros(Long id , List<String> listMail) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'sendMailRetiros'");
        HanRetiros retiros = this.findById(id);
        // buscar el formato
        SystemConfig system = systemService.findByOrigen("mail_retiros");
        if (system == null) {
            throw new ExceptionNullSql(new Date(), "Consultado fondo Mail", "no se encontro registro");
        }

        String[] fondo = system.getComando().split("@");
        String fondoFinal = fondo[0] + "http://10.111.39.2/controlmas/v2/#/dashboard/clientes/"
                + retiros.getIdContrato()
                + "/retiro" + fondo[1];

        // user_mail_retiros
        EmailCampaignApi datos = emailCampaignApiService
                .findApiMailRelay(Integer.parseInt(retiros.getIdServicio().toString()));
        if (datos == null) {
            throw new ExceptionNullSql(new Date(), "Consultado datos de api", "no se encontro registro");
        }
        // consultamos lista de correos a enviar
        /*SystemConfig correos = systemService.findByOrigen("user_mail_retiros");
        if (correos == null) {
            throw new ExceptionNullSql(new Date(), "Consultado datos de correos a enviar", "no se encontro registro");
        }*/
        // consumir Clase MailRelaySend
        MailRelaySendMail mail = new MailRelaySendMail();
        // send
        MailRelaySendMail.Send principal = mail.new Send();
        // from
        MailRelaySendMail.From from = mail.new From();
        from.setEmail(datos.getMail_envio());
        from.setName(datos.getNombre_envio());
        principal.setFrom(from);

        // to
        List<MailRelaySendMail.To> listFrom = new ArrayList<>();

        //String[] arrayCorreo = correos.getComando().split(",");

        //List<String> listCorreos = Arrays.asList(arrayCorreo);

        for (String cor : listMail) {
            MailRelaySendMail.To to = mail.new To();
            to.setEmail(cor);
            to.setName("Destinatario");

            listFrom.add(to);
        }

        principal.setTo(listFrom);

        principal.setSubject("Retiro "+ retiros.getIdContrato() + " [" +retiros.getTipo().substring(0,10)+"]");
        principal.setHtml_part(fondoFinal);
        principal.setText_part("Notificación");
        principal.setTxt_part_auto(false);

        String url = datos.getUrl()+"send_emails";

        String resonseRelay = mailRelayService.mailRelaySend(principal, datos.getToken(), url);

    }

}
