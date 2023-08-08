package com.happydroid.cocktailbar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.happydroid.cocktailbar.data.model.CocktailItem

/**
 * Represents the local storage for cocktail items.
 */
@Database(entities = [CocktailItem::class], version = 1, exportSchema = false)
abstract class LocalStorage : RoomDatabase() {
    abstract fun cocktailItems(): CocktailDao

    companion object {
        @Volatile
        private var INSTANCE: LocalStorage? = null

        fun getDatabase(context: Context): LocalStorage {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalStorage::class.java,
                    "cocktail_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
