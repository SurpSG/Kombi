@file:JvmName("CombinationsBuilder")
package com.sgnatiuk.combination

fun <K, V> combinationsOf(
        map: Map<K, V>
) : Combination<Map<K, V>> = MapCombination(map)

fun <T> combinationsOf(
        list: List<T>
) : Combination<List<T>> = ListCombination(list)