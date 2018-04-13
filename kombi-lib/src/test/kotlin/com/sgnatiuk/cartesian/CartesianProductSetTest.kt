package com.sgnatiuk.cartesian

import com.sgnatiuk.dataList
import com.sgnatiuk.expectedCartesianList
import com.sgnatiuk.extensions.BigInt
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

internal class CartesianProductSetTest {

    @Test
    fun `verify empty collection is returned when passed empty collection`() {
        val emptyCollection = ArrayList<List<Int>>()
        CartesianProductSet(emptyCollection).forEach {
            throw RuntimeException("expected empty collection")
        }
    }

    @Test
    fun `verify Cartesian product set returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(dataList, false).map { it }
        assertContainsAll(expectedCartesianList, result)
    }

    @Test
    fun `verify Cartesian product set with keep order returns all possible combinations`() {
        val result: List<List<Int>> = CartesianProductSet(dataList, true).map { it }
        assertContainsAll(expectedCartesianList, result)
        expectedCartesianList.forEach {
            assertTrue(result.contains(it))
        }
    }

    @Test
    fun `verify cartesian product calculates combinations number properly`(){
        var result = 1.BigInt
        val cartesianProductSet = CartesianProductSet(dataList)
        dataList.forEach { result *= it.size.BigInt }
        Assert.assertEquals(result, cartesianProductSet.combinationsCount)
        Assert.assertEquals(expectedCartesianList.size.BigInt, cartesianProductSet.combinationsCount)
    }

    private fun <T> assertContainsAll(
            expected: Collection<Collection<T>>,
            actual: Collection<Collection<T>>
    ) {
        expected.forEach { expectedCombination ->
            var foundCombination = false
            for (computed in actual){
                if(computed.containsAll(expectedCombination)){
                    foundCombination = true
                    break
                }
            }
            assertTrue("Expected $expectedCombination, but not found in $actual", foundCombination)
        }
    }
}