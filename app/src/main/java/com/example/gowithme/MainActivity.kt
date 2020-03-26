package com.example.gowithme

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search -> {}
        }
        return true
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> showBottomNav()
                R.id.nav_map -> showBottomNav()
                R.id.nav_favorites -> showBottomNav()
                R.id.nav_profile -> showBottomNav()
                R.id.eventPageFragment -> { hideBottomNav() }
                else -> hideBottomNav()
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