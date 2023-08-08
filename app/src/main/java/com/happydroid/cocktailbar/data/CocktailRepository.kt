package com.happydroid.cocktailbar.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.happydroid.cocktailbar.data.local.CocktailDao
import com.happydroid.cocktailbar.data.local.LocalStorage
import com.happydroid.cocktailbar.data.model.CocktailItem

class CocktailRepository private constructor(application: Application) {
    private val cocktailDao: CocktailDao = LocalStorage.getDatabase(application).cocktailItems()
    val allCocktails: LiveData<List<CocktailItem>> = cocktailDao.getAllCocktails()

    suspend fun insertCocktail(cocktail: CocktailItem) {
        cocktailDao.insertCocktail(cocktail)
    }

    companion object {
        @Volatile
        private var instance: CocktailRepository? = null

        fun getInstance(application: Application): CocktailRepository {
            return instance ?: synchronized(this) {
                instance ?: CocktailRepository(application).also { instance = it }
            }
        }
    }
}
