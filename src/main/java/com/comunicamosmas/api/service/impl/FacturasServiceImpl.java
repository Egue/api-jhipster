package com.comunicamosmas.api.service.impl;
 
import java.time.YearMonth;
import java.time.format.DateTimeFormatter; 
import java.util.Collections;
import java.util.List;
import java.util.Locale; 
import java.util.Optional; 
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Cliente;
import com.comunicamosmas.api.domain.Deuda; 
import com.comunicamosmas.api.repository.IClienteDao;
import com.comunicamosmas.api.repository.IDeudaDao;
import com.comunicamosmas.api.repository.IEmailCampaignDetalleDao;
import com.comunicamosmas.api.repository.IEmailCampanignDao;
import com.comunicamosmas.api.service.IEmailCampaignDetalleService;
import com.comunicamosmas.api.service.IFacturasServices; 
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO; 
import com.comunicamosmas.api.service.dto.FacturasControlmasDTO.FacturaSendMail; 

@Service
public class FacturasServiceImpl implements IFacturasServices{

    private final IDeudaDao deudaDao;

    private final IClienteDao clienteDao;
 

    private final IEmailCampaignDetalleDao emailCampaignDetalleDao;
 

    public FacturasServiceImpl(IDeudaDao deudaDao , IClienteDao clienteDao , IEmailCampaignDetalleDao emailCampaignDetalleDao)
    {
        this.deudaDao = deudaDao;

        this.clienteDao = clienteDao; 

        this.emailCampaignDetalleDao = emailCampaignDetalleDao;
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

    
    
}
