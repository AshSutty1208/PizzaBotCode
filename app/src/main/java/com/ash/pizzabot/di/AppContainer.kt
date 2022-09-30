package com.ash.pizzabot.di

import com.ash.pizzabot.data.grid.GridState
import com.ash.pizzabot.data.pizzabot.PizzaBot
import com.ash.pizzabot.data.utils.PepperoniLogger
import com.ash.pizzabot.solution.BotInstructionsRepository

/**
 * A class for containing objects to ensure DI is correct
 *
 * This is a very manual way of doing it. I would usually use Hilt/Dagger.
 */
class AppContainer {
    private val pizzaBot = PizzaBot(logger = PepperoniLogger())
    private val botInstructionsRepository = BotInstructionsRepository()
    val pizzaBotViewModelFactory = PizzaBotViewModelFactory(pizzaBot, botInstructionsRepository)
}