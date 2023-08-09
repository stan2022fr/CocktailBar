package com.happydroid.cocktailbar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.happydroid.cocktailbar.R
import com.happydroid.cocktailbar.data.CocktailRepository
import com.happydroid.cocktailbar.presentation.rv.CocktailListAdapter

class CocktailListFragment : Fragment() {

    private val cocktailListAdapter = CocktailListAdapter()

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

        val cocktailRecyclerView: RecyclerView = view.findViewById(R.id.cocktail_rv)
        setRecyclerView(cocktailRecyclerView)

        val fabAddTask = view.findViewById<FloatingActionButton>(R.id.fabAddCocktail)
        fabAddTask.setOnClickListener {
            val addCocktailFragment = AddCocktailFragment.newInstance()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out,
            )
            fragmentTransaction.replace(R.id.fragment_container, addCocktailFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun setRecyclerView(cocktailRecyclerView: RecyclerView) {

        cocktailRecyclerView.adapter = cocktailListAdapter

        val introImage: ImageView = requireView().findViewById(R.id.intro_image)
        val arrowImage: ImageView = requireView().findViewById(R.id.arrow)
        val introText: TextView = requireView().findViewById(R.id.create_cock_hint_tv)
        val titleText: TextView = requireView().findViewById(R.id.title_tv)
        val frameLayout: FrameLayout = requireView().findViewById(R.id.frame_layout)

        cocktailViewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.isEmpty()) {
                introImage.visibility = View.VISIBLE
                arrowImage.visibility = View.VISIBLE
                introText.visibility = View.VISIBLE
                cocktailRecyclerView.visibility = View.GONE
            } else {
                titleText.setTextAppearance(R.style.app_title)
                introImage.visibility = View.GONE
                arrowImage.visibility = View.GONE
                introText.visibility = View.GONE
                val layoutParams = frameLayout.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                frameLayout.layoutParams = layoutParams
                cocktailRecyclerView.visibility = View.VISIBLE
                cocktailListAdapter.submitList(cocktails)
            }
        }

        cocktailListAdapter.itemClickListener = { itemId ->
            openDetails(itemId)
        }
    }

    private fun openDetails(itemId: Int) {
        val detailsCocktailFragment = DetailsCocktailFragment.newInstance()
        val fragmentManager = requireActivity().supportFragmentManager
        // Создаем Bundle и добавляем данные
        val bundle = Bundle()
        bundle.putString("idItem", itemId.toString())
        detailsCocktailFragment.arguments = bundle

        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out,
            )
            replace(R.id.fragment_container, detailsCocktailFragment)
            addToBackStack(null)
            commit()
        }
    }

}
