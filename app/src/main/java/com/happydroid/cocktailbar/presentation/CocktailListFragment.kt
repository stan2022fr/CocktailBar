package com.happydroid.cocktailbar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.CocktailRepository

class CocktailListFragment : Fragment() {

    companion object {
        fun newInstance() = CocktailListFragment()
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
        return inflater.inflate(R.layout.fragment_cocktail_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
