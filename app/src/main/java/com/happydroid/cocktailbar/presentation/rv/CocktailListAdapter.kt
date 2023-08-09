package com.happydroid.cocktailbar.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.model.CocktailItem
import androidx.recyclerview.widget.ListAdapter

class CocktailListAdapter : ListAdapter<CocktailItem, CocktailViewHolder>(CocktailDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  CocktailViewHolder(
            layoutInflater.inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
