package com.hjsolutions.msm_send.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjsolutions.msm_send.ui.data.local.entities.UserSession
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM user_session LIMIT 1")
    suspend fun getCurrentSession(): UserSession?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserSession)

    @Query("UPDATE user_session SET token = :newToken")
    suspend fun updateToken(newToken: String)

    @Query("SELECT COUNT(*) > 0 FROM user_session")
    suspend fun hasActiveSession(): Boolean

    @Query("DELETE FROM user_session")
    suspend fun clearSession()
}
