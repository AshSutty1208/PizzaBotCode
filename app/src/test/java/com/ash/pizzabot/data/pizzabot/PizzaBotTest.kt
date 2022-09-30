package com.ash.pizzabot.data.pizzabot

import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.data.utils.PepperoniLogger
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class PizzaBotTest {
    private val pepperoniLogger = Mockito.mock(PepperoniLogger::class.java)
    private val pizzaBot = PizzaBot(logger = pepperoniLogger)

    @Test
    fun testPizzaBot_moveBot_moveNorth() {
        pizzaBot.moveBot(BotMovement.NORTH)

        assertEquals("N", pizzaBot.movementsMade)
        assertEquals(Location(0, 1), pizzaBot.currentPosition)
    }

    @Test
    fun testPizzaBot_moveBot_moveEast() {
        pizzaBot.moveBot(BotMovement.EAST)

        assertEquals("E", pizzaBot.movementsMade)
        assertEquals(Location(1, 0), pizzaBot.currentPosition)
    }

    @Test
    fun testPizzaBot_moveBot_moveWest() {
        pizzaBot.moveBot(BotMovement.WEST)

        assertEquals("W", pizzaBot.movementsMade)
        assertEquals(Location(-1, 0), pizzaBot.currentPosition)
    }

    @Test
    fun testPizzaBot_moveBot_moveSouth() {
        pizzaBot.moveBot(BotMovement.SOUTH)

        assertEquals("S", pizzaBot.movementsMade)
        assertEquals(Location(0, -1), pizzaBot.currentPosition)
    }

    @Test
    fun testPizzaBot_moveBot_performAction() {
        pizzaBot.moveBot(BotMovement.PERFORM_ACTION)

        assertEquals("D", pizzaBot.movementsMade)
        assertEquals(Location(0, 0), pizzaBot.currentPosition)
    }
}