package com.ash.pizzabot.solution

import androidx.core.text.isDigitsOnly
import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.data.utils.isGreaterThan
import java.lang.IllegalArgumentException

class BotInstructionsRepository {
    fun getGridSize(instructionsInput: String): Pair<Int, Int> {
        if (!instructionsInput.contains("x")) throw IllegalArgumentException("No grid size value in input string")

        val trimmedValue = instructionsInput.trim()
        val splitStrings = trimmedValue.split(" ")
        val gridSizeString = splitStrings[0]
        val gridSizeIntsSplit = gridSizeString.split("x")

        return Pair(gridSizeIntsSplit[0].toInt(), gridSizeIntsSplit[1].toInt())
    }

    fun getGridDropOffLocations(instructionsInput: String): ArrayList<Location> {
        val dropOffLocations: ArrayList<Location> = arrayListOf()
        val trimmedValue = instructionsInput.trim()
        val gridSize = getGridSize(instructionsInput)

        trimmedValue.splitToSequence(" (", ")").forEach { splitString ->
            // Returning forEach acts like continue keyword
            if (splitString.contains("x")
                || splitString.isEmpty()
                || !splitString.contains(",")
            ) return@forEach

            val splitByComma = splitString.split(",").apply {
                if (this.isNullOrEmpty() || this[0].isEmpty() || this[1].isEmpty()) return@forEach
            }

            val dropOffXLocation = splitByComma[0].trim().toIntOrNull() ?: return@forEach
            val dropOffYLocation = splitByComma[1].trim().toIntOrNull() ?: return@forEach

            dropOffXLocation.apply {
                if (this.isGreaterThan(gridSize.first)) return@forEach
            }

            dropOffYLocation.apply {
                if (this.isGreaterThan(gridSize.second)) return@forEach
            }

            dropOffLocations.add(Location(dropOffXLocation, dropOffYLocation))
        }

        return dropOffLocations
    }
}