package com.hjsolutions.isp_api.service

import io.appwrite.Client
import org.springframework.stereotype.Service

@Service
class AppWriteClient {

    private val APP_WRITE_PROJECT_ID = "69fc90d2000725013e58"
    private val APP_WRITE_DATA_BASE = "69fc98e1002137e724e8"
    private val APP_WRITE_KEY_DATABASE = "standard_acd715997d454a18f8e564f389c920236c39a81c1ff86d13cb66c697ce515fb02ae515c87f4417792bf53e2633e9d27c7700dd99cc0b1c2cc20fc2071bafa382c72109cef1d1a34e676568eb1f9416d50fd283385374e518b724bb081ece8bb5fb4f2da5b3f61ee0999a722a49bde53bf7dbf848c96e8a78d554ae5a9477df52"
    private val APP_WRITE_ENDPOINT = "http://2.24.81.107/v1"


    fun client(): Client {
        return Client()
            .setEndpoint(APP_WRITE_ENDPOINT)
            .setProject(APP_WRITE_PROJECT_ID)
            .setKey(APP_WRITE_KEY_DATABASE)

    }

    fun app_database():String{
        return APP_WRITE_DATA_BASE
    }
}
