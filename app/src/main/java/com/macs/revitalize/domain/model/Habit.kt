package com.macs.revitalize.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Habit(
    var name: String,
    var desc: String,
    var freq: String,
    var tags: MutableList<String>,
    var startDate: Date
): Parcelable
