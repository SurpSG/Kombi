package com.sgnatiuk.cartesian;

import java.io.Serializable;
import java.util.*;

class CartesianProductSet<T> extends EncodableCartesianProduct<List<T>> implements Serializable {

    private final Object[][] valuesArr;
    private final boolean keepOrder;

    CartesianProductSet(Collection<? extends Collection<T>> values, boolean keepOrder) {
        this(copyToArray(values, keepOrder), keepOrder);
    }

    private CartesianProductSet(Object[][] data, boolean keepOrder) {
        this.valuesArr = data;
        this.keepOrder = keepOrder;
        if (!keepOrder) {
            Arrays.sort(valuesArr, new ArrValuesCountDesc());
        }
    }

    private static Object[][] copyToArray(Collection<? extends Collection<?>> data, boolean keepOrder) {
        Object[][] res = new Object[data.size()][];
        int index = 0;
        for (Collection<?> originData : data) {
            Object[] objects = originData.toArray();
            if (objects.length == 0) {
                return new Object[0][];
            }
            res[index++] = objects;
        }
        return res;
    }

    @Override
    protected MaskDecoder<List<T>> maskDecoder() {
        return DecodableCombination::new;
    }

    private class DecodableCombination extends AbstractList<T> {

        private final int[] localEncoded;

        public DecodableCombination(int[] encoded) {
            this.localEncoded = new int[encoded.length];
            System.arraycopy(encoded, 0, localEncoded, 0, localEncoded.length);
        }

        @Override
        public int size() {
            return localEncoded.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T get(int index) {
            return (T) valuesArr[index][localEncoded[index]];
        }

        @Override
        public Object[] toArray() {
            Object[] result = new Object[localEncoded.length];
            for (int i = 0; i < localEncoded.length; i++) {
                result[i] = get(i);
            }
            return result;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                int index = 0;
                final int size = localEncoded.length;

                @Override
                public boolean hasNext() {
                    return index < size;
                }

                @Override
                public T next() {
                    return get(index++);
                }
            };
        }
    }

    @Override
    protected Object[][] values() {
        return valuesArr;
    }


    @Override
    public List<CartesianProduct<List<T>>> split(int n) {
        if (n < 2) {
            return Collections.singletonList(this);
        }
        List<CartesianProduct<List<T>>> splitList = new ArrayList<>(n);

        int maxLengthArrIndex = indexOfMaxLengthArray();
        int maxLength = valuesArr[maxLengthArrIndex].length;

        Object[] maxLengthArray = valuesArr[maxLengthArrIndex];
        int parts = Math.min(n, maxLength);

        int from = 0;
        for (int i = 0; i < parts; i++) {
            int valuesPerChunk = (maxLength - from) / (parts - i);
            int to = from + valuesPerChunk;

            Object[][] data = new Object[valuesArr.length][];
            System.arraycopy(valuesArr, 0, data, 0, data.length);
            data[maxLengthArrIndex] = Arrays.copyOfRange(maxLengthArray, from, to);

            splitList.add(new CartesianProductSet<>(data, keepOrder));
            from = to;
        }
        return splitList;
    }

    private int indexOfMaxLengthArray() {
        int maxLengthArrIndex = 0;
        int maxLength = valuesArr[0].length;

        for (int i = 1; i < valuesArr.length; i++) {
            if (valuesArr[i].length > maxLength) {
                maxLength = valuesArr[i].length;
                maxLengthArrIndex = i;
            }
        }
        return maxLengthArrIndex;
    }

    private static class ArrValuesCountDesc implements Comparator<Object[]> {

        @Override
        public int compare(Object[] o1, Object[] o2) {
            return o2.length - o1.length;
        }
    }
}
