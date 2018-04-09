package com.sgnatiuk.combination

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class MapBuilderTest {

    private val map = mapOf(
            1 to "1",
            2 to "2",
            3 to "3"
    )
    private val mapBuilder = MapBuilder(map, map.keys.toList())

    @Test
    fun newCollectionTest() {
        val newCollection = mapBuilder.newCollection(0)

        assertTrue(newCollection is MutableMap<*, *>)
        assertEquals(0, newCollection.size)
    }

    @Test
    fun addItemByIndexTest() {
        val newCollection = mapBuilder.newCollection(0)
        map.keys.forEachIndexed { index, key  ->
            mapBuilder.addItemByIndex(newCollection, index)
            assertTrue(newCollection.contains(key))
        }
        assertEquals(map, newCollection)

    }
}