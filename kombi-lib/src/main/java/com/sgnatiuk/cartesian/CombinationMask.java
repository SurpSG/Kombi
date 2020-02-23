package com.sgnatiuk.cartesian;

import java.util.Iterator;

public class CombinationMask implements Iterator<int[]> {

    private final int[] bases;
    private final int[] mask;
    private boolean hasNext = true;

    public CombinationMask(int[] bases) {
        if (bases.length == 0) {
            throw new IllegalArgumentException("Expected non empty array");
        }
        this.bases = bases;
        this.mask = new int[bases.length];
    }

    @Override
    public boolean hasNext() {
        return hasNext || (hasNext = !increment());
    }

    @Override
    public int[] next() {
        hasNext = false;
        return mask;
    }

    /**
     * Computes next encoded combination
     *
     * @return true if encoded was overflowed, otherwise false
     */
    private boolean increment() {
        int extra = 1;
        int index = mask.length - 1;

        do {
            int temp = mask[index] + extra;
            mask[index] = temp % this.bases[index];
            extra = temp / this.bases[index];
            --index;
        } while (extra != 0 && index >= 0);

        return index < 0 && extra > 0;
    }
}
