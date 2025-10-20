package com.hjsolutions.msm_send.ui.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * {
 * "id": 202,
 * "login": "EGUE",
 * "firtsName": "EDWIN FABIAN",
 * "lastName": "EGÜE ALBARRACIN",
 * "email": "web@internetinalambrico.com.co",
 * "activated": true,
 * "langKey": "W5DEHCI5YSB7X2TS",
 * "imageUrl": "DSC_0064.JPG",
 * "resetDate": "2018-10-30T11:50:36Z",
 * "rol": "ROLE_ADMIN"
 * }
 */
@Entity(tableName = "user_session")
data class UserSession(
    @PrimaryKey
    val userId : String,
    val token : String,
    val login:String,
    val rol:String,
    val createdAt: Long = System.currentTimeMillis()
)
