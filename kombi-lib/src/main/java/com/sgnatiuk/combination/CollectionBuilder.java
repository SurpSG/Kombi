package com.sgnatiuk.combination;

interface CollectionBuilder<T> {
    T newCollection(int initialCapacity);

    void addItemByIndex(T collection, int index);
}
