package com.example.newyorkschoolsassessment.model.datasource

import com.example.newyorkschoolsassessment.model.SchoolsAPI
import com.example.newyorkschoolsassessment.model.data.School

class SchoolsDataSourceImpl(
    private val schoolsAPI: SchoolsAPI
): SchoolsDataSource {
    override suspend fun getSchools(): List<School> {
        return schoolsAPI.getSchools()
    }
}