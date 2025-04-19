package com.example.sodv3203_final_project.Data

import androidx.room.TypeConverter
import com.example.sodv3203_final_project.Data.Orders.OrderCustomization
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromCustomizations(customizations: List<OrderCustomization>?): String? {
        val gson = Gson()
        return gson.toJson(customizations) // Convert List<OrderCustomization> to JSON string
    }

    @TypeConverter
    fun toCustomizations(customizationsJson: String?): List<OrderCustomization>? {
        val gson = Gson()
        val listType = object : TypeToken<List<OrderCustomization>>() {}.type
        return gson.fromJson(customizationsJson, listType) // Convert JSON string back to List<OrderCustomization>
    }
}
