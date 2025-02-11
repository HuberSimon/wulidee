package com.example.wulidee.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wulidee.data.PersonRepository
import com.example.wulidee.data.local.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonViewModel(
    private val repository: PersonRepository,
) : ViewModel() {

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    private val _selectedPerson = MutableStateFlow<Person?>(null)
    val selectedPerson: StateFlow<Person?> = _selectedPerson

    val allPersons: StateFlow<List<Person>> = repository.allPersons
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    val mainPerson: StateFlow<Person?> = repository.getMainPerson().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    val personCount = repository.personCount.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        0
    )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.initializeMainPerson()
            _isInitialized.value = true
        }
    }

    fun addPerson(person: Person) {
        viewModelScope.launch {
            repository.insertPerson(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            repository.deletePerson(person)
        }
    }

    fun getPersonById(id: Int) {
        viewModelScope.launch {
            val person = repository.getPersonById(id)
            _selectedPerson.value = person
        }
    }
}
