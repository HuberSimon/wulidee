package com.example.wulidee.data

import com.example.wulidee.data.local.Reminder
import com.example.wulidee.data.local.ReminderDao
import com.example.wulidee.data.local.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ReminderRepository(private val reminderDao: ReminderDao) {

    val reminderCount: Flow<Int> = reminderDao.getReminderCount()

    suspend fun addReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            reminderDao.insertReminder(reminder)
        }
    }

    suspend fun getRemindersForUser(userId: Int): List<Reminder> {
        return withContext(Dispatchers.IO) {
            reminderDao.getRemindersForUser(userId)
        }
    }
}
