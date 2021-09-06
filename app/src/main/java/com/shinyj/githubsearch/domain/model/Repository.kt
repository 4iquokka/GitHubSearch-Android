package com.shinyj.githubsearch.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id : Int,
    val name : String,
    val description : String,
    val numOfStars : Int,
    val numOfForks : Int,
    val numOfWatchers : Int,
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
        if (numOfStars != other.numOfStars) return false
        if (numOfForks != other.numOfForks) return false
        if (numOfWatchers != other.numOfWatchers) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + numOfStars
        result = 31 * result + numOfForks
        result = 31 * result + numOfWatchers
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Repository(id=$id, name='$name', description='$description', numOfStars=$numOfStars, numOfForks=$numOfForks, numOfWatchers=$numOfWatchers, createdAt='$createdAt', updatedAt=$updatedAt)"
    }


}
