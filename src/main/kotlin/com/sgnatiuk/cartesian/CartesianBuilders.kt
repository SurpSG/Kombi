@file:JvmName("CartesianBuilder")
package com.sgnatiuk.cartesian

fun <K, V> CartesianProduct(
        map: Map<K, Collection<V>>,
        keepOrder: Boolean = false
) : CartesianProduct<Map<K, V>>{
    return CartesianProductMap(map, keepOrder)
}

fun <T> CartesianProduct(
    collection : Collection<Collection<T>>,
    keepOrder: Boolean = false
) : CartesianProduct<List<T>> {
    return CartesianProductSet(collection, keepOrder)
}