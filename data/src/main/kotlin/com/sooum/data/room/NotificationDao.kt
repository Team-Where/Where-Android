package com.sooum.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sooum.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationItem)

    @Query("SELECT * FROM notification ORDER BY receiveTime DESC")
    fun getAllNotifications(): Flow<List<NotificationItem>>
}