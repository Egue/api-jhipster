package com.hjsolutions.isp_api.service
import org.springframework.stereotype.Service
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.ApiConnectionException;
import me.legrange.mikrotik.MikrotikApiException;
import javax.net.SocketFactory
import com.comunicamosmas.api.service.IOrdenService
import com.comunicamosmas.api.domain.Orden

@Service
class MikrotikClient() {

    fun get_mikrotik_String(user:String , pass:String , ip:String , port:Long , comando:String): String {
        // Simulate fetching a string from Mikrotik
       try {
        var apiConnection:ApiConnection = ApiConnection.connect(SocketFactory.getDefault() , ip, port.toInt() , 2000)
        apiConnection.login(user, pass) 
        val response:List<Map<String,String>> = apiConnection.execute(comando)
        
        apiConnection.close()
        if (response.isEmpty()) {
            return "No data found"
        }
        val id = response[0]["id"] ?: "not found"

        return id
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
            //return ""
        }catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error executing command on Mikrotik: ${e.message}")
            //return ""
        }
 
    }

    fun get_mikrotik_list(user:String , pass:String , ip:String , port:Long , comando:String): List<Map<String,String>> {
        // Simulate fetching a list from Mikrotik
        try {
            var apiConnection:ApiConnection = ApiConnection.connect(SocketFactory.getDefault() , ip, port.toInt() , 2000)
            apiConnection.login(user, pass) 
            val response:List<Map<String,String>> = apiConnection.execute(comando)         
            
            apiConnection.close()
            return response
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
            //return ""
        }catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error executing command on Mikrotik: ${e.message}")
            //return ""
        }
    }

     

     

}