package com.example.wulidee.ui

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wulidee.data.IdeaRepository
import com.example.wulidee.data.local.Idea
import com.example.wulidee.data.local.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IdeaViewModel(private val repository: IdeaRepository) : ViewModel() {

    private val _userIdeas = MutableStateFlow<List<Idea>>(emptyList())
    val userIdeas: StateFlow<List<Idea>> = _userIdeas

    fun allIdeasByUser(userId: Int) {
        viewModelScope.launch {
            val ideas = repository.getAllIdeasByUser(userId)
            _userIdeas.value = ideas
        }
    }

    val allIdeas: StateFlow<List<Idea>> = repository.allIdeas
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun addIdea(idea: Idea) {
        viewModelScope.launch {
            repository.insertIdea(idea)
        }
    }

    fun updateIdea(idea: Idea) {
        viewModelScope.launch {
            repository.updateIdea(idea)
        }
    }

    fun deleteIdea(idea: Idea) {
        viewModelScope.launch {
            repository.deleteIdea(idea)
        }
    }
}