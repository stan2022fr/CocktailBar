package com.happydroid.cocktailbar.presentation.rv

import androidx.recyclerview.widget.DiffUtil
import com.happydroid.cocktailbar.data.model.CocktailItem

class CocktailDiffCallback : DiffUtil.ItemCallback<CocktailItem>() {
    override fun areItemsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
        return oldItem == newItem
    }
}
