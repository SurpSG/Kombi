package com.sgnatiuk.combination

internal interface CollectionBuilder<T> {
    fun newCollection(initialCapacity: Int) : T
    fun addItemByIndex(collection: T, index: Int)
}

internal class ListBuilder<T>(
        private val originData: List<T>
) : CollectionBuilder<MutableList<T>> {
    override fun newCollection(initialCapacity: Int) = ArrayList<T>(initialCapacity)

    override fun addItemByIndex(collection: MutableList<T>, index: Int) {
        collection += originData[index]
    }
}

internal class MapBuilder<K, V>(
        private val originData: Map<K, V>,
        private val dataKeys: List<K>
) : CollectionBuilder<MutableMap<K, V>> {

    override fun newCollection(initialCapacity: Int) = HashMap<K, V>(initialCapacity)

    override fun addItemByIndex(collection: MutableMap<K, V>, index: Int) {
        val key = dataKeys[index]
        collection[key] = originData[key]!!
    }
}