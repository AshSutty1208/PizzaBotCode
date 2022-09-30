package com.ash.pizzabot.solution


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ash.pizzabot.data.grid.GridState
import com.ash.pizzabot.data.pizzabot.BotMovement
import com.ash.pizzabot.data.pizzabot.PizzaBot
import com.ash.pizzabot.data.utils.PepperoniLogger
import com.ash.pizzabot.data.utils.isGreaterThan
import com.ash.pizzabot.data.utils.isLowerThan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.IndexOutOfBoundsException

class PizzaBotViewModel(private val pizzaBot: PizzaBot,
                        private val botInstructionsRepository: BotInstructionsRepository
) : ViewModel() {
    val gridState = MutableLiveData<GridState>()

    suspend fun setupGrid(instructionsInput: String) = coroutineScope {
        val gridDropOffLocations =  botInstructionsRepository.getGridDropOffLocations(instructionsInput)
        val gridSize = botInstructionsRepository.getGridSize(instructionsInput)

        withContext(Dispatchers.Main) {
             gridState.value = GridState(gridSize, gridDropOffLocations)
        }
    }

    suspend fun startPizzaBot(gridState: GridState) = coroutineScope {
        for (dropOffLocation in gridState.pizzaDropOffLocations) {
            if (dropOffLocation.x.isGreaterThan(gridState.gridXSize())
                || dropOffLocation.y.isGreaterThan(gridState.gridYSize())) {
                    PepperoniLogger.debug("startPizzaBot", "$dropOffLocation skipped as location is off grid.")
                continue
            }

            xLoop@ for (i in 0..gridState.gridXSize()) {
                val dropOffLocationX = dropOffLocation.x
                val pizzaBotCurrentX = pizzaBot.currentPosition.x
                if (pizzaBotCurrentX != dropOffLocationX) {
                    if (pizzaBotCurrentX.isLowerThan(dropOffLocationX)) {
                        pizzaBot.moveBot(BotMovement.EAST)
                    } else {
                        pizzaBot.moveBot(BotMovement.WEST)
                    }
                } else {
                    yLoop@ for (j in 0..gridState.gridYSize()) {
                        val dropOffLocationY = dropOffLocation.y
                        val pizzaBotCurrentY = pizzaBot.currentPosition.y
                        if (pizzaBotCurrentY != dropOffLocationY ) {
                            if (pizzaBotCurrentY.isLowerThan(dropOffLocationY)) {
                                pizzaBot.moveBot(BotMovement.NORTH)
                            } else {
                                pizzaBot.moveBot(BotMovement.SOUTH)
                            }
                        } else {
                            pizzaBot.moveBot(BotMovement.PERFORM_ACTION)
                            break@xLoop
                        }
                    }
                }
            }
        }
    }
}