package com.comunicamosmas.api.service.impl;
 
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale; 
import java.util.Optional; 
import java.util.stream.Collectors; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.domainMongo.FacturasEmitidas;
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.repository.IContratoDao;
import com.comunicamosmas.api.repository.IDeudaDao;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.repository.IEmailCampanignDao;
import com.comunicamosmas.api.repository.IEmpresaDao;
import com.comunicamosmas.api.service.IContratoComboService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IFacturasServices;
import com.comunicamosmas.api.service.IFunctionGenerateService;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO; 
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO.FacturaSendMail;
import com.comunicamosmas.api.service.mapper.DeudasMapper;
import com.comunicamosmas.api.service.dto.InfoFacturaDTO; 

@Service
public class FacturasServiceImpl implements IFacturasServices{

    private final IDeudaDao deudaDao;

    private final IClienteDao clienteDao;
 
    private final IEmailCampanignDao emailCampanignDao;

    private final IEmailCampaignDetalleDao emailCampaignDetalleDao;
 
    private final IEmpresaDao empresaDao;

    private final IContratoService contratoService;
 
    private final IContratoComboService contratoComboService;

    private final DeudasMapper deudasMapper;

    private final IContratoDao contratoDao;

    private final IFunctionGenerateService generateService;

    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO")); 

    public FacturasServiceImpl(IContratoDao contratoDao, IDeudaDao deudaDao , IClienteDao clienteDao , IEmailCampaignDetalleDao emailCampaignDetalleDao, 
    IEmpresaDao empresaDao ,IEmailCampanignDao emailCampanignDao ,   IContratoService contratoService , DeudasMapper deudasMapper ,IContratoComboService contratoComboService ,
    IFunctionGenerateService generateService)
    {
        this.deudaDao = deudaDao;

        this.clienteDao = clienteDao; 

        this.emailCampaignDetalleDao = emailCampaignDetalleDao;

        this.empresaDao = empresaDao;

        this.emailCampanignDao = emailCampanignDao;
 

        this.contratoService = contratoService;

        this.deudasMapper = deudasMapper;

        this.contratoComboService = contratoComboService;

        this.contratoDao = contratoDao;

        this.generateService = generateService;
    }

    @Override
    //@Cacheable("findFacturaByMouth")
    public FacturasControlmasDTO.FacturasPendientes findFacturaByMouth(Long idCliente) {
        // TODO Auto-generated method stub
        //consultar los contratos facturados
        Optional<Cliente> cliente = clienteDao.findById(idCliente);

        if(cliente.isPresent())
        {

            //buscar deudas
            Optional<List<Deuda>> deudas = deudaDao.findActiveDebtsWithInvoiceByClientId(cliente.get().getId());
            
            FacturasControlmasDTO.FacturasPendientes facturasPendientes =  new FacturasControlmasDTO.FacturasPendientes();
            facturasPendientes.setCorreo(cliente.get().getMail());
            facturasPendientes.setDocumento(cliente.get().getDocumento().toString());
            facturasPendientes.setId_cliente(cliente.get().getId());
            facturasPendientes.setTelefono(cliente.get().getCelularA());
            if(cliente.get().getTipoCliente().equals("J"))
            {
                facturasPendientes.setNombres(cliente.get().getRazonSocial());
            }else{
                facturasPendientes.setNombres(cliente.get().getApellidoPaterno() + " " + cliente.get().getApellidoMaterno() + " " +cliente.get().getNombrePrimer());
            }

            //agrupar por factura
            List<Deuda> groupFactura = deudas
                        .map(listDeudas -> listDeudas.stream()
                            .collect(Collectors.toMap(
                                    Deuda::getFactura, 
                                    deuda -> deuda, (deudaExistente , nuevaDeuda) -> deudaExistente))
                            .values()
                            .stream()
                            .collect(Collectors.toList())
                        ).orElse(Collections.emptyList());
            
             
            //recorrer lista original
            List<FacturasControlmasDTO.FacturasDebe> facturasDebes = groupFactura.stream().map(deuda ->{
                FacturasControlmasDTO.FacturasDebe  facturaDebe = new FacturasControlmasDTO.FacturasDebe();
                //Filtrar las deudas por Factura 
                List<Deuda> deudaByInvoice = deudas.get().stream().filter(s -> s.getFactura().equals(deuda.getFactura())).collect(Collectors.toList());

                //suma los valores parciales y totales
                double total = deudaByInvoice.stream()
                            .mapToDouble(Deuda::getValorTotal)
                            .sum();
                //parciales
                double parcial = deudaByInvoice.stream()
                                .mapToDouble(Deuda::getValorParcial)
                                .sum();

                facturaDebe.setFactura(deuda.getFactura());
                facturaDebe.setLado(deuda.getRefiere());
                facturaDebe.setId_empresa(deuda.getIdEmpresa());
                facturaDebe.setFacturado_mes(deuda.getFacturadoFecha());

                double diferencia = total - parcial;
                String formatDiferencia = String.format("%.2f", diferencia); 
                facturaDebe.setValor(formatDiferencia);
                String mes_servicio = transformDateToString(deuda.getMesServicio());
                facturaDebe.setConcepto("Servicio mes de "+mes_servicio);
                return facturaDebe;
            }).collect(Collectors.toList());
            

            facturasPendientes.setFacturas(facturasDebes);
            
          

            return facturasPendientes;
        }
        return null;
    }

