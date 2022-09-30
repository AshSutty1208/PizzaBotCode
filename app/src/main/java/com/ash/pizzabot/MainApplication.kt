package com.ash.pizzabot

import android.app.Application
import com.ash.pizzabot.di.AppContainer

class MainApplication : Application() {
    val appContainer = AppContainer()
}