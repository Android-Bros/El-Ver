package com.androidbros.elver.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androidbros.elver.R
import com.androidbros.elver.databinding.ActivityFlowBinding

class FlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

        val bottomNavigationView = binding.bottomNavigationView

        val appConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.aidMapFragment,
                R.id.notificationFragment,
                R.id.needyMapFragment,
                R.id.userProfileFragment
            )
        )

        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appConfiguration)
    }
}