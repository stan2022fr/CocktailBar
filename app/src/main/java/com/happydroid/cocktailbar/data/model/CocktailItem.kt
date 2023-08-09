package com.happydroid.cocktailbar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.happydroid.cocktailbar.data.local.ListConverter

@Entity(tableName = "cocktail_items")
@TypeConverters(ListConverter::class)
data class CocktailItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String?,
    val recipe: String?,
    val ingredients: List<String>,
    val imageFileName: String
)
