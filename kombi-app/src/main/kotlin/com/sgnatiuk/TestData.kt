@file:JvmName("TestData")

package com.sgnatiuk

val listOfLists = listOf(
        listOf(1, 2, 3),
        listOf(4),
        listOf(5, 6)
)
val mapOfLists : Map<Int, List<Int>> = listOfLists.asIndexedMap()

val listOfStrings = listOf("1", "2", "3")
val mapIntToString : Map<Int, String> = listOfStrings.mapIndexed(::Pair).toMap()

fun <T> List<List<T>>.asIndexedMap() = this.mapIndexed(::Pair).toMap()