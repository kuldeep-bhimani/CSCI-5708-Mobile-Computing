package com.macs.revitalize.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var email: String,
    var habits: List<String>,
    var subscribedTo: List<String>,
    var achievements: List<String>
): Parcelable
