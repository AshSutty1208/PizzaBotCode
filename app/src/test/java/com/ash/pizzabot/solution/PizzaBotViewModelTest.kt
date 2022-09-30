package com.ash.pizzabot.solution

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ash.pizzabot.data.grid.GridState
import com.ash.pizzabot.data.pizzabot.PizzaBot
import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.data.utils.PepperoniLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class PizzaBotViewModelTest {
    private val repository: BotInstructionsRepository = mock(BotInstructionsRepository::class.java)
    private val pepperoniLogger = mock(PepperoniLogger::class.java)
    private val pizzaBot = PizzaBot(logger = pepperoniLogger)
    private val pizzaBotViewModel = PizzaBotViewModel(pizzaBot, repository)

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * USE THIS ONE TO MODIFY AND UPDATE AS YOU WISH
     */
    @Test
    fun testPizzaBotViewModel_startPizzaBot_locationPath() = runBlocking {
        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val gridSize = GridState(Pair(5, 5), dropOffLocations)

        pizzaBotViewModel.startPizzaBot(gridSize)

        assertEquals("DENNNDEEENDSSDDWWWWSDEEENDWNDEESSD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_locationPath1() = runBlocking {
        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        pizzaBotViewModel.startPizzaBot(GridState(Pair(5, 5), dropOffLocations))

        assertEquals("DENNNDEEENDSSDDWWWWSDEEENDWNDEESSD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_locationPath2() = runBlocking {
        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(2, 5),
            Location(4, 3),
            Location(2, 1),
            Location(0, 1),
            Location(5, 2),
            Location(1, 3),
            Location(5, 1))

        pizzaBotViewModel.startPizzaBot(GridState(Pair(5, 5), dropOffLocations))

        assertEquals("DENNNDENNDEESSDWWSSDWWDEEEEENDWWWWNDEEEESSD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_locationOffGrid_skipsIt() = runBlocking {
        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(2, 5),
            Location(4, 3),
            Location(2, 1),
            Location(0, 1),
            Location(10, 2), //Location off grid x is 10, grid size is 5
            Location(0, 3),
            Location(5, 1))

        pizzaBotViewModel.startPizzaBot(GridState(Pair(5, 5), dropOffLocations))

        assertEquals("DENNNDENNDEESSDWWSSDWWDNNDEEEEESSD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_locationMultipleSameDropOffs() = runBlocking {
        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(0, 0),
            Location(0, 0),
            Location(0, 0),
            Location(0, 0))

        pizzaBotViewModel.startPizzaBot(GridState(Pair(5, 5), dropOffLocations))

        assertEquals("DDDDD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_largeGrid() = runBlocking {
        val dropOffLocations = arrayListOf(Location(49, 49),
            Location(35, 18),
            Location(4, 50),
            Location(20, 39),
            Location(15, 35))

        pizzaBotViewModel.startPizzaBot(GridState(Pair(50, 50), dropOffLocations))

        assertEquals("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEENNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNDWWWWWWWWWWWWWWSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSDWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNDEEEEEEEEEEEEEEEESSSSSSSSSSSDWWWWWSSSSD", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_startPizzaBot_noLocations() = runBlocking {
        val dropOffLocations = arrayListOf<Location>()

        pizzaBotViewModel.startPizzaBot(GridState(Pair(5, 5), dropOffLocations))

        assertEquals("", pizzaBot.movementsMade)
    }

    @Test
    fun testPizzaBotViewModel_setupGrid() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

        val dropOffLocations = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))


        `when`(repository.getGridDropOffLocations(testInstructionsInput)).thenReturn(dropOffLocations)
        `when`(repository.getGridSize(testInstructionsInput)).thenReturn(Pair(5, 5))
        pizzaBotViewModel.setupGrid(testInstructionsInput)

        pizzaBotViewModel.gridState.observeForever {
            assertEquals(5, it.gridXSize())
            assertEquals(5, it.gridYSize())
        }
    }
}