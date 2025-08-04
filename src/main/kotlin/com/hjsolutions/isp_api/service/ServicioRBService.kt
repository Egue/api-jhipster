package com.hjsolutions.isp_api.service
import org.springframework.stereotype.Service
import com.hjsolutions.isp_api.service.MikrotikClient
import com.comunicamosmas.api.service.IEstacionService
import com.comunicamosmas.api.domain.Estacion
import com.comunicamosmas.api.domain.Orden
import com.comunicamosmas.api.service.IOrdenService
import com.comunicamosmas.api.domain.Contrato
import com.comunicamosmas.api.service.IContratoService
import com.comunicamosmas.api.domain.Cliente
import com.comunicamosmas.api.service.IClienteService
import com.comunicamosmas.api.domain.Tarifa
import com.comunicamosmas.api.service.ITarifaService
import com.hjsolutions.isp_api.service.GlobalService
import com.comunicamosmas.api.service.IWinmaxPassService
import com.comunicamosmas.api.domain.WinmaxPass 
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ServicioRBService(
    private val mikrotikClient: MikrotikClient,
    private val estacionService: IEstacionService,
    private val ordenService: IOrdenService,
    private val contratoService: IContratoService,
    private val clienteService: IClienteService,
    private val tarifaService: ITarifaService,
    private val globalService: GlobalService,
    private val winmaxService: IWinmaxPassService

) {

    
    

    /*fun getSecretRB(user: String, pass: String, ip: String, port: Long, idOnu: Long): String {
        // Simulate fetching a secret from RB
        val comando = "/interface/winbox-interfaces/secret/get?id=${idOnu}"

        return mikrotikClient.get_mikrotik_String(user, pass, ip, port, comando)
    }*/

    // Create a secret in RB for a given station, order, and AP

    fun createSecretRB(idEstacion:Long , idOrden:Long , idAp:Long): String {
        val estacion: Estacion = estacionService.findById(idEstacion)
        
        var orden:Orden = ordenService.findById(idOrden)

        var contrato:Contrato = orden.let{ord -> 
            contratoService.findById(ord.idContrato)
        }
        var cliente:Cliente = contrato.let{cont -> 
            clienteService.findById(cont.idCliente)
        }   

        var tarifa:Tarifa = contrato.let{cont -> 
            tarifaService.findById(cont.idTarifaPromo)
        }
        orden.logApi = "Creando secret para el cliente ${cliente.id} - ${cliente.documento} - ${cliente.nombrePrimer} ${cliente.nombreSegundo} ${cliente.apellidoPaterno} ${cliente.apellidoMaterno} en la estacion ${estacion.nombre} con tarifa ${tarifa.nombre}"
         
        var profile:String = get_profile(estacion, tarifa.codigoMikrotik , orden)
        orden.logApi += " - Perfil encontrado: ${profile}"
        val user = estacion.apiUser
        val pass = estacion.apiPass
        val ip = estacion.apiIp
        val port = estacion.apiPort ?: 8728 // Default port for Mikrotik API
        // Simulate creating a secret in RB
        var nameCliente = cliente.let{cli -> 
            if(cli.tipoCliente == "N"){
                cli.nombrePrimer + " " + cli.nombreSegundo + " " + cli.apellidoPaterno + " " + cli.apellidoMaterno
            }else{
                cli.razonSocial
            }
        }
        var secretName = "${contrato.id}${globalService.get_letters_random(3)}"
        var secretPass = globalService.get_letters_random_numbers(7)
        var comment = globalService.getMD5Hash(input="${globalService.llave_internet_comentarios}${contrato.id}${secretName}${profile}")+
        " - ${cliente.id} - ${cliente.documento} - ${nameCliente} - Creo APIV2"
        
        val comando = "/ppp/secret/add name=\"${secretName}\" password=\"${secretPass}\" service=pppoe profile=\"${profile}\" comment=\"${comment}\""

        try {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            val formatted = current.format(formatter)
            mikrotikClient.getApiConnection(user, pass, ip, port.toInt())
            orden.logApi += " - Conectando a la estacion ${estacion.nombre}"
            var response = mikrotikClient.get_mikrotik_String(comando)
            orden.logApi += " - Secret creado: ${secretName} con  profile ${profile}"
            orden.logApi += " - Respuesta de Mikrotik: ${response}"
            contrato.idEstacion = idEstacion
            contrato.idApMaster = idAp
            contratoService.save(contrato)
            orden.logApi += " - Contrato actualizado con idEstacion: ${idEstacion} y idApMaster: ${idAp}"
            var winmaxPass:WinmaxPass = WinmaxPass()
            winmaxPass.idContrato = contrato.id
            winmaxPass.usuario = secretName
            winmaxPass.pass = secretPass
            winmaxPass.idEstacion = idEstacion
            winmaxPass.comentario = comment
            winmaxService.save(winmaxPass)
            orden.logApi += " - WinmaxPass creado:  con usuario ${secretName}"
            orden.winmax = 1L
            orden.winmaxIdUsuario = globalService.getCurrentUserID()
            orden.apiAutomatica = 1L
            orden.winmaxMarca = formatted
            ordenService.save(orden)
            mikrotikClient.closeConnection()
           return  response
        } catch (e: Exception) {
            orden.logApi += " - Error al crear el secret: ${e.message}"
            ordenService.save(orden)
            mikrotikClient.closeConnection()
            throw Exception("Error al crear el secret: ${e.message}")
        }
    }

    // Eliminate a secret in RB for a given station and order 

    fun eliminate_secretRB(idEstacion:Long , idOrden:Long): Boolean {
        val estacion: Estacion = estacionService.findById(idEstacion)
        var orden:Orden = ordenService.findById(idOrden)
        var contrato:Contrato = orden.let{ord -> 
            contratoService.findById(ord.idContrato)
        }
        var winmaxPass:WinmaxPass? = orden.let{ord ->
            winmaxService.findByIdContrato(ord.idContrato)
        }
        if(winmaxPass == null){
            orden.logApi += " - No se ha encontrado el WinmaxPass para la orden ${orden.id}"
            ordenService.save(orden)
            throw Exception("No se ha encontrado el WinmaxPass para la orden ${orden.id}")
        }
         
        var id = get_secret_RB(estacion, winmaxPass.usuario, orden)
        orden.logApi += " - Secret encontrado: ${id} para el usuario ${winmaxPass.usuario}"
        //eliminar el secret en RB
        var commando:String = "/ppp/secret/remove id=${id}"
        var user = estacion.apiUser
        var pass = estacion.apiPass
        var ip = estacion.apiIp
        var port = estacion.apiPort ?: 8728 // Default port for Mikrotik API
        var commando_eliminate_active:String = "/ppp/active/remove id=${id}"
        try{
            mikrotikClient.getApiConnection(user, pass, ip, port.toInt())
            orden.logApi += " - Conectando a la estacion ${estacion.nombre}"
             mikrotikClient.get_mikrotik_list(commando)
            orden.logApi += " - Secret eliminado: ${winmaxPass.usuario} en la estacion ${estacion.nombre}"
            mikrotikClient.get_mikrotik_list(commando_eliminate_active)
            orden.logApi += " - Secret activo eliminado: ${winmaxPass.usuario} en la estacion ${estacion.nombre}"
            //actualizar contrato
            globalService.changeStatusContrato(contrato, 4L)
            contrato.estado = 4L
            contratoService.save(contrato)
            orden.logApi += " - Contrato actualizado a estado 4 (Eliminado) para la orden ${orden.id}"
            orden.logApi += " - Actualizando orden ${orden.id}"
            orden.winmax = 1L
            orden.winmaxIdUsuario = globalService.getCurrentUserID()
            orden.winmaxMarca = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
            ordenService.save(orden)

            return true

        }catch(e:Exception){
            orden.logApi += " - Error al eliminar el secret ${winmaxPass.usuario} en la estacion ${estacion.nombre}: ${e.message}"
            ordenService.save(orden)
            throw Exception("Error al eliminar el secret ${winmaxPass.usuario} en la estacion ${estacion.nombre}: ${e.message}")
        }
          
    }

    fun get_secret_RB(estacion:Estacion , secret:String , orden:Orden): String {

        var comando:String = "/ppp/secret/print where name=\"${secret}\""
        try {
            var response = mikrotikClient.get_mikrotik_String(comando)
            if(response.equals("No data found")){
                orden.logApi += " - No se ha encontrado el secret ${secret} en la estacion ${estacion.nombre}"
                ordenService.save(orden)
                throw Exception("No se ha encontrado el secret ${secret} en la estacion ${estacion.nombre}")
            }

            return response
        }
        catch(e:Exception){
            orden.logApi += " - Error al obtener el secret ${secret} en la estacion ${estacion.nombre}: ${e.message}"
            ordenService.save(orden) 
            
        }

         throw Exception("Error al obtener el secret ${secret} en la estacion ${estacion.nombre}")

    }

    fun get_profile(estacion:Estacion , profile:String , orden:Orden):String{
        var comando:String = "ppp/profile/print where name=\"${profile}\""
        var response = mikrotikClient.get_mikrotik_list(comando)
        if(response.isEmpty()){
            orden.logApi += " - No se ha encontrado el perfil ${profile} en la estacion ${estacion.nombre}"
            ordenService.save(orden)
            throw Exception("No se ha encontrado el perfil ${profile} en la estacion ${estacion.nombre}")
        }

        var name:String = response[0]["name"] ?: throw Exception("No se ha encontrado el name del perfil ${profile} en la estacion ${estacion.nombre}")
        return name

    }
}
