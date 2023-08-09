package com.happydroid.cocktailbar.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.happydroid.cocktailbar.data.local.CocktailDao
import com.happydroid.cocktailbar.data.local.LocalStorage
import com.happydroid.cocktailbar.data.model.CocktailItem

class CocktailRepository private constructor(application: Application) {
    private val cocktailDao: CocktailDao = LocalStorage.getDatabase(application).cocktailItems()


    fun getAllCocktails(): LiveData<List<CocktailItem>> {
        return cocktailDao.getAllCocktails()
    }

    suspend fun insertCocktail(cocktail: CocktailItem) {
        cocktailDao.insertCocktail(cocktail)
    }

    fun getCocktailById(id: Int): LiveData<CocktailItem> {
        return cocktailDao.getCocktailById(id)
    }

    suspend fun deleteCocktail(id: Int) {
        cocktailDao.deleteCocktailById(id)
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
