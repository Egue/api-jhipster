package com.comunicamosmas.api.cron;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled; 

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Estacion;
import com.comunicamosmas.api.domain.Orden;
import com.comunicamosmas.api.service.IApiRestService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IEstacionService;
import com.comunicamosmas.api.service.IOrdenService;
import com.comunicamosmas.api.service.IPagoLineaVersionDosService;
import com.comunicamosmas.api.service.IPaymentOnlineService;
 
public class ReconexionCron {
	
	@Autowired
	IOrdenService ordenService;
	
	@Autowired
	IEstacionService estacionService;
	
	@Autowired
	IContratoService contratoService;
	
	@Autowired
	IApiRestService apiService;

	@Autowired
	IPaymentOnlineService paymentOnlineService;

	@Autowired
	IPagoLineaVersionDosService pagoLineaVersionDosService;
	
	//@Scheduled(cron ="*/20 * * * * *")
	public void reconectar()
	{
		
		 
		try {
			List<Orden> result = ordenService.findAllByTipo(3L);	
			for(Orden rs : result)
			{
				
				/**
				 * 1. buscar la estación por el contrato*/
				Contrato contrato = contratoService.findById(rs.getIdContrato());
				/**
				 * 2. cada contrato tiene una estación asociada , se buscar la información de la estacion*/
				Estacion estacion = estacionService.findById(contrato.getIdEstacion());
				
				try {
					//conectar a Rb y buscar el 
				}catch(Exception e)
				{
					
				}
				
			}
			
		}catch(Exception e)
		{
			System.out.print(e.getMessage());
		}
		 
			
	}
	
	//@Scheduled(cron ="0 0/30 * * * ?")//cada hora
	//@Scheduled(cron ="0 0/16 * * * *")
	//@Scheduled(cron = "0 53 11,23 * * *")//
	//@Scheduled(cron = "0 16 08 * * *")//
	public void pagosSupergiros()
	{
		try {
			
			apiService.pagosSupergiros();
			
		}catch(Exception e)
		{
			System.out.print(e.getMessage());
		}
	}

	/*
	 * download pagos pse v2
	 * save to log 
	 */
	//@Scheduled(cron = "0 0/15 * * * *")
	//@Scheduled(cron = "0 41 15 * * *")
	public void downloadPaymentOnline()
	{
		try {

			paymentOnlineService.downloadPaymentOnline();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	//@Scheduled(cron = "0 0/20 * * * *")
	public void reconexionAndAnulationCortePse()
	{
		try {
			pagoLineaVersionDosService.iterarReconexionesAndCorte();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 53 07 * * *")
	public void prueba()
	{
		System.out.println("hola");
	}
}
