package com.hjsolutions.isp_api.service

import javax.net.SocketFactory
import me.legrange.mikrotik.ApiConnection
import me.legrange.mikrotik.ApiConnectionException
import me.legrange.mikrotik.MikrotikApiException
import org.springframework.stereotype.Service

@Service
class MikrotikClient() {

    private var apiConnection: ApiConnection? = null

    fun getApiConnection(user: String, pass: String, ip: String, port: Int) {
        // Create a connection to the Mikrotik API
        try {
            apiConnection = ApiConnection.connect(SocketFactory.getDefault(), ip, port, 2000)
            apiConnection?.login(user, pass)
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
        } catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error logging in to Mikrotik: ${e.message}")
        }
        // return apiConnection
    }

    fun executeCommand(  user: String,   pass: String,   ip: String,      port: Int,   command: String): List<Map<String, String>> {
        val socketFactory = if (port == 8729) { javax.net.ssl.SSLSocketFactory.getDefault()
                } else {
                    SocketFactory.getDefault()
                }

        val connection = ApiConnection.connect(socketFactory, ip, port, 5000)
        connection.use {
            it.login(user, pass)
            return it.execute(command)
        }
    }

    fun get_mikrotik_String(comando: String): String {
        // Simulate fetching a string from Mikrotik
        try {

            val response: List<Map<String, String>> =
                    this.apiConnection?.execute(comando)
                            ?: throw IllegalStateException("API connection is not initialized")

            if (response.isEmpty()) {
                return "No data found"
            }
            val id = response[0]["id"] ?: "not found"

            return id
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
            // return ""
        } catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error executing command on Mikrotik: ${e.message}")
            // return ""
        }
    }

    fun get_mikrotik_list(comando: String): List<Map<String, String>> {
        // Simulate fetching a list from Mikrotik
        try {

            var response: List<Map<String, String>> =
                    this.apiConnection?.execute(comando)
                            ?: throw IllegalStateException("API connection is not initialized")

            return response
        } catch (e: ApiConnectionException) {
            e.printStackTrace()
            throw RuntimeException("Error connecting to Mikrotik: ${e.message}")
            // return ""
        } catch (e: MikrotikApiException) {
            e.printStackTrace()
            throw RuntimeException("Error executing command on Mikrotik: ${e.message}")
            // return ""
        }
    }

    fun closeConnection() {
        // Close the API connection
        this.apiConnection?.close()
        this.apiConnection = null
    }
}
