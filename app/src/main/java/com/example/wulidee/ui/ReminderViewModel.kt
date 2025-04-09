package com.example.wulidee.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wulidee.data.ReminderRepository
import com.example.wulidee.data.local.Reminder
import com.example.wulidee.data.local.ReminderDao
import com.example.wulidee.data.local.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class ReminderViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val reminderCount: StateFlow<Int?> = reminderRepository.reminderCount
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            0
        )

    fun addReminder(userId: Int, description: String, importantDate: Date) {
        val reminder = Reminder(description = description, userId = userId, importantDate = importantDate)
        viewModelScope.launch {
            reminderRepository.addReminder(reminder)
        }
    }

    fun getRemindersForUser(userId: Int, onRemindersFetched: (List<Reminder>) -> Unit) {
        viewModelScope.launch {
            val reminders = reminderRepository.getRemindersForUser(userId)
            onRemindersFetched(reminders)
        }
    }
}
