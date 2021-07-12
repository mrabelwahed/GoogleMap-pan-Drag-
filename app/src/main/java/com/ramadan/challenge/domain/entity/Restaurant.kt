package com.ramadan.challenge.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    val id: String,
    val name: String?,
    val latitude: Double,
    val longitude: Double,
    val city: String?,
    val address: String?
) : Parcelable
