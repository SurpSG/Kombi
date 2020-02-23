package com.sgnatiuk.cartesian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

class CartesianProductSet<T> extends EncodableCartesianProduct<List<T>> implements Serializable {

    private final List<? extends List<T>> values;

    CartesianProductSet(Collection<? extends Collection<T>> values) {
        this(values, false);
    }

    CartesianProductSet(Collection<? extends Collection<T>> values, boolean keepOrder) {
        this.values = convertToFixedOrderMap(values, keepOrder);
    }

    private List<List<T>> convertToFixedOrderMap(Collection<? extends Collection<T>> data, boolean keepOrder) {
        List<List<T>> res = new ArrayList<>();
        for (Collection<T> originData : data) {
            res.add(new ArrayList<>(originData));
        }
        if (!keepOrder) {
            res.sort(new ValuesCountDesc<>());
        }
        return res;
    }


    @Override
    protected MaskDecoder<List<T>> maskDecoder() {
        return encoded -> {
            List<T> res = new ArrayList<>();
            for (int i = 0; i < encoded.length; i++) {
                res.add(values.get(i).get(encoded[i]));
            }
            return res;
        };
    }

    @Override
    protected Collection<? extends Collection<?>> values() {
        return values;
    }


    @Override
    public List<CartesianProduct<List<T>>> split(int n) {
        List<CartesianProduct<List<T>>> splitList = new ArrayList<>(n);
        ArrayList<List<T>> descSortedData = new ArrayList<>(values);
        descSortedData.sort(new ValuesCountDesc<>());

        List<T> firstFieldValues = descSortedData.get(0);
        int parts = Math.min(n, firstFieldValues.size());

        int from = 0;
        for (int i = 0; i < parts; i++) {
            int valuesPerChunk = (firstFieldValues.size() - from) / (parts - i);
            int to = from + valuesPerChunk;
            ArrayList<List<T>> newData = new ArrayList<>(descSortedData);
            newData.set(0, firstFieldValues.subList(from, to));
            splitList.add(new CartesianProductSet<>(newData));
            from = to;
        }
        return splitList;
    }

    private static class ValuesCountDesc<T> implements Comparator<Collection<T>> {
        @Override
        public int compare(Collection<T> o1, Collection<T> o2) {
            return o2.size() - o1.size();
        }
    }
}