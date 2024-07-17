package com.comunicamosmas.api.service.impl;
 

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.domain.PagoLineaVersionDos;
import com.comunicamosmas.api.repository.IPagoLineaVersionDosDao;
import com.comunicamosmas.api.service.IContratoAddService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.IPagoLineaVersionDosService;

@Service
public class PagoLineaVersionDosServiceImpl implements IPagoLineaVersionDosService{

    private final IPagoLineaVersionDosDao pagoLineaVersionDosDao;

    private final IContratoService contratoService;

    private final IOrdenService ordenService;

    public PagoLineaVersionDosServiceImpl(IPagoLineaVersionDosDao pagoLineaVersionDosDao , IContratoService contratoService,IOrdenService ordenService)
    {
        this.pagoLineaVersionDosDao = pagoLineaVersionDosDao;
        this.contratoService = contratoService; 
        this.ordenService = ordenService;
    }

    @Override
    public Page<PagoLineaVersionDos> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void save(PagoLineaVersionDos pagoLineaVersionDos) {
        // TODO Auto-generated method stub
        pagoLineaVersionDosDao.save(pagoLineaVersionDos);
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public PagoLineaVersionDos findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void iterarReconexionesAndCorte() {
        // TODO Auto-generated method stub
         Optional<List<PagoLineaVersionDos>> optionalPagos = pagoLineaVersionDosDao.findByEjecutadoAndGroupByIdContrato(0L);

         if(optionalPagos.isPresent())
         {
            List<PagoLineaVersionDos> list = optionalPagos.get();

            list.stream().forEach(p -> {
                Contrato contrato = contratoService.findById(p.getIdContrato());

                if(contrato.getEstado() == 2)
                {
                    Orden reconexion = ordenService.findOrdenActivaByTipo(3L, contrato.getId());

                    if(reconexion == null)
                    {
                        String comentario = "Pago por PSE se crea automaticamente";

						ordenService.reconexion(contrato, comentario);
                    }
                }else if(contrato.getEstado() == 1)
                {
                    Orden corteActiva = ordenService.findOrdenActivaByTipo(2L, contrato.getId());
					if (corteActiva != null) {
						// anular orde
						String comment = "Anulada por pago supergiros antes de ser asignada";

						ordenService.anularOrden(corteActiva, comment);

					}
                }

                p.setEjecutado(1L);
                pagoLineaVersionDosDao.save(p);
            });
         }
    }

    
    
}
