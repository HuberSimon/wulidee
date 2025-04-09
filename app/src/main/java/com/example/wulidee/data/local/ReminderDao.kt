package com.example.wulidee.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE userId = :userId ORDER BY importantDate ASC")
    suspend fun getRemindersForUser(userId: Int): List<Reminder>

    @Query("SELECT COUNT(*) FROM reminders")
    fun getReminderCount(): Flow<Int>
}