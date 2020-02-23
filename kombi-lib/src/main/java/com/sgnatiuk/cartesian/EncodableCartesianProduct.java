package com.sgnatiuk.cartesian;

import com.sgnatiuk.extensions.CollectionsExtKt;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

abstract class EncodableCartesianProduct<T> implements CartesianProduct<T> {

    protected abstract MaskDecoder<T> maskDecoder();

    protected abstract Collection<? extends Collection<?>> values();

    @Override
    public BigInteger combinationsCount() {
        return CollectionsExtKt.multiplyAll(values(), Collection::size);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        if (values().isEmpty()) {
            return Collections.emptyIterator();
        }
        return new Iterator<T>() {
            private final CombinationMask dataEncoder = new CombinationMask(bases());
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

    int[] bases() {
        Collection<? extends Collection<?>> values = values();
        int[] radixes = new int[values.size()];
        int index = 0;
        for (Collection<?> value : values) {
            radixes[index++] = value.size();
        }
        return radixes;
    }
}

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
