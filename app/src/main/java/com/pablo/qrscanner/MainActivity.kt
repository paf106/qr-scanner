package com.pablo.qrscanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.pablo.qrscanner.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.scanFragment,
                R.id.historyFragment,
                R.id.generateFragment,
                R.id.settingsFragment
            )
        )

        val navController = navHostFragment.navController


        setupActionBarWithNavController(navController, appBarConfiguration)

        // Sincronizacion del navController con nuestro bottom navigation
        bottomNavigationView.setupWithNavController(navController)

    }
}