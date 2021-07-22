package com.sgnatiuk.combination;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

class KnCombinationIterator<T> implements Iterator<List<T>> {

    private final KnCombinationIndexesIterator combinationIndexesIterator;
    private final T[] items;

    public KnCombinationIterator(T[] items, int combinationSize) {
        this.items = items;
        combinationIndexesIterator = new KnCombinationIndexesIterator(items.length, combinationSize);
    }

    @Override
    public boolean hasNext() {
        return combinationIndexesIterator.hasNext();
    }

    @Override
    public List<T> next() {
        return new KnEncodedList<>(items, combinationIndexesIterator.next());
    }

    private static class KnEncodedList<T> extends AbstractList<T> {
        private final int[] combinationIndexes;
        private final T[] items;

        private KnEncodedList(T[] items, int[] combinationIndexes) {
            this.items = items;
            this.combinationIndexes = combinationIndexes;
        }

        @Override
        public int size() {
            return combinationIndexes.length;
        }

        @Override
        public T get(int index) {
            return items[combinationIndexes[index]];
        }
    }

    private static class KnCombinationIndexesIterator implements Iterator<int[]> {

        private final int elementsCount;

        private final int[] selectedIndexes;
        private final int lastItemIndex;

        KnCombinationIndexesIterator(int elementsCount, int selectCount) {
            this.elementsCount = elementsCount;
            selectedIndexes = new int[selectCount];
            for (int i = 1; i < selectCount; i++) {
                selectedIndexes[i] = i;
            }
            selectedIndexes[0] = -1;
            lastItemIndex = selectCount - 1;
        }

        @Override
        public boolean hasNext() {
            return selectedIndexes[lastItemIndex] != elementsCount - 1
                    || selectedIndexes[lastItemIndex] - selectedIndexes[0] != lastItemIndex;
        }

        @Override
        public int[] next() {
            int index = 0;
            int increment = 1;
            do {
                selectedIndexes[index] += increment;
                if (index == lastItemIndex) {
                    break;
                }
                increment = selectedIndexes[index] == selectedIndexes[index + 1] ? 1 : 0;
                if (increment == 0) {
                    break;
                }
                selectedIndexes[index] = selectedIndexes[index] % selectedIndexes[index + 1] + index;
                index++;
            } while (true);
            return selectedIndexes;
        }
    }

}
