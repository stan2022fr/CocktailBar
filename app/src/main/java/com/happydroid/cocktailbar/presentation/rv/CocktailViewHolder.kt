package com.happydroid.cocktailbar.presentation.rv

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.model.CocktailItem

class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.item_tv)
    private val image: ShapeableImageView = itemView.findViewById(R.id.item_shapeable_image)

    fun bind(cocktail: CocktailItem) {
        title.text = cocktail.name

        image.setImageResource(cocktail.imageFileName.toIntOrNull() ?: R.drawable.cocktail1)
        Log.i("hhh", cocktail.name)
    }

}
