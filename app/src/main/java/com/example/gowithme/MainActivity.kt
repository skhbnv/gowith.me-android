package com.example.gowithme

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
//                    location_image, city_name
                    val theTitle = resources.getText(R.string.events_in).toString() + " Almaty"
                    val cityText = SpannableString(theTitle)
                    cityText.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimary)),
                        10,
                        theTitle.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )

                    title_bar.text = cityText
                    image_end.visibility = View.VISIBLE
                    image_end.background = resources.getDrawable(R.drawable.location)
                    showBottomNav()
                }
                R.id.nav_map -> {
//                     funnel
                    showBottomNav()
                    title_bar?.text = resources.getText(R.string.nearby_events)
                    image_end.visibility = View.VISIBLE
                    back_button.visibility = View.GONE
                    image_end.background = resources.getDrawable(R.drawable.funnel)
                }
                R.id.nav_favorites -> {
//                    nothing
                    image_end.visibility = View.GONE
                    back_button.visibility = View.GONE
                    title_bar?.text = resources.getText(R.string.subscriptions)

                    showBottomNav()
                }
                R.id.nav_profile -> {
//                    nothing
                    back_button.visibility = View.GONE
                    image_end.visibility = View.GONE
                    showBottomNav()
                }
                R.id.eventPageFragment -> {
                    back_button.visibility = View.VISIBLE
                    back_button.setOnClickListener {
                        onBackPressed()
                    }
                    image_end.visibility = View.GONE
                    hideBottomNav()
                }

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