package com.wodud7308.movieinfo.app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        initView()
        initNavigation()
    }

    private fun initView() {
        enableEdgeToEdge()
        binding.root.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { view, insets ->
                val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                view.setPadding(0, statusBarHeight, 0, 0)
                insets
            }

            setContentView(it)
        }
    }

    private fun initNavigation() {
        with(binding) {
            val navHost = supportFragmentManager.findFragmentById(navHost.id) as NavHostFragment
            val navController = navHost.findNavController()

            menuBottom.setupWithNavController(navController)
        }
    }
}
