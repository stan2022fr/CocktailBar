package com.happydroid.cocktailbar.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.happydroid.cocktailbar.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, CocktailListFragment.newInstance())
                .commitNow()
        }
    }
}
