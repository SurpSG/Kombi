package com.sgnatiuk.cartesian;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;

class CartesianProductSpliterator<T> implements Spliterator<T> {

    private CartesianProduct<T> cartesianProduct;
    private Iterator<T> cartesianProductIterator;
    private boolean isSizeKnown;
    private long size;

    CartesianProductSpliterator(CartesianProduct<T> cartesianProduct) {
        this.cartesianProduct = cartesianProduct;
        cartesianProductIterator = cartesianProduct.iterator();

        Map.Entry<Boolean, Long> sizeInfo = computeSize();
        isSizeKnown = sizeInfo.getKey();
        size = sizeInfo.getValue();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (cartesianProductIterator.hasNext()) {
            action.accept(cartesianProductIterator.next());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        List<CartesianProduct<T>> cartesianProducts = cartesianProduct.split(2);

        cartesianProduct = cartesianProducts.get(1);
        cartesianProductIterator = cartesianProduct.iterator();
        Map.Entry<Boolean, Long> sizeInfo = computeSize();
        isSizeKnown = sizeInfo.getKey();
        size = sizeInfo.getValue();

        return new CartesianProductSpliterator<>(cartesianProducts.get(0));
    }

    @Override
    public long estimateSize() {
        return size;
    }

    @Override
    public int characteristics() {
        int flags = Spliterator.CONCURRENT | Spliterator.IMMUTABLE | Spliterator.ORDERED;
        return isSizeKnown
                ? flags | Spliterator.SIZED | Spliterator.SUBSIZED
                : flags;
    }

    private Map.Entry<Boolean, Long> computeSize() {
        BigInteger combinationsCount = cartesianProduct.combinationsCount();
        return combinationsCount.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0
                ? new AbstractMap.SimpleEntry<>(false, Long.MAX_VALUE)
                : new AbstractMap.SimpleEntry<>(true, combinationsCount.longValueExact());
    }
}
