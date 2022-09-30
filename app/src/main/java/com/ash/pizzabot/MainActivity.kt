package com.ash.pizzabot

import androidx.appcompat.app.AppCompatActivity
import com.ash.pizzabot.solution.PizzaBotViewModel
import kotlinx.coroutines.runBlocking

/**
 * Main class (Starting Area)
 */
class MainActivity: AppCompatActivity() {
    lateinit var pizzaBotViewModel: PizzaBotViewModel

    fun startPizzaBot(initialInstructions: String) {
        val appContainer = (application as MainApplication).appContainer
        pizzaBotViewModel = appContainer.pizzaBotViewModelFactory.create()
        runBlocking {
            pizzaBotViewModel.setupGrid(initialInstructions)
        }

        pizzaBotViewModel.gridState.observe(this@MainActivity) {
            runBlocking {
                pizzaBotViewModel.startPizzaBot(it)
            }
        }
    }
}