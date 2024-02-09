package com.example.newyorkschoolsassessment.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class School(
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("overview_paragraph")
    val overview: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("website")
    val website: String
): Parcelable
