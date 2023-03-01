package com.czech.housegot.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    @TypeConverter
    fun listToString(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun stringToList(value: String) = Json.decodeFromString<List<String>>(value)
}