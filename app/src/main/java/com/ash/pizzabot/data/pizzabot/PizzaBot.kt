package com.ash.pizzabot.data.pizzabot

import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.data.utils.PepperoniLogger
import java.util.logging.Logger

/**
 * This class represents PizzaBot and its positioning values
 */
class PizzaBot(override var movementsMade: String = "",
               override val startingPosition: Location = Location(0, 0),
               override var currentPosition: Location = startingPosition,
               override val logger: PepperoniLogger
) : BaseBot() {
    override val botName: String
        get() = "PizzaBot"

    override fun performAction() {
        movementsMade += "D"

        PepperoniLogger.debug(botName, "MovementMade -> ${BotMovement.PERFORM_ACTION}")
    }
}

