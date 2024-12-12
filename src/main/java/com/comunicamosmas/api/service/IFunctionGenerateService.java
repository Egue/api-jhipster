package com.comunicamosmas.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import net.bytebuddy.asm.Advice.This;
@Service
public class IFunctionGenerateService {
    
    private String _INSTALACION = "Instalación";
    private String _RECONEXION = "Reconexión";
    private String _MATERIALES = "Materiales";
    private String _TRASLADO = "Traslado";
    private String _MENSUALIDAD = "Mensualidad";


    public String findTypeDeuda(Integer instalacion, Integer reconexion , Integer materiales, Integer traslado , Integer otros, String concepto , Integer mesServicio)
    {

        if(instalacion.equals(1))
        {
            return _INSTALACION + " / " + this.GenerateMensualidad(mesServicio);
        }else if(reconexion.equals(1))
        {
            return _RECONEXION + " / " + this.GenerateMensualidad(mesServicio);
        }else if(materiales.equals(1))
        {
            return _MATERIALES + " / " + this.GenerateMensualidad(mesServicio);
        }else if(traslado.equals(1))
        {
            return _TRASLADO + " / " + this.GenerateMensualidad(mesServicio);
        }else if(otros.equals(1))
        {
            return concepto + " / " + this.GenerateMensualidad(mesServicio);
        }else{
            return _MENSUALIDAD + " / " + this.GenerateMensualidad(mesServicio);
        }
    }

    public String GenerateMensualidad(Integer mensualidad){
        String convert = mensualidad.toString();
		String fechaSalida = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		try {
			Date fechaDate = dateFormat.parse(convert);
			SimpleDateFormat dateformatSalid = new SimpleDateFormat("MMMM-yyyy");
			fechaSalida = dateformatSalid.format(fechaDate);
		} catch (ParseException e) {

			throw new ExceptionNullSql(new Date(), "Transformando Fecha Integer", e.getMessage());
		}

		return fechaSalida;
    }
}
