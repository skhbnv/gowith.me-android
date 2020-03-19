package com.example.gowithme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_map,
                R.id.nav_dashboard,
                R.id.nav_favorites,
                R.id.nav_profile
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> showBottomNav()
                R.id.nav_map -> showBottomNav()
                R.id.nav_dashboard -> showBottomNav()
                R.id.nav_favorites -> showBottomNav()
                R.id.nav_profile -> showBottomNav()
                R.id.eventPageFragment -> {
                    hideBottomNav()
                    
                }
                else -> hideBottomNav()
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        nav_view.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        nav_view.visibility = View.GONE
    }
}