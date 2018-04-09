package com.sgnatiuk.combination

fun main(args: Array<String>) {
    val inputData = listOf(1, 2, 3)

    printCombinationsOfList(inputData)
    printCombinationsOfMap(inputData)
}

fun <T> printCombinationsOfList(inputData: List<T>) {
    println("Combinations of $inputData")
    combinationsOf(inputData).forEach(::println)
}

fun <T> printCombinationsOfMap(inputData: List<T>) {
    val inputDataMap: Map<Int, T> = inputData.mapIndexed(::Pair).toMap()
    println("Combinations of $inputDataMap")
    combinationsOf(inputDataMap).forEach(::println)
}