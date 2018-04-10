package com.sgnatiuk.cartesian

import com.sgnatiuk.extensions.BigInt
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class CartesianProductMapTest {
    private val data = mapOf(
            1 to listOf(1, 2, 3),
            2 to listOf(4, 5),
            3 to listOf(6)
    )
    private val expectedCombies = listOf(
            mapOf(
                    1 to 1,
                    2 to 4,
                    3 to 6
            ),
            mapOf(
                    1 to 2,
                    2 to 4,
                    3 to 6
            ),
            mapOf(
                    1 to 3,
                    2 to 4,
                    3 to 6
            ),
            mapOf(
                    1 to 1,
                    2 to 5,
                    3 to 6
            ),
            mapOf(
                    1 to 2,
                    2 to 5,
                    3 to 6
            ),
            mapOf(
                    1 to 3,
                    2 to 5,
                    3 to 6
            )
    )

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        CartesianProductSet(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<Map<Int, Int>> = CartesianProductMap(data, false).map { it }
        expectedCombies.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<Map<Int, Int>> = CartesianProductMap(data, true).map { it }
        expectedCombies.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify cartesian product calculates combinations number properly`(){
        var result = 1.BigInt
        val cartesianProductMap = CartesianProductMap(data)
        data.values.forEach { result *= it.size.BigInt }
        assertEquals(result, cartesianProductMap.combinationsCount)
        Assert.assertEquals(expectedCombies.size.BigInt, cartesianProductMap.combinationsCount)
    }
}