package com.shinyj.githubsearch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val someString : String
) : Parcelable
