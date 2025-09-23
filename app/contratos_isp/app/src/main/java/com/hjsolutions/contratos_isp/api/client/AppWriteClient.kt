package com.hjsolutions.contratos_isp.api.client

import android.content.Context
import com.hjsolutions.contratos_isp.constants.APPWRITE_PROJECT_ID
import com.hjsolutions.contratos_isp.constants.APPWRITE_PUBLIC_ENDPOINT
import io.appwrite.Client
import io.appwrite.services.Account

object AppWriteClient {

    private var _client: Client? = null
    private var _account: Account? =null

    fun init(context: Context){
        _client = Client(context)
            .setEndpoint(APPWRITE_PUBLIC_ENDPOINT)
            .setProject(APPWRITE_PROJECT_ID)

    _account = Account(_client!!)
    }

val client : Client
    get() = _client ?: throw IllegalArgumentException("Appwrite not inizialite")

val account : Account
    get() = _account ?: throw IllegalArgumentException("Appwrtie not inizialite")
}
