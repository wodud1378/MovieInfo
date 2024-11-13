package com.wodud7308.movieinfo.splash

import android.content.Context
import android.content.Intent
import com.wodud7308.movieinfo.MainActivity
import com.wodud7308.presentation.splash.NavigationHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NavigationHandlerImpl
    @Inject
    constructor(
        @ApplicationContext
        private val context: Context,
    ) : NavigationHandler {
        override fun navigateToMain() {
            Intent(context, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }.also {
                    context.startActivity(it)
                }
        }
    }
