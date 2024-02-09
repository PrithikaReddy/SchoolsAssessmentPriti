package com.example.newyorkschoolsassessment.model.datasource

import com.example.newyorkschoolsassessment.model.data.School

interface SchoolsDataSource {
    suspend fun getSchools(): List<School>
}