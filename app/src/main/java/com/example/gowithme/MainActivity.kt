package com.example.gowithme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
                    showBottomNav()
                }
                R.id.nav_map_list -> {
                    showBottomNav()
                }
                R.id.nav_map -> {
                    showBottomNav()
                }
                R.id.nav_favorites -> {
                    showBottomNav()
                }
                R.id.nav_profile -> {
                    showBottomNav()
                }
                R.id.nav_add_new_event -> {
                    navController.navigate(R.id.action_nav_add_new_event_to_loginFragment)
                    hideBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }
        navView.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        nav_view.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        nav_view.visibility = View.GONE
    }
}