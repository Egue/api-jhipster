package com.hjsolutions.agente_inventory.service

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class HttpClient{
    private val client = OkHttpClient()


    fun get(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()
            
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string()
            }
        } catch (e: IOException) {
            println("Error en GET: ${e.message}")
            null
        }
    }
    
    // POST Request con JSON
    fun post(url: String, jsonBody: String): String? {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonBody.toRequestBody(mediaType)
        
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
            
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string()
            }
        } catch (e: IOException) {
            println("Error en POST: ${e.message}")
            null
        }
    }
    
    // PUT Request
    fun put(url: String, jsonBody: String): String? {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonBody.toRequestBody(mediaType)
        
        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()
            
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string()
            }
        } catch (e: IOException) {
            println("Error en PUT: ${e.message}")
            null
        }
    }
    
    // DELETE Request
    fun delete(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()
            
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string()
            }
        } catch (e: IOException) {
            println("Error en DELETE: ${e.message}")
            null
        }
    }
    
    // Request con headers personalizados
    fun getWithHeaders(url: String, headers: Map<String, String>): String? {
        val requestBuilder = Request.Builder().url(url)
        
        headers.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }
        
        val request = requestBuilder.build()
        
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                response.body?.string()
            }
        } catch (e: IOException) {
            println("Error en GET con headers: ${e.message}")
            null
        }
    }


}