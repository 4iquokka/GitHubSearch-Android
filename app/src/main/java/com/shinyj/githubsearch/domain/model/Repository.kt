package com.shinyj.githubsearch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id : Long,
    val name : String,
    val description : String?,
    val url : String?,
    val numOfStars : Long,
    val numOfForks : Long,
    val numOfWatchers : Long,
    val createdAt: String,
    val updatedAt: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repository

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (url != other.url) return false
        if (numOfStars != other.numOfStars) return false
        if (numOfForks != other.numOfForks) return false
        if (numOfWatchers != other.numOfWatchers) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + numOfStars.hashCode()
        result = 31 * result + numOfForks.hashCode()
        result = 31 * result + numOfWatchers.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Repository(id=$id, name='$name', description=$description, url=$url, numOfStars=$numOfStars, numOfForks=$numOfForks, numOfWatchers=$numOfWatchers, createdAt='$createdAt', updatedAt=$updatedAt)"
    }
}
