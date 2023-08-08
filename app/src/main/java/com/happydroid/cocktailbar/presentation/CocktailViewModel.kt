package com.happydroid.cocktailbar.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.happydroid.cocktailbar.data.CocktailRepository
import com.happydroid.cocktailbar.data.model.CocktailItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CocktailViewModel(private val cocktailRepository: CocktailRepository) : ViewModel() {
    val allCocktails: LiveData<List<CocktailItem>> = cocktailRepository.allCocktails

    fun insertCocktail(cocktail: CocktailItem) = GlobalScope.launch {
        cocktailRepository.insertCocktail(cocktail)
    }

}
