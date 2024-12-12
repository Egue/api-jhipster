package com.comunicamosmas.api.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comunicamosmas.api.service.IFunctionGenerateService;
import com.comunicamosmas.api.service.dto.InfoFacturaDTO;

@Component
public class DeudasMapper {

    @Autowired
    private IFunctionGenerateService generateService;
    
    public InfoFacturaDTO.Deudas map(Object[] resp , StringBuilder idContrato , StringBuilder serializable , InfoFacturaDTO.Factura fact , InfoFacturaDTO.Resolucion resolucion)
    {
        InfoFacturaDTO.Deudas deuda = new InfoFacturaDTO.Deudas();
            deuda.setBase((Double) resp[0]);
            deuda.setIva( (Float) resp[1]);
            deuda.setParcial((Float) resp[2]);
            deuda.setTotal((Double) resp[3]);
            deuda.setServicio((String) resp[17]);
            deuda.setDireccion((String) resp[22]);
            deuda.setType(this.generateService.findTypeDeuda((Integer) resp[4] , (Integer) resp[5] , (Integer) resp[6] , (Integer) resp[7] , (Integer) resp[8], (String) resp[9] , (Integer) resp[10] ));

            fact.setNumero((String) resp[18].toString());
            fact.setEmision((String) resp[19].toString());

            resolucion.setResolucion((String) resp[11]);
            resolucion.setPrefijo((String) resp[12]);
            resolucion.setFecha((String) resp[13].toString());
            resolucion.setRangoinicial((String) resp[14]);
            resolucion.setRangofinal((String) resp[15]);
            resolucion.setVigencia((String) resp[16].toString());
            if(idContrato.length() == 0)
            {
                idContrato.append(resp[20]);
                serializable.append((resp[21]));
            }
            
            return deuda;
    }
}
