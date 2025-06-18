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

    private var apiConnection: ApiConnection? = null

      

    fun getApiConnection(user:String , pass:String , ip:String, port:Long) {
        // Create a connection to the Mikrotik API
        try {
        this.apiConnection =  ApiConnection.connect(SocketFactory.getDefault() , ip , port.toInt() , 2000)
        this.apiConnection?.login(user, pass)
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
        } catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error logging in to Mikrotik: ${e.message}")
        }
        //return apiConnection
    }

    fun get_mikrotik_String(comando:String): String {
        // Simulate fetching a string from Mikrotik
       try {

        val response: List<Map<String, String>> = this.apiConnection?.execute(comando)
            ?: throw IllegalStateException("API connection is not initialized")
        
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

    fun get_mikrotik_list(comando:String): List<Map<String,String>> {
        // Simulate fetching a list from Mikrotik
        try {
             
            var response:List<Map<String,String>> = this.apiConnection?.execute(comando)   
                ?:
                    throw IllegalStateException("API connection is not initialized")
            
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

    fun closeConnection() {
        // Close the API connection
        this.apiConnection?.close()
        this.apiConnection = null
    }

     

     

}