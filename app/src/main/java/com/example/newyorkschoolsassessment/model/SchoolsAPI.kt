package com.example.newyorkschoolsassessment.model

import com.example.newyorkschoolsassessment.model.SchoolsAPI.Companion.BASE_URL
import com.example.newyorkschoolsassessment.model.data.School
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SchoolsAPI {

    companion object {
        const val BASE_URL = "https://data.cityofnewyork.us/"
    }

    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchools(): List<School>
}

fun getSchoolsAPI(): SchoolsAPI {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SchoolsAPI::class.java)
}