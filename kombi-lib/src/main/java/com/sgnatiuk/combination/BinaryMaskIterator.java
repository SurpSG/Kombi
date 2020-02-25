package com.sgnatiuk.combination;

import java.util.Iterator;

class BinaryMaskIterator<T> implements Iterator<T> {

    private final CollectionBuilder<T> collectionBuilder;

    private final long lastValueMask;
    private long currentCombinationMask;

    public BinaryMaskIterator(Range range, CollectionBuilder<T> collectionBuilder) {
        this.collectionBuilder = collectionBuilder;

        currentCombinationMask = range.getFirst();
        lastValueMask = range.getLast();
    }

    @Override
    public boolean hasNext() {
        return currentCombinationMask <= lastValueMask;
    }

    @Override
    public T next() {
        T nextCollection = collectionBuilder.newCollection(Long.bitCount(currentCombinationMask));
        long itemsMask = currentCombinationMask;
        int index = 0;
        do {
            if ((itemsMask & 1) == 1) {
                collectionBuilder.addItemByIndex(nextCollection, index);
            }
            index++;
            itemsMask >>= 1;
        } while (itemsMask > 0);
        currentCombinationMask++;
        return nextCollection;
    }
}
