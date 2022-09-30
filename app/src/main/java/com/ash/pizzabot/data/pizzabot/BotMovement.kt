package com.ash.pizzabot.data.pizzabot

/**
 * Holds list of Movements a bot can make as an Enum with a specific movement indicator
 *
 * @param movementIndicator - Letter indication of which movement a bot made.
 */
enum class BotMovement(val movementIndicator: String) {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W"),
    PERFORM_ACTION("")
}