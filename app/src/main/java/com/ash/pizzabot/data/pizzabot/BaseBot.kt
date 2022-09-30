package com.ash.pizzabot.data.pizzabot

import android.util.Log
import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.data.utils.PepperoniLogger

/**
 * Base class for a bot
 *
 * @property startingPosition - The Location of the bots starting position usually 0,0
 * @property currentPosition - The Location of the bots current position as 0,0
 * @property movementsMade - String value of all movements a bot has made e.g. "DEWWESSNND"
 * @property botName - Name of the bot for debugging purposes
 * @property logger - Pepperoni Logger instance
 */
abstract class BaseBot {
    abstract val startingPosition: Location
    abstract var currentPosition: Location
    abstract var movementsMade: String
    abstract fun performAction()
    abstract val botName: String
    abstract val logger: PepperoniLogger

    open fun moveBot(botMovement: BotMovement) {
        when(botMovement) {
            BotMovement.NORTH -> {
                currentPosition.y++
                movementsMade += BotMovement.NORTH.movementIndicator

                PepperoniLogger.debug(botName, "MovementMade -> ${BotMovement.NORTH}")
            }

            BotMovement.SOUTH -> {
                currentPosition.y--
                movementsMade += BotMovement.SOUTH.movementIndicator

                PepperoniLogger.debug(botName, "MovementMade -> ${BotMovement.SOUTH}")
            }

            BotMovement.EAST -> {
                currentPosition.x++
                movementsMade += BotMovement.EAST.movementIndicator

                PepperoniLogger.debug(botName, "MovementMade -> ${BotMovement.EAST}")
            }

            BotMovement.WEST -> {
                currentPosition.x--
                movementsMade += BotMovement.WEST.movementIndicator

                PepperoniLogger.debug(botName, "MovementMade -> ${BotMovement.WEST}")
            }

            BotMovement.PERFORM_ACTION -> {
                performAction()
            }
        }

        logPosition()
        logMovementsMade()
    }

    open fun logPosition() {
        PepperoniLogger.debug(botName, "CurrentPosition -> $currentPosition")
    }

    private fun logMovementsMade() {
        PepperoniLogger.debug(botName, "CurrentMovementsMade -> $movementsMade")
    }
}