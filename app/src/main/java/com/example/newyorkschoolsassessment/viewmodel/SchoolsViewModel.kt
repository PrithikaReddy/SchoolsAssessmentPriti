package com.example.newyorkschoolsassessment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newyorkschoolsassessment.model.data.School
import com.example.newyorkschoolsassessment.model.data.SchoolsRepository
import kotlinx.coroutines.launch

class SchoolsViewModel(
    private val schoolsRepository: SchoolsRepository
): ViewModel() {

    private val _schools = mutableStateListOf<School>()
    val schools: List<School> = _schools

    var exception: String by mutableStateOf("")

    fun getSchools() {
        viewModelScope.launch {
            try {
                val list = schoolsRepository.getSchools()
                _schools.clear()
                _schools.addAll(list)
            } catch(e: Throwable) {
                exception = e.localizedMessage.orEmpty()
            }
        }
    }
}

class SchoolsViewModelProviderFactory(
    private val repository: SchoolsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SchoolsViewModel(repository) as T
    }
}