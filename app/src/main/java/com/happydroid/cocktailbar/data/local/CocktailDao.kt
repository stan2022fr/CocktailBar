package com.happydroid.cocktailbar.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.happydroid.cocktailbar.data.model.CocktailItem

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktail_items")
    fun getAllCocktails(): LiveData<List<CocktailItem>>

    @Query("SELECT * FROM cocktail_items WHERE id = :id")
    fun getCocktailById(id: Int): LiveData<CocktailItem>

    @Query("DELETE FROM cocktail_items WHERE id = :id")
    suspend fun deleteCocktailById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: CocktailItem)
}

