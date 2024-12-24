package com.wodud7308.movieinfo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.wodud7308.movieinfo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        with(binding) {
            val navHost = supportFragmentManager.findFragmentById(navHost.id) as NavHostFragment
            val navController = navHost.findNavController()

            menuBottom.setupWithNavController(navController)
        }
    }
}
