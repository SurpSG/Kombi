package com.sgnatiuk.combination

import com.sgnatiuk.listOfStrings
import com.sgnatiuk.mapIntToString

fun main(args: Array<String>) {
    printCombinationsOfList(listOfStrings)
    printCombinationsOfMap(mapIntToString)
}

fun <T> printCombinationsOfList(inputData: List<T>) {
    println("Combinations of $inputData")
    combinationsOf(inputData).forEach(::println)
}

fun <K,V> printCombinationsOfMap(inputData: Map<K, V>) {
    println("Combinations of $inputData")
    combinationsOf(inputData).forEach(::println)
}