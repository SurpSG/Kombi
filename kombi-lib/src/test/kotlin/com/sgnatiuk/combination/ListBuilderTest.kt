package com.sgnatiuk.combination

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class ListBuilderTest {
    private val listOf = listOf(1, 2, 3)
    private val listBuilder = ListBuilder(listOf)

    @Test
    fun newCollection() {
        val newCollection = listBuilder.newCollection(0)
        assertTrue(newCollection is MutableList<*>)
        assertEquals(0, newCollection.size)
    }

    @Test
    fun addItemByIndex() {
        val newCollection = listBuilder.newCollection(0)

        listOf.forEachIndexed { index, item ->
            listBuilder.addItemByIndex(newCollection, index)
            assertTrue(newCollection.contains(item))
        }
        assertEquals(listOf, newCollection)

    }
}