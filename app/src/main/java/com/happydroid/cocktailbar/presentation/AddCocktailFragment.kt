package com.happydroid.cocktailbar.presentation

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.CocktailRepository
import com.happydroid.cocktailbar.data.model.CocktailItem


class AddCocktailFragment : Fragment() {

    companion object {
        fun newInstance() = AddCocktailFragment()
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

    private lateinit var chipGroup: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_add_cocktail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipGroup = view.findViewById(R.id.chipGroup)

        val image: ImageView = view.findViewById(R.id.cocktail_image)
        val drawableResources = listOf(
            R.drawable.cocktail1,
            R.drawable.cocktail2,
            R.drawable.cocktail3,
            R.drawable.cocktail4,
            R.drawable.cocktail5,
            R.drawable.cocktail6
        )
        val randomIndex = (0..2).random()
        val idImage = drawableResources[randomIndex]
        image.setImageResource(idImage)
        image.setTag(R.id.image_view_tag, idImage)

        val cocktailNameEditText: EditText =
            view.findViewById(R.id.cocktail_name_edit)
        val supportingCocktailName: TextView =
            view.findViewById(R.id.supporting_cocktail_name_tv)

        cocktailNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val hintColor = ContextCompat.getColor(
                    requireContext(),
                    R.color.hint_color
                )
                cocktailNameEditText.setBackgroundResource(R.drawable.rounded_corner_background)
                cocktailNameEditText.setTextColor(hintColor)
                cocktailNameEditText.setHintTextColor(hintColor)
                supportingCocktailName.setTextColor(hintColor)

            }
        }

        // Установка обработчика на кнопку добавления ингридиента
        val ingredientName: EditText =
            requireView().findViewById(R.id.ingredient_edit)
        val addChipButton: Button = view.findViewById(R.id.buttonAddChip)
        addChipButton.setOnClickListener {
            if (!ingredientName.text.toString().isEmpty()) {
                addIngredient(ingredientName.text.toString())
                ingredientName.setText("")
            }

        }

        // Установка обработчика клика на кнопку "Сохранить"
        val saveButton: Button = view.findViewById(R.id.buttonSave)
        saveButton.setOnClickListener {
            if (cocktailNameEditText.text.isNullOrEmpty()) {
                cocktailNameEditText.clearFocus()
                val redColor =
                    ContextCompat.getColor(requireContext(), R.color.red)
                cocktailNameEditText.setTextColor(redColor)
                cocktailNameEditText.setHintTextColor(redColor)
                supportingCocktailName.setTextColor(redColor)

                val shape = GradientDrawable()
                shape.setStroke(2, redColor)
                shape.cornerRadius = 15f
                cocktailNameEditText.background = shape

            }else if(chipGroup.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    getString(R.string.message_no_ingredients),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                saveCocktailItem()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        // Установка обработчика клика на кнопку "Закрыть"
        val cancelButton: Button = view.findViewById(R.id.buttonCancel)
        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun addIngredient(ingredient: String) {

        val chip = Chip(context)
        val buttonColor = ContextCompat.getColor(
            requireContext(),
            R.color.button_color
        )
        val textColor = ContextCompat.getColor(
            requireContext(),
            R.color.hint_color
        )

        chip.setChipStrokeColor(ColorStateList.valueOf(buttonColor))
        chip.setCloseIconTint(ColorStateList.valueOf(buttonColor))
        chip.setTextColor(textColor)
        chip.text = ingredient
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
        }
        chipGroup.addView(chip)
    }

    private fun saveCocktailItem() {
        val cocktailName: EditText =
            requireView().findViewById(R.id.cocktail_name_edit)
        val cocktailDescription: EditText =
            requireView().findViewById(R.id.description_edit)
        val cocktailRecipe: EditText =
            requireView().findViewById(R.id.recipe_edit)
        val cocktailImage: ImageView =
            requireView().findViewById(R.id.cocktail_image)

        val chipTextList = mutableListOf<String>()

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chipTextList.add(chip.text.toString())
        }

        val cocktailItem = CocktailItem(
            id = 0,
            name = cocktailName.text.toString(),
            description = cocktailDescription.text.toString(),
            recipe = cocktailRecipe.text.toString(),
            ingredients = chipTextList,
            imageFileName = cocktailImage.getTag(R.id.image_view_tag)
                .toString()
        )
        cocktailViewModel.insertCocktail(cocktailItem)
    }

}
