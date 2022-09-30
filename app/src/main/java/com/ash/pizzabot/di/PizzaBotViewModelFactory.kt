package com.ash.pizzabot.di

import com.ash.pizzabot.data.pizzabot.PizzaBot
import com.ash.pizzabot.solution.BotInstructionsRepository
import com.ash.pizzabot.solution.PizzaBotViewModel

interface Factory<T> {
    fun create(): T
}

class PizzaBotViewModelFactory(private val pizzaBot: PizzaBot,
                               private val botInstructionsRepository: BotInstructionsRepository)
    : Factory<PizzaBotViewModel> {
    override fun create(): PizzaBotViewModel {
        return PizzaBotViewModel(pizzaBot, botInstructionsRepository)
    }
}