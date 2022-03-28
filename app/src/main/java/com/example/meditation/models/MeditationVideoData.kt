package com.example.meditation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MeditationVideoData (
    var id: String,
    var title: String,
    var image: String,
    var link: String
): Parcelable