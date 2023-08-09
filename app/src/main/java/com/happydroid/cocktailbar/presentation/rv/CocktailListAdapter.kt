package com.happydroid.cocktailbar.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.model.CocktailItem

class CocktailListAdapter : ListAdapter<CocktailItem, CocktailViewHolder>(CocktailDiffCallback()) {

    var itemClickListener: ((itemId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CocktailViewHolder(
            layoutInflater.inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item.id)
        }
    }
}
