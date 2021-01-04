package com.surkhojb.architectmovies.data.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class MovieCast(
    var cast: List<Cast>
)

data class Cast(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)

class CastTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): MovieCast? {
        if (data == null) {
            return null
        }

        val listType = object : TypeToken<MovieCast>() {

        }.type

        return gson.fromJson<MovieCast>(data, listType)
    }

    @TypeConverter
    fun listToString(objects: MovieCast?): String {
        return gson.toJson(objects)
    }
}