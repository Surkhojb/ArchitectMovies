package com.surkhojb.architectmovies.data.local.room.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "movies_searchs")
class MovieSearchs {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "words")
    @TypeConverters(ArrayListConverter::class)
    var wordsSearched: ArrayList<String> = arrayListOf()
}

class ArrayListConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): ArrayList<String>? {
        if (data == null) {
            return null
        }

        val listType = object : TypeToken<ArrayList<String>>() {

        }.type

        return gson.fromJson<ArrayList<String>>(data, listType)
    }

    @TypeConverter
    fun listToString(objects: ArrayList<String>?): String {
        return gson.toJson(objects)
    }
}