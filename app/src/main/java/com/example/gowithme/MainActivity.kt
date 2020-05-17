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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val appBarConfiguration = AppBarConfiguration(
            setOf( R.id.nav_home, R.id.nav_map_list, R.id.nav_friends_events, R.id.nav_profile )
        )

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
                    showBottomNav()
                }
                R.id.nav_map_list -> {
                    showBottomNav()
                }
                R.id.nav_friends_events -> {
                    showBottomNav()
                }
                R.id.nav_profile -> {
                    showBottomNav()
                }
                else -> {
                    hideBottomNav()
                }
            }
        }
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    private fun showBottomNav() {
        nav_view.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        nav_view.visibility = View.GONE
    }
}