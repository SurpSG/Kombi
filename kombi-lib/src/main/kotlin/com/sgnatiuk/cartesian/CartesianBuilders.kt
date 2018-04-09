@file:JvmName("CartesianBuilder")
package com.sgnatiuk.cartesian

@JvmOverloads
fun <K, V> cartesianProductOf(
        map: Map<K, Collection<V>>,
        keepOrder: Boolean = false
) : CartesianProduct<Map<K, V>> = CartesianProductMap(map, keepOrder)

@JvmOverloads
fun <T> cartesianProductOf(
    collection : Collection<Collection<T>>,
    keepOrder: Boolean = false
) : CartesianProduct<List<T>> = CartesianProductSet(collection, keepOrder)