package com.hjsolutions.isp_api.service

import io.appwrite.Client
import org.springframework.stereotype.Service

@Service
class AppWriteClient {

    private val APP_WRITE_PROJECT_ID = "68bceb260025a20cf69f"
    private val APP_WRITE_DATA_BASE = "68bdf0c3003e769ff3be"
    private val APP_WRITE_KEY_DATABASE = "standard_871a17ea8107a75a7fae408100279588605fd30102855ed802a65ffb50d37f006b0e22a4a3c0a50dca522aa1d0f562be7c9f9fbe47838690b13804b3c07b437145bf18918369e31ec2d02888752a4a9449308b5aa6de5a397992f679316ed3e37ed5ef71a91db74cbd26d7988fd305377d9ee3e62043d9b1c80418c3f533c2f1"
    private val APP_WRITE_ENDPOINT = "https://nyc.cloud.appwrite.io/v1"


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