    private String transformDateToString(Long mes)
    {
        YearMonth yearMonth = YearMonth.parse(mes.toString(), DateTimeFormatter.ofPattern("yyyyMM"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("es" , "ES"));

        String formattString = yearMonth.format(formatter);

        return formattString;
    }

    @Override
    public void sendMailFactura(FacturaSendMail facturaSendMail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMailFactura'");
         
    }

    //@Cacheable(value="findListFacturaByIdCliente" , keyGenerator = "keyGenerator")
    @Override
    public List<EmailCampaignDetalleDTO> findListFacturaByIdCliente(Long idCliente , Pageable page) {
        // TODO Auto-generated method stub 

         Page<Object[]> listInvoice = emailCampaignDetalleDao.findListByIdClienteAndService(idCliente , page);

        return listInvoice.map(this::converterListEmailCampaignDetalleDTO).getContent();
    }

    private EmailCampaignDetalleDTO converterListEmailCampaignDetalleDTO(Object[] object){

        EmailCampaignDetalleDTO detalle = new EmailCampaignDetalleDTO();

        detalle.setId((Integer) object[0]);
        detalle.setFactura((String) object[1]);
        detalle.setIdCliente((Integer) object[2]);
        detalle.setEmail((String) object[3]);
        detalle.setIdCampaign((Integer) object[7]);
        detalle.setOrigen((String) object[8]);
        detalle.setNombreServicio((String) object[10]);
        detalle.setFecha_limite((String) object[11]);
        detalle.setFecha_corte((String) object[12]);;
        return detalle;
    }

    @Override
    public InfoFacturaDTO informationInFactura(FacturasEmitidas factura) {
        // TODO Auto-generated method stub
        final StringBuilder mesServicio = new StringBuilder();
        final StringBuilder idContrato = new StringBuilder();
        final StringBuilder serializable = new StringBuilder();
        final StringBuilder periodoFacturado = new StringBuilder();
        //list Deudas de la factura
        Optional<List<Object[]>> object = emailCampanignDao.findById(factura.getIdCampaign())
        .map(exist -> {
            mesServicio.append(exist.getAnno()+exist.getMes());
            return  empresaDao.findDeudasAndEmpresaAndResolucionForCombo(factura.getIdCliente(), factura.getFactura(), factura.getOrigen() , mesServicio.toString());
        }).orElseGet(() -> { 
            return Optional.empty();
        });         

        InfoFacturaDTO infoDacDto = new InfoFacturaDTO();
        InfoFacturaDTO.Factura fact = new  InfoFacturaDTO.Factura();
        InfoFacturaDTO.Resolucion resolucion = new InfoFacturaDTO.Resolucion();
        InfoFacturaDTO.Cliente cliente = new InfoFacturaDTO.Cliente();
        InfoFacturaDTO.Empresa empre = new InfoFacturaDTO.Empresa();
 
        //recuperar las deudas de la factura
        List<InfoFacturaDTO.Deudas> listDeudas = object.map( (List<Object[]> list) -> 
            list.stream()
                .map((Object[] resp) -> deudasMapper.map(resp, idContrato, serializable, fact, resolucion , periodoFacturado))
                .collect(Collectors.toList()) //
        ).orElse(List.of());
        //recuperar info de contrato
        
        List<Integer> listContrato = contratoDao.findById(Long.parseLong(idContrato.toString()))
        .map(contrato -> {
            fact.setOrigen(contrato.getGrupo());

            if (contrato.getCombo().equals(1)) {
                
                return this.contratoComboService.findByEmpresaAndIdCombo(contrato.getIdEmpresa(), contrato.getIdCombo())
                        .map(list -> list.stream()
                                .map(resp -> (Integer) resp[0])
                                .collect(Collectors.toList()))
                        .orElseGet(ArrayList::new); 
            } else {
                return List.of(Integer.parseInt(contrato.getId().toString()));
            }
        })
        .orElseGet(ArrayList::new);
        //buscar saldo anteriores de cada combo
        Optional<List<Object[]>> listDeudasAnterior = deudaDao.saldoAnterior(Integer.valueOf(fact.getEmision()), listContrato);
        //sumar parcial
        Float parcialAnterior = listDeudasAnterior.map(list -> 
                    list.stream()
                        .map(resp -> ((Number) resp[1]).floatValue())
                        .reduce(0f, Float::sum))
                    .orElse(0f);
        //sumar total
        Double totalAnterior = listDeudasAnterior.map(list -> list.stream()
                        .mapToDouble(resp-> ((Number) resp[2]).doubleValue()).sum()).orElse(0.0);
        //diferecnia de total - parcial
        Double saldoAnterior = totalAnterior - parcialAnterior;
        //armar clase response
        ContratoInfoFacturaDTO infoCliente = contratoService.contratoFindFactura(Long.parseLong(idContrato.toString()));
        cliente.setDireccion(infoCliente.getDireccion());
        cliente.setDocumento(infoCliente.getDocumento());
        cliente.setNombres(infoCliente.getNombreCliente());
        cliente.setTelefonos(infoCliente.getCelular());
        infoDacDto.setCliente(cliente);

        Empresa empresa = empresaDao.findEmpresaByContrato(Long.parseLong(idContrato.toString()));
        empre.setDireccion(empresa.getDireccion());
        empre.setNit(empresa.getNit().toString());
        empre.setNombre(empresa.getRazonSocial());
        empre.setIndicativo(empresa.getDv().toString());
        empre.setTelefono(empresa.getTelefonos());
        empre.setUrl(empre.getUrl());
        infoDacDto.setEmpresa(empre);
        //create Deudas
        infoDacDto.setDeudas(listDeudas);
        //set info factura
        fact.setFechaCorte(factura.getFecha_corte());
        fact.setFechaLimite(factura.getFecha_limite());
        fact.setResolucion(resolucion);
        fact.setSerializable(serializable.toString());
        fact.setPeriodo(this.generateService.GenerateMensualidad(Integer.parseInt(periodoFacturado.toString())));
        //add factura
        infoDacDto.setFactura(fact);
         //sumar base , sumar iva
         Double totalBase = listDeudas.stream().mapToDouble(deuda->deuda.getBase()).sum();
         Double totalIva = listDeudas.stream().mapToDouble(deuda->deuda.getIva()).sum();
         Double parcial = listDeudas.stream().mapToDouble(deuda->deuda.getParcial()).sum();
         Double totalMes = listDeudas.stream().mapToDouble(deuda->deuda.getTotal()).sum();
        infoDacDto.setIva(formatter.format(totalIva).toString());
        infoDacDto.setSubTotal(formatter.format(totalBase).toString());
        infoDacDto.setPagosaCuenta(formatter.format(parcial).toString());
        infoDacDto.setTotalMes(formatter.format(totalBase + totalIva).toString());
        infoDacDto.setSaldoAnterior(formatter.format(saldoAnterior).toString());
        infoDacDto.setTotal(formatter.format(totalMes + saldoAnterior - parcial).toString());
        return infoDacDto;
    }

    
    
}
