package com.hjsolutions.isp_api.service
import  org.springframework.stereotype.Service
import com.comunicamosmas.api.service.IContratoService
import com.comunicamosmas.api.service.IEstacionService
import com.comunicamosmas.api.domain.Estacion
import com.comunicamosmas.api.service.IWinmaxPassService
import com.comunicamosmas.api.domain.WinmaxPass
import com.comunicamosmas.api.service.ISystemConfigService
import com.hjsolutions.isp_api.service.dto.ConfigurationOLT
import com.hjsolutions.isp_api.service.dto.EndPointOLT
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference

@Service
class ServicioOLTService(
    private val contratoService: IContratoService,
    private val estacionService: IEstacionService,
    private val winmax:IWinmaxPassService,
    private val systemService:ISystemConfigService
) {

    fun get_unconfigured_onus(id_estacion:Long , endpoint:String = "unconfigured") : String {
        ///api/system/get_speed_profiles listar perfiles
        
        var httpCliente = HttpClient()

        //infoestacion
        var estacion: Estacion? = estacionService.findById(id_estacion)
        if(estacion == null){
            throw Exception("Estacion no encontrada en ${id_estacion}")
        }

        var system = systemService.findByOrigen("configuration_olt")
        if(system == null){
            throw Exception("No se ha configurado el sistema de OLT")
        }
        var configOltString = system.let{it.comando}
        if(configOltString == null || configOltString.isEmpty()){
            throw Exception("No se ha configurado el sistema de OLT")
        }
        var mapper = ObjectMapper()

        val configOltList: List<ConfigurationOLT> = mapper.readValue(configOltString, object : TypeReference<List<ConfigurationOLT>>() {})

        var configOlt: ConfigurationOLT? = configOltList.find { it.type == estacion.type }
        
        if(configOlt == null){
            throw Exception("No se ha configurado el sistema de OLT para el tipo ${estacion.type}")
        }

        var endpointOlt:EndPointOLT? = configOlt.endpoint.find { it?.name == endpoint }
        if(endpointOlt == null){
            throw Exception("No se ha configurado el endpoint ${endpoint} para el tipo ${estacion.type}")
        }
        var url_completa = "${estacion.apiUser}${endpointOlt.endpoint}"
        var headers = HashMap<String,String>()
        headers.put("X-Token" , estacion.apiPass)
        var response = httpCliente.getWithHeaders(url_completa ,  headers)


        return response ?: throw Exception("No response received from OLT endpoint")

         
    }

    fun cortarCliente(cus:Long){

        var httpCliente = HttpClient()

        //infoestacion
        var contrato = contratoService.findById(cus)

        var id_estacion = contrato.let { it.idEstacion }

        if(id_estacion != null)
        {
            var estacion:Estacion = estacionService.findById(id_estacion)

            if(estacion.type.equals("olt")){
                //
                var winmax : WinmaxPass = winmax.findByIdContrato(cus)
                if(winmax.usuario != null)
                {
                    var url :String = estacion.apiIp //https://cablemag.vortex-m2.com/
                    var token : String = estacion.apiPass
                    var url_completa = "${url}/api/onu/disable/${winmax.usuario}"
                    var headers = HashMap<String,String>()
                    headers.put("X-Token" , token)
                    var response = httpCliente.getWithHeaders(url_completa ,  headers)
                }
                //usar credentials en system
                

            }

        }else{

            throw Exception("Estacion no encontrada en ${cus}")
            
        }
    }



}