package com.sgnatiuk.cartesian;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

abstract class EncodableCartesianProduct<T> implements CartesianProduct<T> {

    protected abstract MaskDecoder<T> maskDecoder();

    protected abstract Object[][] values();

    private Lazy<BigInteger> combinationsCount = new Lazy<>(() -> multiplySubArraysLength(values()));

    @Override
    public BigInteger combinationsCount() {
        return combinationsCount.get();
    }

    @Override
    public Iterator<T> iterator() {
        if (values().length == 0) {
            return Collections.emptyIterator();
        }
        return new Iterator<T>() {
            private final CombinationMask dataEncoder = new CombinationMask(radixes());
            private final MaskDecoder<T> maskDecoder = maskDecoder();

            @Override
            public boolean hasNext() {
                return dataEncoder.hasNext();
            }

            @Override
            public T next() {
                return maskDecoder.decode(dataEncoder.next());
            }
        };
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(
                new CartesianProductSpliterator<>(this),
                false
        );
    }

    int[] radixes() {
        Object[][] values = values();
        int[] radixes = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            radixes[i] = values[i].length;
        }
        return radixes;
    }

    private static BigInteger multiplySubArraysLength(Object[][] items) {
        if (items.length == 0) {
            return BigInteger.ZERO;
        }
        BigInteger result = BigInteger.ONE;
        for (Object[] item : items) {
            result = result.multiply(
                    BigInteger.valueOf(item.length)
            );
        }
        return result;
    }

    private static class Lazy<T> {
        private final Supplier<T> supplier;
        private T value;

        private Lazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        private T get() {
            if (value == null) {
                value = supplier.get();
            }
            return value;
        }
    }
}
