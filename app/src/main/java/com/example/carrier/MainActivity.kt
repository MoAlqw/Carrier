package com.example.carrier

import android.os.Bundle
import android.util.Log.i
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment)
            .navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.flightsFragment,
                R.id.archiveFragment,
                R.id.analyticsFragment,
                R.id.profileFragment
            )
        )

        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment)
            .navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}