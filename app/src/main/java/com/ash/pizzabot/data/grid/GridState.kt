package com.ash.pizzabot.data.grid

import com.ash.pizzabot.data.utils.Location

/**
 * Represents the grids state
 *
 * @param gridSize - Represents a size of the grid i.e. 2x2
 * @param pizzaDropOffLocations - ArrayList of location pairs where the PizzaBot will visit
 */
data class GridState(val gridSize: Pair<Int, Int>, val pizzaDropOffLocations: ArrayList<Location>) {
    fun gridXSize(): Int = gridSize.first
    fun gridYSize(): Int = gridSize.second
}