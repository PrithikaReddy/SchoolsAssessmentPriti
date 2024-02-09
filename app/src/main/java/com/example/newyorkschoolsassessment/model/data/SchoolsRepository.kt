package com.example.newyorkschoolsassessment.model.data

import com.example.newyorkschoolsassessment.model.datasource.SchoolsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SchoolsRepository(
    private val schoolsDataSource: SchoolsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getSchools() = withContext(dispatcher) {
        schoolsDataSource.getSchools()
    }
}