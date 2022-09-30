package com.ash.pizzabot.solution

import com.ash.pizzabot.data.utils.Location
import com.ash.pizzabot.solution.BotInstructionsRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import kotlin.math.exp

@RunWith(JUnit4::class)
class BotInstructionsRepositoryTest {
    private val instructionsAdapter = BotInstructionsRepository()

    @Test
    fun testInstructionsAdapter_getGridSize() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"
        val gridSize = instructionsAdapter.getGridSize(testInstructionsInput)

        val expectedValue = Pair(5, 5)
        assertEquals(expectedValue.first, gridSize.first)
        assertEquals(expectedValue.second, gridSize.second)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_locationXOutOfGridBounds_skipsIt() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (6, 4) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_locationYOutOfGridBounds_skipsIt() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 6) (4, 2) (4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_missingLeftBracketStillAdded() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) 4, 2) (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_missingRightBracketStillAdded() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) (4, 2 (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_nullValueInInput_skipsIt() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) null (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test
    fun testInstructionsAdapter_getGridDropOffLocations_noValuesInBrackets_skipsIt() = runBlocking {
        val testInstructionsInput = " 5x5 (0, 0) (1, 3) (4, 4) (4, 2) (,) (0, 1) (3, 2) (2, 3) (4, 1)"

        val expectedValues = arrayListOf(Location(0, 0),
            Location(1, 3),
            Location(4, 4),
            Location(4, 2),
            Location(0, 1),
            Location(3, 2),
            Location(2, 3),
            Location(4, 1))

        val dropOffLocations = instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        assertEquals(expectedValues, dropOffLocations)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInstructionsAdapter_emptyString() = runBlocking {
        val testInstructionsInput = ""

        instructionsAdapter.getGridDropOffLocations(testInstructionsInput)

        Assert.fail()
    }
}