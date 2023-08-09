package com.happydroid.cocktailbar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.CocktailRepository
import com.happydroid.cocktailbar.data.model.CocktailItem

class DetailsCocktailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsCocktailFragment()
    }


    private val cocktailViewModel: CocktailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = CocktailRepository.getInstance(requireActivity().application)
                if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
                    return CocktailViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_cocktail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // получаем Item из Bundle
        val bundle = arguments
        val idCocktail: String? = bundle?.getString("idItem")
        cocktailViewModel.getCocktailItemById(idCocktail?.toIntOrNull() ?: 0)
            .observe(viewLifecycleOwner, { cocktailItem ->
                updateUIWithCocktailData(cocktailItem)
            })

        val editButton: Button = view.findViewById(R.id.buttonEdit)
        editButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val backButton: Button = view.findViewById(R.id.buttonDelete)
        backButton.setOnClickListener {
            cocktailViewModel.deleteCocktail(idCocktail?.toIntOrNull() ?: 0)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateUIWithCocktailData(cocktailItem: CocktailItem?) {
        val image: ImageView = requireView().findViewById(R.id.cocktail_image_detail)
        val cocktailName: TextView = requireView().findViewById(R.id.cocktail_name_tv)
        val description: TextView = requireView().findViewById(R.id.description_tv)
        val recipe: TextView = requireView().findViewById(R.id.recipe_tv)

        if (cocktailItem != null) {
            image.setImageResource(cocktailItem.imageFileName.toIntOrNull() ?: 0)
            cocktailName.text = cocktailItem.name

            if (!cocktailItem.description.isNullOrEmpty()) {
                description.text = cocktailItem.description
                description.visibility = View.VISIBLE
            } else {
                description.visibility = View.GONE
            }

            if (!cocktailItem.recipe.isNullOrEmpty()) {
                recipe.text = cocktailItem.recipe
                recipe.visibility = View.VISIBLE
            } else {
                recipe.visibility = View.GONE
            }
        }

    }


}
